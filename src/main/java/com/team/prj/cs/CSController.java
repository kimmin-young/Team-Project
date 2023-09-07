package com.team.prj.cs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.team.prj.TokenMaker;
import com.team.prj.i_recipe.IRDAO;
import com.team.prj.user.UDAO;

@Controller
public class CSController {

	@Autowired
	private UDAO uDAO;

	@Autowired
	private CSDAO cSDAO;

	@Autowired
	private IRDAO irDAO;

	////////////////////////////////////////////////////////////////////////////// [
	////////////////////////////////////////////////////////////////////////////// 유저
	////////////////////////////////////////////////////////////////////////////// 전용
	////////////////////////////////////////////////////////////////////////////// ]

	// 고객센터 페이지
	@RequestMapping(value = "/cs", method = RequestMethod.GET)
	public String csGo(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCs(req);
			cSDAO.searchClear(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 글작성 페이지
	@RequestMapping(value = "/cs.write", method = RequestMethod.GET)
	public String csWrite(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/csWrite.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 글작성 → 고객센터 페이지
	@RequestMapping(value = "/cs.list", method = RequestMethod.POST)
	public String csList(CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.writeCs(csp, req);
			cSDAO.countAllCs(req);
			cSDAO.searchClear(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 글작성 - 이미지 업로드처리
	@RequestMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
			HttpServletRequest request) {
		JsonObject jsonObject = new JsonObject();

		// 내부경로로 저장
		// Wrapper : 필터 (최종 자원의 요청 결과를 알맞게 변경 가능)
		// String contextRoot = new
		// HttpServletRequestWrapper(request).getRealPath("resources/fileupload/"); //
		// 아래와 동일
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/resources/fileupload/");

		String originalFileName = multipartFile.getOriginalFilename(); // 업로드된 파일의 확장자를 포함한 이름 반환
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자 부분 가져오기
		String savedFileName = UUID.randomUUID() + extension; // 저장될 파일명
		// UUID.randomUUID() : 중복 방지를 위해 식별자 생성

		File targetFile = new File(contextRoot + savedFileName);
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
			jsonObject.addProperty("url", "resources/fileupload/" + savedFileName); // contextroot + 파일명
			jsonObject.addProperty("responseCode", "success");

		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
			jsonObject.addProperty("responseCode", "error");
			request.setAttribute("uploadMsg", "<script type=\"text/javascript\"> alert(\"올바르지 않습니다.\"); </script>");
		}
		String a = jsonObject.toString();
		return a;
	}

	// 검색
	@RequestMapping(value = "cs.search", method = RequestMethod.GET)
	public String csSearch(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.searchCs(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 카테고리
	@RequestMapping(value = "cs.category", method = RequestMethod.GET)
	public String csCategory(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCs(req);
			cSDAO.categoryCs(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 페이징
	@RequestMapping(value = "cs.paging", method = RequestMethod.GET)
	public String csPaging(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCs(req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCs(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			req.setAttribute("contentPage", "home.jsp");
		}
		return "index";
	}

	// 컨텐츠 페이지
	@RequestMapping(value = "csContent.go", method = RequestMethod.GET)
	public String csContent(CSPost csp, CSReply csr, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCs(req);
			cSDAO.getCsContent(csp, csr, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCs(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지를 수정하는 페이지
	@RequestMapping(value = "/cs.update.page", method = RequestMethod.POST)
	public String csUpdatePage(CSPost csp, CSReply csr, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.getCsContent(csp, csr, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/csUpdate.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 수정
	@RequestMapping(value = "cs.update", method = RequestMethod.POST)
	public String csUpdate(CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.updateCs(csp, req);
			cSDAO.countAllCs(req);
			cSDAO.searchClear(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 삭제
	@RequestMapping(value = "csContent.remove", method = RequestMethod.GET)
	public String csRemove(CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.removeCS(csp, req);
			cSDAO.countAllCs(req);
			cSDAO.searchClear(req);
			cSDAO.getCs(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 댓글 등록
	@RequestMapping(value = "cs.reply.write", method = RequestMethod.POST)
	public String csReplyWrite(CSReply csr, CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			// 댓글 DB에 등록
			cSDAO.writeCsReply(csr, req);
			// 컨텐츠 페이지랑 댓글 가져오기
			cSDAO.countAllCs(req);
			csp.setCs_num(csr.getCsr_cs_num());
			cSDAO.getCsContentReply(csr, csp, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCs(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 댓글 삭제
	@RequestMapping(value = "csReply.remove", method = RequestMethod.POST)
	public String csReplyRemove(CSReply csr, CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			// 댓글 DB에 삭제
			cSDAO.deleteCsReply(csr, req);
			// 컨텐츠 페이지랑 댓글 가져오기
			cSDAO.countAllCs(req);
			csp.setCs_num(csr.getCsr_cs_num());
			cSDAO.getCsContentReply(csr, csp, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCs(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	////////////////////////////////////////////////////////////////////////////// [
	////////////////////////////////////////////////////////////////////////////// 관리자
	////////////////////////////////////////////////////////////////////////////// 전용
	////////////////////////////////////////////////////////////////////////////// ]

	// 고객센터 페이지
	@RequestMapping(value = "/cs.manage", method = RequestMethod.GET)
	public String csManageGo(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCsManage(req);
			cSDAO.searchClear(req);
			cSDAO.getCsManage(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 검색
	@RequestMapping(value = "cs.manage.search", method = RequestMethod.GET)
	public String csManageSearch(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.searchCs(req);
			cSDAO.getCsManage(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 카테고리
	@RequestMapping(value = "cs.manage.category", method = RequestMethod.GET)
	public String csManageCategory(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCsManage(req);
			cSDAO.categoryCs(req);
			cSDAO.getCsManage(1, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 페이징
	@RequestMapping(value = "cs.manage.paging", method = RequestMethod.GET)
	public String csManagePaging(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCsManage(req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCsManage(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/cs.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지
	@RequestMapping(value = "csContent.manage.go", method = RequestMethod.GET)
	public String csManageContent(CSPost csp, CSReply csr, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			cSDAO.countAllCsManage(req);
			cSDAO.getCsContent(csp, csr, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCsManage(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 댓글 등록
	@RequestMapping(value = "cs.manage.reply.write", method = RequestMethod.POST)
	public String csManageReplyWrite(CSReply csr, CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			// 댓글 DB에 등록
			cSDAO.writeCsReply(csr, req);
			// 컨텐츠 페이지랑 댓글 가져오기
			cSDAO.countAllCsManage(req);
			csp.setCs_num(csr.getCsr_cs_num());
			cSDAO.getCsContentReply(csr, csp, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCsManage(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 컨텐츠 페이지 댓글 삭제
	@RequestMapping(value = "cs.manage.Reply.remove", method = RequestMethod.POST)
	public String csManageReplyRemove(CSReply csr, CSPost csp, HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			// 댓글 DB에 삭제
			cSDAO.deleteCsReply(csr, req);
			// 컨텐츠 페이지랑 댓글 가져오기
			cSDAO.countAllCsManage(req);
			csp.setCs_num(csr.getCsr_cs_num());
			cSDAO.getCsContentReply(csr, csp, req);
			int p = cSDAO.convertParam(req); // 페이징을 위한 파라미터 예외 처리
			cSDAO.getCsManage(p, req);
			TokenMaker.makeToken(req);
			req.setAttribute("contentPage", "../user/cs/manage/csContent.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

}
