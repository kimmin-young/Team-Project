package com.team.prj.i_recipe;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.team.prj.TokenMaker;
import com.team.prj.bookmark.BDAO;
import com.team.prj.bookmark.Bookmark;
import com.team.prj.user.UDAO;
import com.team.prj.user.User;

@Controller
public class IRController {

	@Autowired
	private UDAO uDAO;

	@Autowired
	private IRDAO irDAO;

	@Autowired
	private BDAO bDAO;

	private boolean isFirstReq;

	public IRController() {
		isFirstReq = true;
	}

	// 개인 레시피 페이지로 이동
	@RequestMapping(value = "/i_recipe.go", method = RequestMethod.GET)
	public String IRecipe(HttpServletRequest req) {
		
		if (req.getParameter("category") != null) {
			String category = req.getParameter("category");
			irDAO.countCatRcp(category, req);
		} else
			irDAO.countAllRcp();

		uDAO.loginCheck(req);
		irDAO.searchClear(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");

		if (req.getParameter("category") != null) {
			String category = req.getParameter("category");
			irDAO.getRcpByCat(1, shape, sort, category, req);
		} else {
			irDAO.getRcp(1, shape, sort, req);
		}

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/i_recipe.jsp");
		return "homepage/index";
	}

	// 검색
	@RequestMapping(value = "i_recipe.search", method = RequestMethod.GET)
	public String searchRcp(HttpServletRequest req) {

		uDAO.loginCheck(req);
		irDAO.searchRcp(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		if (req.getParameter("category") != null) {
			String category = req.getParameter("category");
			irDAO.getRcpByCat(1, shape, sort, category, req);
		} else {
			irDAO.getRcp(1, shape, sort, req);
		}

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/i_recipe.jsp");
		return "homepage/index";
	}

	// 페이징
	@RequestMapping(value = "/i_recipe.page", method = RequestMethod.GET)
	public String pageChange(HttpServletRequest req) {

		System.out.println("category");
		uDAO.loginCheck(req);
		int p = irDAO.convertParam(req);
		TokenMaker.makeToken(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		if (req.getParameter("category") != null) {
			String category = req.getParameter("category");
			irDAO.getRcpByCat(p, shape, sort, category, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/i_recipe.jsp");
		return "homepage/index";
	}

	// 내 레시피 보기
	@RequestMapping(value = "/i_recipe.myrecipe", method = RequestMethod.GET)
	public String myRecipePage(HttpServletRequest req) {
		irDAO.countAllRcpById(req);

		uDAO.loginCheck(req);
		irDAO.searchClear(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		irDAO.getRcpById(1, shape, sort, req);

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/myRecipe.jsp");
		return "homepage/index";
	}

	// 내 레시피 검색
	@RequestMapping(value = "i_recipe.search.my", method = RequestMethod.GET)
	public String searchRcpM(HttpServletRequest req) {
		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}
		irDAO.searchRcp(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		irDAO.getRcpById(1, shape, sort, req);

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/myRecipe.jsp");
		return "homepage/index";
	}

	// 내 레시피 페이징
	@RequestMapping(value = "/i_recipe.page.my", method = RequestMethod.GET)
	public String pageChangeM(HttpServletRequest req) {
		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}
		int p = irDAO.convertParam(req);
		TokenMaker.makeToken(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		irDAO.getRcpById(p, shape, sort, req);

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/myRecipe.jsp");
		return "homepage/index";
	}

	// 머리 작성 페이지로 이동
	@RequestMapping(value = "/i_recipe.write.go", method = RequestMethod.GET)
	public String writePage(HttpServletRequest req) {
		if (!uDAO.loginCheck(req)) {
			req.setAttribute("r", "세션 만료(재로그인 필요)");
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/writeHead.jsp");
		return "homepage/index";
	}

	// 머리 작성
	@RequestMapping(value = "/i_recipe.write", method = RequestMethod.POST)
	public String recipeWrite(Recipe rc, HttpServletRequest req, @RequestParam("file") MultipartFile file,
			RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.writeRcp(rc, req, file);
			Recipe r = irDAO.getHeadNo(rc, req);
			irDAO.getRcpDetail(r, req);
			req.setAttribute("bmkd", false);

			// 하단 목록 보이기

			irDAO.searchClear(req);
			String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
			String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
			int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
			irDAO.getRcp(1, "list", "r_no", req);

			req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");

			re.addFlashAttribute("rc", r);
			return "redirect:/i_recipe.rcp.detail.re?r_no=" + r.getR_no();
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}

	}

	// 꼬리 작성
	@RequestMapping(value = "/i_recipe.tail.write", method = RequestMethod.POST)
	public String tailWrite(Tail t, HttpServletRequest req, @RequestParam("file") MultipartFile file,
			RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.writeTail(t, req, file);
		}
		Recipe r = new Recipe();
		r.setR_no(t.getT_head());
		irDAO.getRcpDetail(r, req);
		bDAO.bookmarked(r, req);
		TokenMaker.makeToken(req);

		irDAO.searchClear(req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			bDAO.getBmk(p, shape, sort, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		re.addFlashAttribute("rc", r);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + r.getR_no();
	}

	// 레시피 완결
	@RequestMapping(value = "/i_recipe.end", method = RequestMethod.GET)
	public String endRcp(Recipe rc, HttpServletRequest req, RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.endRcp(rc, req);
			bDAO.bookmarked(rc, req);
		}
		irDAO.getRcpDetail(rc, req);
		TokenMaker.makeToken(req);

		irDAO.searchClear(req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			List<Recipe> rcps = bDAO.getBmk(p, shape, sort, req);
			rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기
			req.setAttribute("rcps", rcps);
		} else if (from.equals("cat")) {
			String c = req.getParameter("category");
			irDAO.getRcpByCat(p, shape, sort, c, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}
		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");

		re.addFlashAttribute("rc", rc);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + rc.getR_no();
	}

	// 레시피 상세 보기(리다이렉트)
	@RequestMapping(value = "/i_recipe.rcp.detail.re", method = RequestMethod.GET)
	public String showDetailR(Recipe rc, HttpServletRequest req, Model m) {

		m.addAttribute("rc", rc);

		if (uDAO.loginCheck(req)) {
			bDAO.bookmarked(rc, req); // 북마크 여부 가져오기
		}
		irDAO.getRcpDetail(rc, req); // 레시피 디테일 불러오기

		Recipe r = (Recipe) req.getAttribute("h");
		System.out.println("레시피 작성자: " + r.getR_writer());
		String w = r.getR_writer();
		irDAO.updateSeen(rc, w, req); // 조회수 업데이트

		irDAO.searchClear(req);
		if (req.getParameter("search") != null) {
			System.out.println("검색어: " + req.getParameter("search"));
			irDAO.searchRcp(req);
		}

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		System.out.println("from=" + from);
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			List<Recipe> rcps = bDAO.getBmk(p, shape, sort, req);
			rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기
			req.setAttribute("rcps", rcps);
		} else if (from.equals("cat")) {
			String c = req.getParameter("category");
			irDAO.getRcpByCat(p, shape, sort, c, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		return "homepage/index";
	}

	// 레시피 상세 보기
	@RequestMapping(value = "/i_recipe.rcp.detail", method = RequestMethod.GET)
	public String showDetail(Recipe rc, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			bDAO.bookmarked(rc, req); // 북마크 여부 가져오기
		}
		irDAO.getRcpDetail(rc, req); // 레시피 디테일 불러오기

		Recipe r = (Recipe) req.getAttribute("h");
		System.out.println("레시피 작성자: " + r.getR_writer());
		String w = r.getR_writer();
		irDAO.updateSeen(rc, w, req); // 조회수 업데이트

		irDAO.searchClear(req);
		if (req.getParameter("search") != null) {
			System.out.println("검색어: " + req.getParameter("search"));
			irDAO.searchRcp(req);
		}

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		System.out.println("from=" + from);
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			List<Recipe> rcps = bDAO.getBmk(p, shape, sort, req);
			rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기
			req.setAttribute("rcps", rcps);
		} else if (from.equals("cat")) {
			String c = req.getParameter("category");
			irDAO.getRcpByCat(p, shape, sort, c, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		return "homepage/index";
	}

	// 리뷰 목록 페이지로 이동 (리다이렉트)
	@RequestMapping(value = "i_recipe.review.re", method = RequestMethod.GET)
	public String showReviewsR(Recipe rc, HttpServletRequest req, Model m) {
		m.addAttribute("rc", rc);
		uDAO.loginCheck(req);
		irDAO.getRev(rc, req);
		if (!irDAO.hasPhoto(rc)) {
			req.setAttribute("ph", false);
		} else
			req.setAttribute("ph", "true");
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/review.jsp");
		return "homepage/index";
	}

	// 리뷰 목록 페이지로 이동
	@RequestMapping(value = "i_recipe.review", method = RequestMethod.GET)
	public String showReviews(Recipe rc, HttpServletRequest req) {
		uDAO.loginCheck(req);
		irDAO.getRev(rc, req);
		if (!irDAO.hasPhoto(rc)) {
			req.setAttribute("ph", false);
		} else
			req.setAttribute("ph", "true");
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/review.jsp");
		return "homepage/index";
	}

	// 리뷰 쓰기
	@RequestMapping(value = "i_recipe.write.review", method = RequestMethod.POST)
	public String writeReview(Review rv, HttpServletRequest req, RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.writeRev(rv, req);
			TokenMaker.makeToken(req);

			Recipe rc = new Recipe();
			BigDecimal no = new BigDecimal(req.getParameter("rv_rcp"));
			rc.setR_no(no);
			irDAO.updateStar(rc, req);
			irDAO.getRev(rc, req);
			req.setAttribute("contentPage", "../i_recipe/review.jsp");

			re.addFlashAttribute("rc", rc);
			return "redirect:/i_recipe.review.re?r_no=" + no;
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "homepage/home.jsp");
			return "homepage/index";
		}

	}

	// 머리 수정하는 페이지로 이동
	@RequestMapping(value = "/i_recipe.update.head.go", method = RequestMethod.GET)
	public String updateHeadPage(Recipe rc, HttpServletRequest req) {

		if (!uDAO.loginCheck(req)) {
			req.setAttribute("r", "세션 만료(재로그인 필요)");
			TokenMaker.makeToken(req);
			irDAO.getRcpDetail(rc, req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
			return "homepage/index";
		} else {
			req.setAttribute("r", "수정 페이지 불러오기 성공");
			TokenMaker.makeToken(req);
			irDAO.getOnlyHead(rc, req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "../i_recipe/updateHead.jsp");
			return "homepage/index";
		}

	}

	// 머리 수정
	@RequestMapping(value = "i_recipe.update.head", method = RequestMethod.POST)
	public String updateHead(Recipe rc, HttpServletRequest req, @RequestParam("file") MultipartFile file,
			RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.updateRcp(rc, req, file);
			bDAO.bookmarked(rc, req);
		}
		irDAO.getRcpDetail(rc, req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		System.out.println("sort0: " + req.getParameter("sort"));
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			bDAO.getBmk(p, shape, sort, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		re.addFlashAttribute("rc", rc);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + rc.getR_no();
	}

	// 머리 삭제 (글삭제)
	@RequestMapping(value = "i_recipe.delete.head", method = RequestMethod.GET)
	public String deleteRcp(Recipe rc, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			irDAO.deleteRcp(rc, req);
		}
		irDAO.getRcp(1, "gallery", "r_no", req);
		TokenMaker.makeToken(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../i_recipe/i_recipe.jsp");
		return "homepage/index";
	}

	// 꼬리 수정 페이지로 이동
	@RequestMapping(value = "/i_recipe.update.tail.go", method = RequestMethod.GET)
	public String updateHeadPage(Tail t, HttpServletRequest req) {

		if (!uDAO.loginCheck(req)) {
			req.setAttribute("r", "세션 만료(재로그인 필요)");
			TokenMaker.makeToken(req);
			Recipe rc = new Recipe();
			t = irDAO.getOneTail(t, req);
			// 꼬리의 head 컬럼을 불러오기 위해 전체 tail 정보 가져오기
			System.out.println(t.getT_head());
			rc.setR_no(t.getT_head());
			irDAO.getRcpDetail(rc, req);
			bDAO.bookmarked(rc, req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
			return "homepage/index";
		} else {
			TokenMaker.makeToken(req);
			Recipe rc = new Recipe();
			t = irDAO.getOneTail(t, req);
			// 꼬리의 head 컬럼을 불러오기 위해 전체 tail 정보 가져오기
			System.out.println(t.getT_head());
			rc.setR_no(t.getT_head());
			irDAO.getRcpDetail(rc, req);
			bDAO.bookmarked(rc, req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "../i_recipe/updateTail.jsp");
			return "homepage/index";
		}

	}

	// 꼬리 수정
	@RequestMapping(value = "i_recipe.update.tail", method = RequestMethod.POST)
	public String updateTail(Tail t, HttpServletRequest req, @RequestParam("file") MultipartFile file,
			RedirectAttributes re) {
		if (uDAO.loginCheck(req)) {
			irDAO.updateTail(t, req, file);
		}
		Recipe rc = new Recipe();
		t = irDAO.getOneTail(t, req);
		// 꼬리의 head 컬럼을 불러오기 위해 전체 tail 정보 가져오기
		rc.setR_no(t.getT_head());
		irDAO.getRcpDetail(rc, req);
		bDAO.bookmarked(rc, req);
		TokenMaker.makeToken(req);

		irDAO.searchClear(req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			bDAO.getBmk(p, shape, sort, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		// 수정 후 레시피 디테일 페이지로
		re.addFlashAttribute("rc", rc);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + rc.getR_no();
	}

	// 꼬리 삭제
	@RequestMapping(value = "i_recipe.delete.tail", method = RequestMethod.GET)
	public String deleteTail(Tail t, HttpServletRequest req, RedirectAttributes re) {
		System.out.println("삭제 요청한 꼬리:" + t.getT_no());
		Recipe rc = new Recipe();
		t = irDAO.getOneTail(t, req);
		// 꼬리의 head 컬럼을 불러오기 위해 전체 tail 정보 가져오기
		rc.setR_no(t.getT_head());

		if (uDAO.loginCheck(req)) {
			irDAO.deleteTail(t, req);
		}

		irDAO.getRcpDetail(rc, req);
		bDAO.bookmarked(rc, req);
		TokenMaker.makeToken(req);

		irDAO.searchClear(req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req);
		String from = req.getParameter("from");
		if (from == null) {
			irDAO.getRcp(p, shape, sort, req);
		} else if (from.equals("my")) {
			irDAO.getRcpById(p, shape, sort, req);
		} else if (from.equals("bmk")) {
			bDAO.getBmk(p, shape, sort, req);
		} else {
			irDAO.getRcp(p, shape, sort, req);
		}

		req.setAttribute("contentPage", "../i_recipe/recipeDetail.jsp");
		// 삭제 후 레시피 디테일 페이지로
		re.addFlashAttribute("rc", rc);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + rc.getR_no();
	}
}
