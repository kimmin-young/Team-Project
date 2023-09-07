package com.team.prj.bookmark;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team.prj.TokenMaker;
import com.team.prj.i_recipe.IRDAO;
import com.team.prj.i_recipe.Recipe;
import com.team.prj.user.UDAO;

@Controller
public class BController {

	@Autowired
	private UDAO uDAO;

	@Autowired
	private IRDAO irDAO;

	@Autowired
	private BDAO bDAO;

	private boolean isFirstReq;

	public BController() {
		isFirstReq = true;
	}

	// 북마크 추가 (현재 페이지 잔류)
	@RequestMapping(value = "/bookmark.add", method = RequestMethod.GET)
	public String addBmk(Bookmark b, HttpServletRequest req, RedirectAttributes re) {
		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}

		Recipe r = new Recipe();
		r.setR_no(b.getB_rcp());
		irDAO.getRcpDetail(r, req); // 북마크 추가한 글의 정보 불러오기

		bDAO.addBmk(b, req);
		bDAO.bookmarked(r, req);

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
		req.setAttribute("contentPage", "i_recipe/recipeDetail.jsp");

		re.addFlashAttribute("rc", r);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + r.getR_no();
	}

	// 북마크 추가 후 북마크함으로 이동
	@RequestMapping(value = "/bookmark.add.go", method = RequestMethod.GET)
	public String addBmkGo(Bookmark b, HttpServletRequest req) {

		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}

		bDAO.addBmk(b, req);
		bDAO.countAllBmk(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		List<Recipe> rcps = bDAO.getBmk(1, shape, sort, req); // 리뷰 개수 제외 전체 정보 받아옴
		rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기

		req.setAttribute("rcps", rcps);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../bookmark/bookmark.jsp");
		return "homepage/index";
	}

	// 북마크 보기
	@RequestMapping(value = "/bookmark.go", method = RequestMethod.GET)
	public String goBmk(HttpServletRequest req) {
		bDAO.countAllBmk(req);

		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}

		irDAO.searchClear(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		List<Recipe> rcps = bDAO.getBmk(1, shape, sort, req); // 리뷰 개수 제외 전체 정보 받아옴
		rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기

		req.setAttribute("rcps", rcps);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../bookmark/bookmark.jsp");
		return "homepage/index";
	}

	// 북마크함 페이징
	@RequestMapping(value = "/bookmark.page", method = RequestMethod.GET)
	public String pageChangeB(HttpServletRequest req) {
		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}
		int p = Integer.parseInt(req.getParameter("p"));
		TokenMaker.makeToken(req);

		String shape = req.getParameter("shape");
		String sort = req.getParameter("sort");
		List<Recipe> rcps = bDAO.getBmk(p, shape, sort, req);
		rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기

		req.setAttribute("rcps", rcps);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../bookmark/bookmark.jsp");
		return "homepage/index";
	}

	// 북마크함 검색
	@RequestMapping(value = "/bookmark.search", method = RequestMethod.GET)
	public String searchRcpB(HttpServletRequest req) {
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
		List<Recipe> rcps = bDAO.getBmk(1, shape, sort, req); // 리뷰 개수 제외 전체 정보 받아옴
		rcps = irDAO.getRevCnt(rcps, req); // 리뷰 개수 받아오기

		req.setAttribute("rcps", rcps);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../bookmark/bookmark.jsp");
		return "homepage/index";
	}

	// 북마크 삭제
	@RequestMapping(value = "/bookmark.delete", method = RequestMethod.GET)
	public String deleteBmk(Bookmark b, HttpServletRequest req, RedirectAttributes re) {

		if (!uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			irDAO.newRcp(req);
			req.setAttribute("contentPage", "home.jsp");
			return "homepage/index";
		}

		Recipe r = new Recipe();
		r.setR_no(b.getB_rcp());
		irDAO.getRcpDetail(r, req); // 북마크 삭제한 글의 정보 불러오기

		bDAO.deleteBmk(b, req);
		bDAO.bookmarked(r, req);

		TokenMaker.makeToken(req);

		String shape = req.getParameter("shape") == null ? "list" : req.getParameter("shape");
		String sort = req.getParameter("sort") == null ? "r_no" : req.getParameter("sort");
		int p = req.getParameter("p") == null ? 1 : irDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
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
		req.setAttribute("contentPage", "i_recipe/recipeDetail.jsp");

		re.addFlashAttribute("rc", r);
		return "redirect:/i_recipe.rcp.detail.re?r_no=" + r.getR_no();
	}

}