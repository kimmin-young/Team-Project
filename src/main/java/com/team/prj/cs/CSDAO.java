package com.team.prj.cs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.prj.SiteOption;
import com.team.prj.user.User;

@Service
public class CSDAO {
	private int allCspCount;

	@Autowired
	private SqlSession ss;

	@Autowired
	private SiteOption so;

	// 해당 회원의 전체 글 갯수
	public void countAllCs(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");
		allCspCount = ss.getMapper(CSMapper.class).getAllCspCount(u);
	}

	// 검색어 초기화
	public void searchClear(HttpServletRequest req) {
		req.getSession().setAttribute("searchCs", null);
		req.getSession().setAttribute("searchType", "cs_title");
		req.getSession().setAttribute("categoryType", null);

	}

	// 글가져오기
	public void getCs(int page, HttpServletRequest req) {

		req.setAttribute("curPage", page); // 현재 페이지

		String searchCs = (String) req.getSession().getAttribute("searchCs"); // 검색어
		String searchType = (String) req.getSession().getAttribute("searchType"); // 검색종류
		String categoryType = (String) req.getSession().getAttribute("categoryType");
		User u = (User) req.getSession().getAttribute("loginUser");
		String id = u.getU_id(); // 해당 유저의 닉네임

		if (categoryType == null) {
			categoryType = "";
		}

		int cspCount = 0;
		if (searchCs == null) { // 검색어 없을 때(전체 조회)
			cspCount = allCspCount;
			searchCs = "";
		} else { // 검색어 있을 때
			CSSelector csSel = new CSSelector(searchCs, searchType, categoryType, id, 0, 0); // 검색어 갯수만큼 가져오기
			cspCount = ss.getMapper(CSMapper.class).getSearchCSPCount(csSel);
		}

		req.setAttribute("cspCount", cspCount); // 전체 글 갯수

		int allPageCount = (int) Math.ceil((double) cspCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 총 갯수

		// 한페이지에 보여줄 시작번호와 끝번호
		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? cspCount : start + so.getRecipePerPage() - 1;

		// 전체 게시글 or 검색한 게시글을 rownum 이용해 한페이지에 보여줄 게시글만 가져오기
		CSSelector csSel2 = new CSSelector(searchCs, searchType, categoryType, id, start, end);
		List<CSPost> csposts = ss.getMapper(CSMapper.class).getCsp(csSel2);
		req.setAttribute("csposts", csposts);
	}

	// 글작성
	public void writeCs(CSPost csp, HttpServletRequest req) {

		String token = req.getParameter("token");
		String st2 = (String) req.getSession().getAttribute("st");
		if (st2 != null && token.equals(st2)) {
			req.setAttribute("r", "글쓰기 실패(새로고침)");
			return;
		}

		User u = (User) req.getSession().getAttribute("loginUser");
		csp.setCs_writer(u.getU_id());

		if (ss.getMapper(CSMapper.class).writeCsp(csp) == 1) { // 게시글 DB에 저장 성공
			req.getSession().setAttribute("st", token);
			req.setAttribute("r", "고객센터 글쓰기 성공");
		} else {
			req.setAttribute("r", "고객센터 글쓰기 실패");
		}

	}

	// 검색
	public void searchCs(HttpServletRequest req) {
		String searchCs = req.getParameter("searchCs");
		String searchType = req.getParameter("searchType");
		req.getSession().setAttribute("searchCs", searchCs); // 검색어
		req.getSession().setAttribute("searchType", searchType); // 검색종류
	}

	// 카테고리
	public void categoryCs(HttpServletRequest req) {
		String categoryType = req.getParameter("categoryType");
		req.getSession().setAttribute("categoryType", categoryType); // 카테고리
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

	// 컨텐츠 페이지
	public void getCsContent(CSPost csp, CSReply csr, HttpServletRequest req) {
		if (ss.getMapper(CSMapper.class).updateCsViews(csp) == 1) { // 조회수가 1증가하면

			// 해당하는 글번호의 데이터 가져오기
			CSPost dbCsp = ss.getMapper(CSMapper.class).getCsContent(csp);
			if (dbCsp != null) { // 해당 데이터가 존재하면
				req.setAttribute("csc", dbCsp); // 컨텐츠 페이지 데이터
				// req.setAttribute("curPage", req.getParameter("curPage")); // 현재 페이지

				// 해당하는 글의 댓글 가져오기
				csr.setCsr_cs_num(csp.getCs_num());
				List<CSReply> dbCsr = ss.getMapper(CSMapper.class).getCsReply(csr);
				if (dbCsr != null) {
					req.setAttribute("csr", dbCsr);
				}
				
				for (CSReply r : dbCsr) {	// 모든 댓글마다
					User u = ss.getMapper(CSMapper.class).getCsReplyWriter(r).get(0);	// 댓글 작성자 정보 가져오기
					r.setCsr_nickname(u.getU_nickname());	// 작성자 아이디를 닉네임으로 바꾸기
					r.setCsr_photo(u.getU_photo());	// 작성자 프로필사진 가져오기
				}

				// 해당하는 글의 댓글 갯수 가져오기
				int replyCount = ss.getMapper(CSMapper.class).getCsReplyCount(csr);
				req.setAttribute("replyCount", replyCount);

			} else {
				req.setAttribute("r", "컨텐츠 불러오기 실패");
			}
		} else {
			req.setAttribute("r", "DB 문제");
		}
	}

	// 컨텐츠 페이지 수정
	public void updateCs(CSPost csp, HttpServletRequest req) {
		String token = req.getParameter("token");
		String st2 = (String) req.getSession().getAttribute("st");
		if (st2 != null && token.equals(st2)) {
			req.setAttribute("r", "글수정 실패(새로고침)");
			return;
		}

		if (ss.getMapper(CSMapper.class).updateCs(csp) == 1) {
			req.getSession().setAttribute("st", token);
			req.setAttribute("r", "컨텐츠 페이지 수정 성공");
		} else {
			req.setAttribute("r", "컨텐츠 페이지 수정 실패");
		}
	}

	// 컨텐츠 페이지 삭제
	public void removeCS(CSPost csp, HttpServletRequest req) {
		if (ss.getMapper(CSMapper.class).removeCS(csp) == 1) { // 삭제
			req.setAttribute("r", "컨텐츠 페이지 삭제 성공");
		} else {
			req.setAttribute("r", "컨텐츠 페이지 삭제 실패");
		}

	}

	// 컨텐츠 페이지 댓글 등록
	public void writeCsReply(CSReply csr, HttpServletRequest req) {
		String token = req.getParameter("token");
		String st2 = (String) req.getSession().getAttribute("st");
		if (st2 != null && token.equals(st2)) {
			req.setAttribute("r", "글수정 실패(새로고침)");
			return;
		}
		if (ss.getMapper(CSMapper.class).wrtieCsReply(csr) == 1) { // 댓글 등록 성공
			req.getSession().setAttribute("st", token);
			req.setAttribute("r", "컨텐츠 페이지 댓글 등록 성공");
		} else {
			req.setAttribute("r", "컨텐츠 페이지 댓글 등록 실패");
		}

	}

	// 컨텐츠 페이지
	public void getCsContentReply(CSReply csr, CSPost csp, HttpServletRequest req) {

		// 해당하는 글번호의 데이터 가져오기
		CSPost dbCsp = ss.getMapper(CSMapper.class).getCsContent(csp);
		if (dbCsp != null) { // 해당 데이터가 존재하면
			req.setAttribute("csc", dbCsp); // 컨텐츠 페이지 데이터

			// 해당하는 글의 댓글 가져오기
			List<CSReply> dbCsr = ss.getMapper(CSMapper.class).getCsReply(csr);
			if (dbCsr != null) {
				req.setAttribute("csr", dbCsr);
			}
			
			for (CSReply r : dbCsr) {	// 모든 댓글마다
				User u = ss.getMapper(CSMapper.class).getCsReplyWriter(r).get(0);	// 댓글 작성자 정보 가져오기
				r.setCsr_nickname(u.getU_nickname());	// 작성자 아이디를 닉네임으로 바꾸기
				r.setCsr_photo(u.getU_photo());	// 작성자 프로필사진 가져오기
			}

			// 해당하는 글의 댓글 갯수 가져오기
			int replyCount = ss.getMapper(CSMapper.class).getCsReplyCount(csr);
			req.setAttribute("replyCount", replyCount);

		} else {
			req.setAttribute("r", "컨텐츠 불러오기 실패");
		}
	}

	// 컨텐츠 페이지 댓글 삭제
	public void deleteCsReply(CSReply csr, HttpServletRequest req) {
		if (ss.getMapper(CSMapper.class).deleteCsReply(csr) == 1) {
			req.setAttribute("r", "댓글 삭제 성공");
		} else {
			req.setAttribute("r", "댓글 삭제 실패");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////

	// 모든 회원의 전체 글 갯수
	public void countAllCsManage(HttpServletRequest req) {
		allCspCount = ss.getMapper(CSMapper.class).getAllCspCountManage();
	}

	// 모든 글 가져오기
	public void getCsManage(int page, HttpServletRequest req) {

		req.setAttribute("curPage", page); // 현재 페이지

		String searchCs = (String) req.getSession().getAttribute("searchCs");
		String searchType = (String) req.getSession().getAttribute("searchType");
		String categoryType = (String) req.getSession().getAttribute("categoryType");

		if (categoryType == null) { // 카테고리 종류가 디폴트 값으로 null이면
			categoryType = ""; // 카테고리 전체 설정
		}

		int cspCount = 0;
		// 검색어 없을 때(전체 조회)
		if (searchCs == null) {
			cspCount = allCspCount; // 전체 글 갯수
			searchCs = ""; // 검색어 없이 전체 설정
		}
		// 검색어 있을 때
		else {
			CSSelector csSel = new CSSelector(searchCs, searchType, categoryType, "", 0, 0); // 검색어 갯수만큼 가져오기
			cspCount = ss.getMapper(CSMapper.class).getSearchCSPCountManage(csSel);
		}

		req.setAttribute("cspCount", cspCount); // 전체 글 갯수

		int allPageCount = (int) Math.ceil((double) cspCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 총 갯수

		// 한 페이지에 보여줄 시작번호와 끝번호
		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? cspCount : start + so.getRecipePerPage() - 1;

		// 전체 글 or 검색한 글을 rownum 이용해 한 페이지에 보여줄 갯수만큼만 글 가져오기
		CSSelector csSel2 = new CSSelector(searchCs, searchType, categoryType, "", start, end);
		List<CSPost> csposts = ss.getMapper(CSMapper.class).getCspManage(csSel2);
		req.setAttribute("csposts", csposts);
	}

	// 컨텐츠 페이지 댓글 삭제
	public void deleteCsReplyManage(CSReply csr, HttpServletRequest req) {
		if (ss.getMapper(CSMapper.class).deleteCsReplyManage(csr) == 1) {
			req.setAttribute("r", "댓글 삭제 성공");
		} else {
			req.setAttribute("r", "댓글 삭제 실패");
		}
	}

}
