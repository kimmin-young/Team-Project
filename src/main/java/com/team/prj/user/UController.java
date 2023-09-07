package com.team.prj.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.team.prj.i_recipe.IRDAO;
import com.team.prj.oauth.SNSLogin;
import com.team.prj.oauth.SnsValue;

@Controller
public class UController {

	// user bean 가져오기
	@Autowired
	private UDAO uDAO;

	// index의 최신레시피 불러오는 IRDAO 가져오기
	@Autowired
	private IRDAO irDAO;

	// 메일 서비스 bean 가져오기
	@Autowired
	private MailService mailService;

	// SNS 로그인에 사용할 bean 가져오기
	@Autowired
	private SnsValue naverSns;

	// 회원가입 선택 페이지로
	@RequestMapping(value = "/user.join.select", method = { RequestMethod.GET, RequestMethod.POST })
	public String userJoinSelect(HttpServletRequest req) {
		uDAO.loginCheck(req);

		// 네이버 로그인 부분
		SNSLogin snsLogin = new SNSLogin(naverSns);
		req.setAttribute("naver_url", snsLogin.getNaverAuthURL());

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/join/joinSelect.jsp");
		return "homepage/index";
	}

	// 회원가입 약관 페이지로
	@RequestMapping(value = "/user.join.tos", method = RequestMethod.GET)
	public String userJoinToS(HttpServletRequest req) {
		uDAO.loginCheck(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/join/joinToS.jsp");
		return "homepage/index";
	}

	// 회원가입 페이지로
	@RequestMapping(value = "/user.join.go", method = RequestMethod.GET)
	public String userJoinGo(HttpServletRequest req) {
		uDAO.loginCheck(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/join/join.jsp");
		return "homepage/index";
	}

	// 회원가입 method = POST 방식
	@RequestMapping(value = "/user.join", method = RequestMethod.POST)
	public String userJoin(User u, HttpServletRequest req, @RequestParam("file") MultipartFile file) {
		uDAO.join(u, req, file);
		uDAO.loginCheck(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/join/joinWelecome.jsp");
		return "homepage/index";
	}

	// 회원가입 - 아이디 중복 검사
	@RequestMapping(value = "/user.id.check", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users idCheck(User u) {
		return uDAO.idCheck(u);
	}

	// 회원가입 - 이메일 중복 검사, 인증
	@RequestMapping(value = "/user.email.check", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody String emailCheck(User u, HttpServletRequest req) {

		String temp_num; // 인증번호

		// 입력받은 이메일 값이 DB에 존재한다면 (중복체크)
		if (uDAO.emailCheck(u) != null) {
			return "1";
		} else {
			String addr = "alcls0101@gmail.com"; // servlet-context.xml에 등록한
													// 보내는 사람의 이메일 계정

			String email = req.getParameter("u_email"); // 받는 사람의 이메일 계정
			String subject = "본인 확인을 위한 인증번호입니다."; // 보내는 이메일 제목
			temp_num = mailService.mixNum(); // 인증번호

			String body = "안녕하세요. 해먹자를 이용해주셔서 감사합니다. " + "\n" + "본인 확인을 위한 인증번호는  " + temp_num + "  입니다.";
			// 보내는 이메일 내용
			mailService.sendEmail(email, addr, subject, body);
		}
		return temp_num;
	}

	// 회원가입 - 닉네임 중복 검사
	@RequestMapping(value = "/user.nickname.check", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users nicknameCheck(User u) {
		return uDAO.nicknameCheck(u);
	}

	// 회원가입 - 전화번호 중복 검사
	@RequestMapping(value = "/user.phone.check", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users phoneCheck(User u) {
		return uDAO.phoneCheck(u);
	}

	// 로그인 페이지로
	@RequestMapping(value = "/user.login.go", method = RequestMethod.GET)
	public String loginGo(HttpServletRequest req) {
		uDAO.loginCheck(req);

		// 네이버 로그인 부분
		SNSLogin snsLogin = new SNSLogin(naverSns);
		req.setAttribute("naver_url", snsLogin.getNaverAuthURL());

		// 구글일 경우(미구현)
		// SNSLogin snsLogin = new SNSLogin(googleSns);
		// req.setAttribute("google_url", snsLogin.getNaverAuthURL());

		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/login/login.jsp");
		return "homepage/index";
	}

	// 로그인 method = POST 방식
	@RequestMapping(value = "/user.login", method = RequestMethod.POST)
	public String login(User inputU, HttpServletRequest req, HttpServletResponse res) {
		uDAO.login(inputU, req, res);
		if (uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		} else {
			req.setAttribute("contentPage", "../user/login/login.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 네이버 로그인 성공시 callback
	@RequestMapping(value = "/auth.naver.callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(HttpServletRequest req, @RequestParam String code) throws Exception {

		// 1. code를 이용해서 access_token 받기 2. access_token을 이용해서 사용자 profile 정보 가져오기
		SNSLogin snsLogin = new SNSLogin(naverSns);
		String profile = snsLogin.getUserProfile(code);
		System.out.println(profile); // 사용자 profile JSON형태로 출력

		// 3. JSON 형태의 문자열 -> JSON 객체로 변환 -> User에 담기
		User u = snsLogin.parseJson(profile);
		uDAO.callback(u, req);
		
		irDAO.newRcp(req);
		// 네이버 로그인 성공시 리다이렉트로 view 페이지 호출
		return "redirect:/oauth.success";
	}

	// callback 성공시 불러올 페이지
	@RequestMapping(value = "oauth.success", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinSuccess(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("r", "로그인성공");
			req.setAttribute("contentPage", "home.jsp");
		} else {
			req.setAttribute("welcomeMsg",
					"<script type=\"text/javascript\"> alert(\"정보변경을 위한 임시 비밀번호가 회원님의 메일로 전송되었습니다.\"); </script>");
			req.setAttribute("contentPage", "../user/join/joinWelecome.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 로그아웃
	@RequestMapping(value = "/user.logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		uDAO.logout(req);
		uDAO.loginCheck(req);
		irDAO.bestRcp(req);
		irDAO.bestSeenRcp(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "home.jsp");
		return "homepage/index";
	}

	// 내정보 페이지로
	@RequestMapping(value = "/user.info.go", method = RequestMethod.GET)
	public String infoGo(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			uDAO.divideAddr(req);
			req.setAttribute("contentPage", "../user/info.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 회원탈퇴
	@RequestMapping(value = "/user.remove", method = RequestMethod.GET)
	public String remove(HttpServletRequest req) {
		if (uDAO.loginCheck(req)) {
			if (uDAO.remove(req)) {
				uDAO.logout(req);
				uDAO.loginCheck(req);
				irDAO.bestRcp(req);
				irDAO.bestSeenRcp(req);
				req.setAttribute("contentPage", "home.jsp");
			} else {
				uDAO.divideAddr(req);
				req.setAttribute("contentPage", "../user/info.jsp");
			}
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 회원정보수정 method = POST 방식
	@RequestMapping(value = "user.update", method = RequestMethod.POST)
	public String update(User u, HttpServletRequest req, @RequestParam("file") MultipartFile file) {
		if (uDAO.loginCheck(req)) {
			uDAO.update(u, req, file);
			uDAO.divideAddr(req);
			req.setAttribute("contentPage", "../user/info.jsp");
		} else {
			irDAO.bestRcp(req);
			irDAO.bestSeenRcp(req);
			req.setAttribute("contentPage", "home.jsp");
		}
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 회원정보수정 - 이메일 중복 검사
	@RequestMapping(value = "user.email.check.update", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users emailCheckUpdate(User u, HttpServletRequest req) {
		return uDAO.emailCheckUpdate(u, req);
	}

	// 회원정보수정 - 닉네임 중복 검사
	@RequestMapping(value = "user.nickname.check.update", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users nicknameCheckUpdate(User u, HttpServletRequest req) {
		return uDAO.nicknameCheckUpdate(u, req);
	}

	// 회원정보수정 - 전화번호 중복 검사
	@RequestMapping(value = "user.u_phone_value_update", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public @ResponseBody Users phoneCheckUpdate(User u, HttpServletRequest req) {
		return uDAO.phoneCheckUpdate(u, req);
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	// 아이디 찾기
	@RequestMapping(value = "user.find.id", method = RequestMethod.GET)
	public String findId(HttpServletRequest req) {
		uDAO.loginCheck(req);
		req.setAttribute("contentPage", "../user/find/findId.jsp");
		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 아이디 찾기 - 넥스트 페이지 method = POST 방식
	@RequestMapping(value = "user.find.id.next", method = RequestMethod.POST)
	public String findIdNext(User inputU, HttpServletRequest req) {
		uDAO.loginCheck(req);
		User getUserInfo = uDAO.findId(inputU, req);
		String input_name = req.getParameter("u_name");
		String input_email = req.getParameter("u_email");

		if (getUserInfo != null) { // 파라미터로 입력받은 이메일 값이 DB에 있으면

			// 이름 불일치, 이메일 일치
			if (!(getUserInfo.getU_name().equals(input_name)) && (getUserInfo.getU_email().equals(input_email))) {
				req.setAttribute("findId", "<script type=\"text/javascript\"> alert(\"회원정보가 일치하지 않습니다.\"); </script>");
				req.setAttribute("contentPage", "../user/find/findId.jsp");
			}
			// 이름 일치, 이메일 일치
			if ((getUserInfo.getU_name().equals(input_name)) && (getUserInfo.getU_email().equals(input_email))) {
				req.setAttribute("msg",
						"회원님의 아이디는 " + "<span style='color: red;'>" + getUserInfo.getU_id() + "</span>" + " 입니다.");
				req.setAttribute("contentPage", "../user/find/findIdNext.jsp");
			}

		} else { // 해당하는 회원정보가 없으면
			req.setAttribute("findId", "<script type=\"text/javascript\"> alert(\"해당하는 회원정보가 없습니다.\"); </script>");
			req.setAttribute("contentPage", "../user/find/findId.jsp");
		}

		irDAO.newRcp(req);
		return "homepage/index";
	}

	// 비밀번호 찾기
	@RequestMapping(value = "user.find.pw", method = RequestMethod.GET)
	public String findPw(HttpServletRequest req) {
		uDAO.loginCheck(req);
		irDAO.newRcp(req);
		req.setAttribute("contentPage", "../user/find/findPw.jsp");
		return "homepage/index";
	}

	// 메일 서비스
	// 비밀번호 찾기 - 넥스트 페이지 method = POST 방식
	@RequestMapping(value = "user.find.pw.next", method = RequestMethod.POST)
	public String FindPwNext(User inputU, HttpServletRequest req) {
		uDAO.loginCheck(req);

		String input_id = req.getParameter("u_id");
		String input_name = req.getParameter("u_name");
		String input_email = req.getParameter("u_email");

		// 필요한 DB 작업하기
		User getUserInfo = mailService.findPw(inputU, req); // DB에 등롤된 유저 정보 가져오기

		if (getUserInfo != null) { // 파라미터로 입력받은 이메일 값이 DB에 있으면

			// 아이디 일치, 이름 일치, 이메일 일치
			if ((getUserInfo.getU_id().equals(input_id)) && (getUserInfo.getU_name().equals(input_name))
					&& (getUserInfo.getU_email().equals(input_email))) {

				String addr = "alcls0101@gmail.com"; // servlet-context.xml에 등록한
														// 이메일 보내는 계정 아이디

				String email = getUserInfo.getU_email(); // 이메일 받는 계정 아이디

				String subject = getUserInfo.getU_name() + "님 임시 비밀번호입니다.";
				// 보내는 이메일 제목

				String temp_pw = mailService.mixNum(); // 임시 비밀번호

				String body = "안녕하세요. 해먹자를 이용해주셔서 감사합니다. " + "\n" + getUserInfo.getU_name() + " 님의 임시 비밀번호는  " + temp_pw
						+ "  입니다.";
				// 보내는 이메일 내용

				mailService.sendEmail(email, addr, subject, body);
				req.setAttribute("msg", "임시 비밀번호가 메일로 발송되었습니다.");
				req.setAttribute("contentPage", "../user/find/findPwNext.jsp");

				// 임시 비밀번호 DB에 반영하는 작업
				getUserInfo.setU_pw(temp_pw);
				mailService.updatePw(getUserInfo, req);

			} else {
				req.setAttribute("findPw", "<script type=\"text/javascript\"> alert(\"회원정보가 일치하지 않습니다.\"); </script>");
				req.setAttribute("contentPage", "../user/find/findPw.jsp");
			}

		} else { // 해당하는 회원정보가 없으면
			req.setAttribute("findPw", "<script type=\"text/javascript\"> alert(\"해당하는 회원정보가 없습니다.\"); </script>");
			req.setAttribute("contentPage", "../user/find/findPw.jsp");
		}

		irDAO.newRcp(req);
		return "homepage/index";
	}

}
