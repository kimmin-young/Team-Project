package com.team.prj.i_recipe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.team.prj.PageSelector;
import com.team.prj.SiteOption;
import com.team.prj.UR_Relation;
import com.team.prj.user.User;

@Service
public class IRDAO {
	private int allRcpCount;
	private int catRcpCount;
	private int idRcpCount;
	
	@Autowired
	private SqlSession ss;

	@Autowired
	private SiteOption so;

	// 베스트 레시피
	public void bestRcp(HttpServletRequest req) {
		List<Recipe> brcp = ss.getMapper(IRMapper.class).bestRecipe();
		req.setAttribute("brcp", brcp);
	}

	// 조회수 레시피
	public void bestSeenRcp(HttpServletRequest req) {
		List<Recipe> srcp = ss.getMapper(IRMapper.class).seenList();
		for (Recipe recipe : srcp) {
			recipe.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(recipe)); // 각 레시피의 리뷰 수 불러오기

			User u = new User();
			u.setU_id(recipe.getR_writer());
			recipe.setR_writer(ss.getMapper(IRMapper.class).getNickById(u)); // 아이디를 닉네임으로 바꾸기
		}
		req.setAttribute("srcp", srcp);
	}
	

	// 최신 레시피
	public void newRcp(HttpServletRequest req) {
		List<Recipe> nrcp = ss.getMapper(IRMapper.class).newList();
		req.setAttribute("nrcp", nrcp);
	}

	// 모든 레시피 갯수 가져오기
	public void countAllRcp() {
		allRcpCount = ss.getMapper(IRMapper.class).getAllRcpCount();
	}

	// 해당 카테고리의 모든 레시피 갯수 가져오기
	public void countCatRcp(String category, HttpServletRequest req) {
		PageSelector psel = new PageSelector();
		psel.setCategory(category);
		catRcpCount = ss.getMapper(IRMapper.class).getCatRcpCount(psel);
	}

	// 해당 아이디의 모든 레시피 갯수 가져오기
	public void countAllRcpById(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");
		String id = u.getU_id();
		PageSelector psel = new PageSelector();
		psel.setId(id);

		idRcpCount = ss.getMapper(IRMapper.class).getRcpCountById(psel);
	}

	// 유저가 해당 레시피에 리뷰 달았는지 보기
	public boolean reviewed(User u, Recipe rc) {

		if (u.getU_id() == rc.getR_writer())
			return true;
		// 자기 글이라면 리뷰 달 수 없도록

		UR_Relation uRel = new UR_Relation();
		uRel.setUser(u.getU_id());
		uRel.setRecipe(rc.getR_no());

		int rev = ss.getMapper(IRMapper.class).getYourRevCount(uRel);
		// 이 유저가 이 글에 단 리뷰가 있는지 본 뒤
		if (rev == 0)
			return false; // 없으면 false
		else
			return true; // 있으면 true
	}

	// 해당 글에 사진이 있는지 보기
	public boolean hasPhoto(Recipe rc) {

		String hp = ss.getMapper(IRMapper.class).getHeadPhoto(rc).get(0).getR_photo();
		System.out.println("머리 사진: " + hp);
		if (!hp.equals("defaultThumbnail.png"))
			return true; // 기본이미지가 아니면 true

		List<Tail> ts = ss.getMapper(IRMapper.class).getTailPhoto(rc);
		if (ts.size() > 0) {
			for (Tail t : ts) {
				String tp = t.getT_photo();
				System.out.println("꼬리 사진: " + tp);

				if (!tp.equals("defaultThumbnail.png"))
					return true; // 하나라도 기본이미지가 아니면 true
			}
		}

		return false;
	}

	// --------------------------- Create ---------------------------------

	// 파일 업로드
	private String uploadFile(HttpServletRequest req, MultipartFile file) throws IOException {

		String originalFileName = file.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileName = UUID.randomUUID().toString() + extension;
		String path = req.getSession().getServletContext().getRealPath("resources/img");
		File target = new File(path, fileName);
		FileCopyUtils.copy(file.getBytes(), target);
		return fileName;
	}

	// 머리 작성
	public void writeRcp(Recipe rc, HttpServletRequest req, MultipartFile file) {
		try {

			String path = req.getSession().getServletContext().getRealPath("/resources/img");

			String savedFileName = "";
			if (file.getOriginalFilename().length() > 0) {
				savedFileName = uploadFile(req, file);
			}

			User u = (User) req.getSession().getAttribute("loginUser");
			rc.setR_writer(u.getU_id()); // 작성자 아이디 설정

			rc.setR_title(req.getParameter("r_title")); // 제목 설정
			rc.setR_category(req.getParameter("r_category")); // 카테고리 설정

			String txt = req.getParameter("r_text");
			txt = txt.replace("\r\n", "<br>");
			rc.setR_text(txt); // 내용 설정

			if (savedFileName == "") { // 사진 없이 글을 올렸을 경우
				// r_photo = "";
				// img 서버 저장 경로에 절대 겹치지 않을 법한 이미지 파일명으로 저장 후 해당 파일명을 DB에 넣기 (기본 프로필 설정)
				savedFileName = "defaultThumbnail.png";
			} else { // 사진이 있을 경우
				// 이미지를 읽기 위한 버퍼
				BufferedImage sourceImg = ImageIO.read(new File(path, savedFileName));

				// 이미지 너비와 높이 구해 둘중 작은 것 구하기
				int w = sourceImg.getWidth();
				int h = sourceImg.getHeight();
				int min = Math.min(w, h);

				// 넓이와 높이 중 짧은 것 기준으로 정방향 크롭
				BufferedImage cropedImg = Scalr.crop(sourceImg, (w - min) / 2, (h - min) / 2, min, min);

				// 크롭한 이미지를 리사이징해서 100픽셀 단위의 썸네일 생성
				BufferedImage destImg = Scalr.resize(cropedImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
				// 썸네일의 이름을 생성(원본파일명에 's_'를 붙임)
				String thumbnailName = path + File.separator + "s_" + savedFileName;
				File newFile = new File(thumbnailName);
				String formatName = savedFileName.substring(savedFileName.lastIndexOf(".") + 1);
				// 썸네일 생성
				ImageIO.write(destImg, formatName.toUpperCase(), newFile);
			}
			rc.setR_photo(savedFileName);

			// r_no는 시퀀스, r_date는 sysdate, r_ended와 r_star는 디폴트값이 따로 있으므로 설정할 필요 X

			if (ss.getMapper(IRMapper.class).writeRcp(rc) == 1) {
				req.setAttribute("r", "레시피 작성 성공");
				allRcpCount++;
			} else {
				req.setAttribute("r", "레시피 작성 실패");
			}

		} catch (Exception e) {
			req.setAttribute("r", "레시피 작성 실패 (DB연결)");
		}
	}

	// 꼬리 작성
	public void writeTail(Tail t, HttpServletRequest req, MultipartFile file) {
		try {

			String path = req.getSession().getServletContext().getRealPath("/resources/img");
			String savedFileName = "";
			if (file.getOriginalFilename().length() > 0) {
				savedFileName = uploadFile(req, file);
			}

			// head 일련번호는 jsp에서 hidden속성 input에 el로 들어와서 넘겨질 것
			BigDecimal head = new BigDecimal(req.getParameter("t_head"));
			t.setT_head(head);
			// System.out.println("head="+head);
			String txt = req.getParameter("t_text");
			txt = txt.replace("\r\n", "<br>");
			t.setT_text(txt); // 내용 설정

			if (savedFileName == "") { // 사진 없이 글을 올렸을 경우
				// r_photo = "";
				// img 서버 저장 경로에 절대 겹치지 않을 법한 이미지 파일명으로 저장 후 해당 파일명을 DB에 넣기 (기본 프로필 설정)
				savedFileName = "defaultThumbnail.png";
			}
			t.setT_photo(savedFileName);

			if (ss.getMapper(IRMapper.class).writeTail(t) == 1) {
				req.setAttribute("r", "작성 성공");
			} else {
				req.setAttribute("r", "작성 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "작성 실패 (DB연결)");
		}
	}

	// 리뷰 작성
	public void writeRev(Review rv, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String st2 = (String) req.getSession().getAttribute("st");
			if (st2 != null && token.equals(st2)) {
				req.setAttribute("r", "글쓰기실패(새로고침)");
				return;
			}

			User u = (User) req.getSession().getAttribute("loginUser");
			rv.setRv_writer(u.getU_id()); // 작성자 id

			BigDecimal star = new BigDecimal(req.getParameter("rv_star"));
			rv.setRv_star(star); // 별점

			String txt = req.getParameter("rv_text");
			txt = txt.replace("\r\n", "<br>");
			rv.setRv_text(txt); // 내용 설정

			BigDecimal rcp = new BigDecimal(req.getParameter("rv_rcp"));
			rv.setRv_rcp(rcp); // 리뷰 달린 레시피

			if (ss.getMapper(IRMapper.class).writeRev(rv) == 1) {
				req.setAttribute("r", "리뷰 작성 성공");
			} else {
				req.setAttribute("r", "리뷰 작성 실패");
			}

		} catch (Exception e) {
			req.setAttribute("r", "리뷰 작성 실패 (DB 연결)");
		}
	}

	// -------------------------------- Read ----------------------------------

	// 검색어 입력-검색어 설정(세션)
	public void searchRcp(HttpServletRequest req) {
		String search = req.getParameter("search");
		String rcp = req.getParameter("rcp");
		req.getSession().setAttribute("search", search);
		req.getSession().setAttribute("rcp", rcp);
	}

	// 검색어 비우기
	public void searchClear(HttpServletRequest req) {
		req.getSession().setAttribute("search", null);
		req.getSession().setAttribute("rcp", "r_title");
		req.getSession().setAttribute("sort", "r_no");
	}

	// 페이징 파라미터 예외 처리
	public int convertParam(HttpServletRequest req) {
		String p = req.getParameter("p");
		int page;
		if (p == null || p.length() == 0) {
			return page = 1;
		}

		try {
			page = Integer.parseInt(p);
		} catch (Exception e) {
			return page = 1;
		}
		req.setAttribute("conNum", page);
		return page;
	}

	// 레시피 목록 보기
	public void getRcp(int page, String shape, String sort, HttpServletRequest req) {

		req.setAttribute("curPage", page);
		req.setAttribute("shape", shape);
		req.setAttribute("sort", sort);
		System.out.println("sort: " + sort);

		String search = (String) req.getSession().getAttribute("search");
		String rcp = (String) req.getSession().getAttribute("rcp");

		int rcpCount = 0;
		if (search == null) { // 검색 x 전체조회시
			rcpCount = allRcpCount;
			search = "";
		} else {
			PageSelector pSel2 = new PageSelector(search, rcp, 0, 0, shape, sort);
			rcpCount = ss.getMapper(IRMapper.class).getSearchRcpCount(pSel2);
		}

		req.setAttribute("rcpCount", rcpCount); // 조회한 글 갯수

		int allPageCount = (int) Math.ceil((double) rcpCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 개수
		// System.out.println(req.getAttribute("allPageCount"));

		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? rcpCount : start + so.getRecipePerPage() - 1;

		PageSelector pSel = new PageSelector(search, rcp, start, end, shape, sort);
		System.out.println("sort: " + sort);
		// System.out.println("rcp=" + pSel.getRcp() + ", search=" + pSel.getSearch());
		List<Recipe> recipes = ss.getMapper(IRMapper.class).getRcp(pSel);

		for (Recipe recipe : recipes) {
			// System.out.println(recipe.getR_no() + "번 " + recipe.getR_ended());
			// System.out.println("r_seen: "+ recipe.getR_seen());
			recipe.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(recipe)); // 각 레시피의 리뷰 수 불러오기

			User u = new User();
			u.setU_id(recipe.getR_writer());
			recipe.setR_writer(ss.getMapper(IRMapper.class).getNickById(u)); // 아이디를 닉네임으로 바꾸기
		}

		req.setAttribute("rcps", recipes);
	}

	// 카테고리별 레시피 목록 보기
	public void getRcpByCat(int page, String shape, String sort, String category, HttpServletRequest req) {

		req.setAttribute("curPage", page);
		req.setAttribute("shape", shape);
		req.setAttribute("sort", sort);
		req.setAttribute("category", category);
		System.out.println("category : " + category);

		String search = (String) req.getSession().getAttribute("search");
		String rcp = (String) req.getSession().getAttribute("rcp");

		int rcpCount = 0;
		if (search == null) { // 검색 x 전체조회시
			rcpCount = catRcpCount;
			search = "";
		} else {
			PageSelector pSel2 = new PageSelector(search, rcp, 0, 0, shape, sort);
			pSel2.setCategory(category);
			rcpCount = ss.getMapper(IRMapper.class).getSearchCatRcpCount(pSel2);
		}

		req.setAttribute("rcpCount", rcpCount); // 조회한 글 갯수

		int allPageCount = (int) Math.ceil((double) rcpCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 개수

		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? rcpCount : start + so.getRecipePerPage() - 1;

		PageSelector pSel = new PageSelector(search, rcp, start, end, shape, sort);
		pSel.setCategory(category);
		List<Recipe> recipes = ss.getMapper(IRMapper.class).getRcpByCat(pSel);

		for (Recipe recipe : recipes) {
			recipe.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(recipe)); // 각 레시피의 리뷰 수 불러오기

			User u = new User();
			u.setU_id(recipe.getR_writer());
			recipe.setR_writer(ss.getMapper(IRMapper.class).getNickById(u)); // 아이디를 닉네임으로 바꾸기
		}
		req.setAttribute("rcps", null);
		req.setAttribute("rcps", recipes);
	}

	// 본인의 레시피 목록 보기
	public void getRcpById(int page, String shape, String sort, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");
		String nickname = u.getU_nickname();
		String id = u.getU_id();

		req.setAttribute("curPage", page);
		req.setAttribute("shape", shape);
		req.setAttribute("sort", sort);

		String search = (String) req.getSession().getAttribute("search");
		String rcp = (String) req.getSession().getAttribute("rcp");

		int rcpCount = 0;
		if (search == null) { // 검색 x 전체조회시
			rcpCount = idRcpCount;
			search = "";
		} else {
			PageSelector pSel2 = new PageSelector(search, rcp, 0, 0, shape, sort, id);
			rcpCount = ss.getMapper(IRMapper.class).getSearchRcpCountById(pSel2);
		}

		req.setAttribute("rcpCount", rcpCount); // 조회한 글 갯수

		int allPageCount = (int) Math.ceil((double) rcpCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 개수
		System.out.println(req.getAttribute("allPageCount"));

		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? rcpCount : start + so.getRecipePerPage() - 1;

		PageSelector pSel = new PageSelector(search, rcp, start, end, shape, sort, id);
		List<Recipe> recipes = ss.getMapper(IRMapper.class).getRcpById(pSel);

		for (Recipe recipe : recipes) {
			recipe.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(recipe)); // 각 레시피의 리뷰 수 불러오기

			recipe.setR_writer(nickname); // 아이디를 닉네임으로 바꾸기
		}

		req.setAttribute("rcps", recipes);
	}

	// 북마크 목록 처리 (레시피 넘버만 가진 레시피 리스트가 담겨오면 그것으로 레시피 목록 전체 리턴)
	public void getBmkRcp(List<Recipe> rcps, HttpServletRequest req) {

		List<Recipe> nRcp = new ArrayList<Recipe>();
		for (Recipe r : rcps) {
			r = ss.getMapper(IRMapper.class).getHead(r).get(0);
			r.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(r)); // 리뷰수
			nRcp.add(r);
		}

		req.setAttribute("rcps", nRcp);
	}

	// 레시피 번호 받아오기
	public Recipe getHeadNo(Recipe rc, HttpServletRequest req) {
		BigDecimal no = ss.getMapper(IRMapper.class).getHeadNo(rc).get(0).getR_no();
		Recipe r = new Recipe();
		r.setR_no(no);
		return r;
	}

	// 레시피 상세정보 받아오기
	public void getRcpDetail(Recipe rc, HttpServletRequest req) {

		try {
			List<Recipe> h = ss.getMapper(IRMapper.class).getHead(rc);
			if (h.isEmpty()) { // 쿼리 결과가 비어있다면
				req.setAttribute("r", "해당 게시글 없음");
				return;
			}
			Recipe head = h.get(0); // 쿼리 결과는 리스트형이므로 첫 요소를 받아와서

			User u = new User();
			u.setU_id(head.getR_writer());
			req.setAttribute("nick", ss.getMapper(IRMapper.class).getNickById(u)); // 유저닉네임 따로 넣기

			head.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(head)); // 그 레시피의 리뷰 수 받아오기
			head.setR_tails(ss.getMapper(IRMapper.class).getTail(head)); // 그 레시피의 꼬리 목록 받아오기
			for (Tail t : head.getR_tails()) {
				System.out.println("ex: " + t.getT_example());
			}
			req.setAttribute("r", "레시피 디테일 불러오기 성공");
			req.setAttribute("h", head);
		} catch (Exception e) {
			req.setAttribute("r", "레시피 디테일 불러오기 실패 (DB)");
		}

	}

	// 머리 하나 정보 가져오기 (수정에 씀)
	public void getOnlyHead(Recipe rc, HttpServletRequest req) {
		try {
			Recipe h = ss.getMapper(IRMapper.class).getHead(rc).get(0);
			req.setAttribute("h", h);
			req.setAttribute("r", "머리 정보 불러오기 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("r", "머리 정보 불러오기 실패 (DB연결)");
		}
	}

	// 꼬리 하나 정보 가져오기 (꼬리수정에 씀)
	public Tail getOneTail(Tail t, HttpServletRequest req) {
		return ss.getMapper(IRMapper.class).getOneTail(t).get(0);
	}

	// 리뷰 보기
	public void getRev(Recipe rc, HttpServletRequest req) {

		getRcpDetail(rc, req);

		if (req.getSession().getAttribute("loginUser") != null) {
			User u = (User) req.getSession().getAttribute("loginUser"); // 로그인 중인 유저의 정보를 받아서
			boolean Revd = reviewed(u, rc); // 이 유저가 레시피에 리뷰를 달았는지 보기
			req.setAttribute("revd", Revd); // 해당 정보 보내기
		} else {
			req.setAttribute("revd", true); // 로그인되어있지 않을 시 리뷰 쓰기 창이 안 뜨도록
		}

		List<Review> reviews = ss.getMapper(IRMapper.class).getRev(rc); // 레시피 아이디로 리뷰 목록 받아오기
		req.setAttribute("reviews", reviews); // 리뷰 리스트 정보 보내기
	}

	// 레시피 목록 받아와서 리뷰 수를 달아주고 리턴
	public List<Recipe> getRevCnt(List<Recipe> rcps, HttpServletRequest req) {
		for (Recipe r : rcps) {
			r.setR_rev_cnt(ss.getMapper(IRMapper.class).getAllRevCount(r)); // 각 레시피의 리뷰 수 불러오기
		}
		return rcps;
	}

	// ------------------------------ Update ---------------------------------

	// 머리 수정
	public void updateRcp(Recipe rc, HttpServletRequest req, MultipartFile file) {
		try {
			String path = req.getSession().getServletContext().getRealPath("/resources/img");
			String savedFileName = "";
			if (file.getOriginalFilename().length() > 0) {
				savedFileName = uploadFile(req, file);
			}

			BigDecimal no = new BigDecimal(req.getParameter("r_no"));
			Recipe orc = new Recipe();
			orc.setR_no(no);
			orc = ss.getMapper(IRMapper.class).getHead(orc).get(0); // 원래 head 정보 가져오기

			String oldFile = orc.getR_photo(); // 원래 게시글 사진
			String newFile = savedFileName; // 새로 받아온 사진

			if (newFile == "") { // 사진수정 X
				newFile = oldFile;
			} else { // 새 이미지이면 새 썸네일 생성

				// 이미지를 읽기 위한 버퍼
				BufferedImage sourceImg = ImageIO.read(new File(path, newFile));

				// 이미지 너비와 높이 구해 둘중 작은 것 구하기
				int w = sourceImg.getWidth();
				int h = sourceImg.getHeight();
				int min = Math.min(w, h);

				// 넓이와 높이 중 짧은 것 기준으로 정방향 크롭
				BufferedImage cropedImg = Scalr.crop(sourceImg, (w - min) / 2, (h - min) / 2, min, min);

				// 크롭한 이미지를 리사이징해서 100픽셀 단위의 썸네일 생성
				BufferedImage destImg = Scalr.resize(cropedImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
				// 썸네일의 이름을 생성(원본파일명에 's_'를 붙임)
				String thumbnailName = path + File.separator + "s_" + newFile;
				File newFileS = new File(thumbnailName);
				String formatName = newFile.substring(newFile.lastIndexOf(".") + 1);
				// 썸네일 생성
				ImageIO.write(destImg, formatName.toUpperCase(), newFileS);
			}
			rc.setR_photo(newFile);

			rc.setR_title(req.getParameter("r_title")); // 제목 수정
			rc.setR_category(req.getParameter("r_category")); // 카테고리 수정
			rc.setR_text(req.getParameter("r_text")); // 내용 수정
			rc.setR_no(no); // 히든으로 받아온 일련번호 넣기

			System.out.println("사진 이름: " + rc.getR_photo());
			if (ss.getMapper(IRMapper.class).updateRcp(rc) == 1) {
				req.setAttribute("r", "수정 성공");

				// 사진 바꿨다면 기존 사진과 썸네일 지우기
				if (!oldFile.equals(newFile) && !oldFile.equals("defaultThumbnail.png")) {
					String oldThumbnail = "s_" + oldFile;
					oldFile = URLDecoder.decode(oldFile, "utf-8");
					new File(path + "/" + oldFile).delete();
					new File(path + "/" + oldThumbnail).delete();
				}
			} else {
				req.setAttribute("r", "수정 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "수정 실패 (DB연결)");
			e.printStackTrace();
		}
	}

	// 레시피 완성
	public void endRcp(Recipe rc, HttpServletRequest req) {
		try {
			if (ss.getMapper(IRMapper.class).endRcp(rc) == 1) {
				req.setAttribute("r", "레시피 완성!");
			} else {
				req.setAttribute("r", "레시피 완성 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "레시피 완성 실패 (DB)");
		}
	}

	// 조회수 1 증가
	public void updateSeen(Recipe rc, String w, HttpServletRequest req) {
		try {
			User u = (User) req.getSession().getAttribute("loginUser");
			if (w.equals(u.getU_id()))
				return; // 레시피의 작성자와 현재 로그인한 유저 아이디가 같으면
			if (ss.getMapper(IRMapper.class).updateSeen(rc) == 1) {
				return;
			}
		} catch (Exception e) {
			System.out.println("조회수 업데이트 실패 (DB연결)");
		}
	}

	// 별점평균 업데이트
	public void updateStar(Recipe rc, HttpServletRequest req) {
		try {
			BigDecimal star = ss.getMapper(IRMapper.class).getStarAvg(rc); // 리뷰 테이블에서 평점평균 가져오기
			rc.setR_star_avg(star);
			if (ss.getMapper(IRMapper.class).updateStar(rc) == 1) {
				System.out.println("별점평균 업데이트 성공");
				return;
			}
		} catch (Exception e) {
			System.out.println("별점평균 업데이트 실패 (DB)");
		}
	}

	// 꼬리 수정
	public void updateTail(Tail t, HttpServletRequest req, MultipartFile file) {
		try {
			String path = req.getSession().getServletContext().getRealPath("/resources/img");
			String savedFileName = "";
			if (file.getOriginalFilename().length() > 0) {
				savedFileName = uploadFile(req, file);
			}

			Tail ot = new Tail();
			BigDecimal no = new BigDecimal(req.getParameter("t_no"));
			System.out.println("히든으로 받아진 번호" + no);
			ot.setT_no(no);
			ot = ss.getMapper(IRMapper.class).getOneTail(ot).get(0); // 원래 head 정보 가져오기

			String oldFile = ot.getT_photo(); // 원래 게시글 사진
			String newFile = savedFileName; // 새로 받아온 사진

			if (newFile == "") { // 사진수정 X
				newFile = oldFile;
			}

			t.setT_photo(newFile); // 사진 수정

			t.setT_text(req.getParameter("t_text")); // 텍스트 수정
			t.setT_no(no); // 히든으로 받아온 일련번호 넣기

			if (ss.getMapper(IRMapper.class).updateTail(t) == 1) {
				req.setAttribute("r", "수정 성공");

				// 사진 바꿨다면 기존 사진과 썸네일 지우기
				if (!oldFile.equals(newFile) && !oldFile.equals("defaultThumbnail.png")) {
					String oldThumbnail = "s_" + oldFile;
					oldFile = URLDecoder.decode(oldFile, "utf-8");
					new File(path + "/" + oldFile).delete();
					new File(path + "/" + oldThumbnail).delete();
				}
			} else {
				req.setAttribute("r", "수정 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "수정 실패 (DB연결)");
		}
	}

	// 리뷰 수정
	public void updateRev(Review rv, HttpServletRequest req) {
		try {
			if (ss.getMapper(IRMapper.class).updateRev(rv) == 1) {
				req.setAttribute("r", "리뷰 수정 성공");

			} else {
				req.setAttribute("r", "리뷰 수정 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "리뷰 수정 실패 (DB연결)");
		}
	}

	// ------------------------------ Delete ----------------------------------

	// 머리 삭제
	public void deleteRcp(Recipe rc, HttpServletRequest req) {
		try {
			String path = req.getSession().getServletContext().getRealPath("/resources/img");
			Recipe orc = ss.getMapper(IRMapper.class).getHead(rc).get(0);
			String oldFile = orc.getR_photo();
			String oldThumbnail = "s_" + oldFile;

			if (ss.getMapper(IRMapper.class).deleteRcp(rc) == 1) {
				req.setAttribute("r", "레시피 삭제 성공");
				allRcpCount--;

				if (!oldFile.equals("defaultThumbnail.png")) {
					oldFile = URLDecoder.decode(oldFile, "utf-8");
					new File(path + "/" + oldFile).delete(); // 사진 삭제
					new File(path + "/" + oldThumbnail).delete(); // 썸네일 삭제
				}

			} else {
				req.setAttribute("r", "레시피 삭제 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "레시피 삭제 실패 (DB연결)");
		}
	}

	// 꼬리 삭제
	public void deleteTail(Tail t, HttpServletRequest req) {
		try {
			String path = req.getSession().getServletContext().getRealPath("/resources/img");
			Tail ot = ss.getMapper(IRMapper.class).getOneTail(t).get(0);
			String oldFile = ot.getT_photo();

			if (ss.getMapper(IRMapper.class).deleteTail(t) == 1) {
				req.setAttribute("r", "삭제 성공");

				oldFile = URLDecoder.decode(oldFile, "utf-8");
				new File(path + "/" + oldFile).delete(); // 사진 삭제

			} else {
				req.setAttribute("r", "삭제 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "삭제 실패 (DB연결)");
		}
	}

	// 리뷰 삭제
	public void deleteRev(Review rv, HttpServletRequest req) {
		try {
			if (ss.getMapper(IRMapper.class).deleteRev(rv) == 1) {
				req.setAttribute("r", "리뷰 삭제 성공");
			} else {
				req.setAttribute("r", "리뷰 삭제 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "리뷰 삭제 실패 (DB연결)");
		}
	}
}
