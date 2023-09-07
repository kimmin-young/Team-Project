package com.team.prj.p_recipe;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.prj.i_recipe.IRDAO;
import com.team.prj.user.UDAO;

@Controller
public class PRController {
	
	private boolean isFirstReq;
	
	public PRController() {
		isFirstReq = true;
	}
	
	@Autowired
	private UDAO uDAO;
	
	@Autowired
	private PRDAO pRDAO;
	
	@Autowired
	private IRDAO irDAO;
	
	// 공공 레시피 페이지
	@RequestMapping(value = "p_recipe.go", method = RequestMethod.GET)
	public String PRecipe(HttpServletRequest req) {
		if (isFirstReq) {
			pRDAO.countAllPRecipe();
			isFirstReq = false;	// 계속 true 상태면 무한루프
		}
		uDAO.loginCheck(req);
		pRDAO.searchClear(req); // 첫 페이지는 검색이 없는 상태
		pRDAO.getPRecipe(1, req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../p_recipe/p_recipe.jsp");
		return "homepage/index";
	}
	
	// 검색
	@RequestMapping(value = "p_recipe.search", method = RequestMethod.GET)
	public String PRecipeSearch(HttpServletRequest req) {
		uDAO.loginCheck(req);
		pRDAO.searchPRecipe(req);
		pRDAO.getPRecipe(1, req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../p_recipe/p_recipe.jsp");
		return "homepage/index";
	}
	
	// 페이징
	@RequestMapping(value = "p_recipe.paging", method = RequestMethod.GET)
	public String PRecipePaging(HttpServletRequest req) {
		uDAO.loginCheck(req);
		int p = pRDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
		pRDAO.getPRecipe(p, req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../p_recipe/p_recipe.jsp");
		return "homepage/index";
	}
	
	// 공공 레시피 컨텐츠 페이지
	@RequestMapping(value = "p_recipeContent.go", method = RequestMethod.GET)
	public String PRecipeContent(PRecipe pr ,HttpServletRequest req) {
		uDAO.loginCheck(req);
		pRDAO.getPRecipeContent(pr, req);
		int p = pRDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
		pRDAO.getPRecipe(p, req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../p_recipe/p_recipeContent.jsp");
		return "homepage/index";
	}
	

}
