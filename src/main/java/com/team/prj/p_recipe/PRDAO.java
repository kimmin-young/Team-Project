package com.team.prj.p_recipe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.prj.SiteOption;

@Service
public class PRDAO {

	private int allpRecipeCount;

	@Autowired
	SqlSession ss;

	@Autowired
	private SiteOption so;

	// [공공 레시피] 전체 글 갯수
	public void countAllPRecipe() {
		allpRecipeCount = ss.getMapper(PRMapper.class).getAllPRCount();
	}

	// 검색어 초기화
	public void searchClear(HttpServletRequest req) {
		req.getSession().setAttribute("search", null);
		req.getSession().setAttribute("rcp", "RCP_NM");
	}

	// [공공 레시피 페이지]에 보여줄 데이터 가져오기
	public void getPRecipe(int page, HttpServletRequest req) {

		req.setAttribute("curPage", page); // 현재 페이지

		String search = (String) req.getSession().getAttribute("search"); // 검색어
		String rcp = (String) req.getSession().getAttribute("rcp"); // 검색종류

		int pRecipeCount = 0;
		if (search == null) { // 검색어가 없을 때(전체 조회)
			pRecipeCount = allpRecipeCount;
			search = "";
		} else { // 검색어가 있을 때
			PRSelector prSel = new PRSelector(search, rcp, 0, 0); // 검색어의 갯수만큼 가져오기
			pRecipeCount = ss.getMapper(PRMapper.class).getSearchPRCount(prSel);
		}

		req.setAttribute("pRecipeCount", pRecipeCount); // 전체 글 갯수

		int allPageCount = (int) Math.ceil((double) pRecipeCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 총 갯수

		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? pRecipeCount : start + so.getRecipePerPage() - 1;

		// 전체 게시글 or 검색 게시글의 rownum을 통해 한 페이지당 보여줄 게시글을 가져옴
		PRSelector prSel2 = new PRSelector(search, rcp, start, end);
		List<PRecipe> precipes = ss.getMapper(PRMapper.class).getPRecipe(prSel2);
		req.setAttribute("precipes", precipes);

	}

	// 검색
	public void searchPRecipe(HttpServletRequest req) {
		String search = req.getParameter("search");
		String rcp = req.getParameter("rcp");
		req.getSession().setAttribute("search", search);
		req.getSession().setAttribute("rcp", rcp);
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

	// [공공 레시피 컨텐츠 페이지]에 보여줄 데이터 가져오기
	public void getPRecipeContent(PRecipe pr ,HttpServletRequest req) {
		if (ss.getMapper(PRMapper.class).updatePRecipe(pr) == 1) { // 조회수가 1증가하면

			// 해당하는 글번호의 데이터 가져오기
			PRecipe dbPRContent = ss.getMapper(PRMapper.class).getPRecipeContent(pr);
			if (dbPRContent != null) {
				req.setAttribute("pr", dbPRContent); // 공공 레시피 컨텐츠 데이터
				String manual = dbPRContent.getMANUAL(); // 공공 레시피 컨텐츠의 MANUAL
				String manual_img = dbPRContent.getMANUAL_IMG(); // 공공 레시피 컨텐츠의 MANUAL_IMG
				String[] manual_li = manual.split("!!");
				String[] manual_img_li = manual_img.split("!!");
				req.setAttribute("manual", manual_li);
				req.setAttribute("manual_img", manual_img_li);

			} else {
				req.setAttribute("r", "컨텐츠 불러오기 실패");
			}
		}
	}

}
