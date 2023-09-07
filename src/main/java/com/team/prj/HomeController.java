package com.team.prj;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.team.prj.i_recipe.IRDAO;
import com.team.prj.user.UDAO;

@Controller
public class HomeController {
	
	@Autowired
	UDAO uDAO;

	@Autowired
	IRDAO irDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
		uDAO.loginCheck(req);
		irDAO.bestRcp(req);
		irDAO.bestSeenRcp(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "home.jsp");
		return "homepage/index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest req) {
		return home(req);
	}
	
}
