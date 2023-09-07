package com.team.prj.user;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Service
public class UDAO {

	@Autowired
	private SqlSession ss;

	@Autowired
	private MailService mailService;

	// 로그인 체크
	public boolean loginCheck(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");
		if (u != null) {
			req.setAttribute("loginPage", "../user/login/loginSuccess.jsp");
			return true;
		} else {
			req.setAttribute("loginPage", "../user/login/loginGo.jsp");
			return false;
		}
	}

	// 회원가입
	public void join(User u, HttpServletRequest req, MultipartFile file) {

		try {

			String savedFileName = "";
			if (file.getOriginalFilename().length() > 0) {
				savedFileName = uploadFile(req, file);
			}

			u.setU_id(req.getParameter("u_id"));
			u.setU_pw(req.getParameter("u_pw"));
			String email = req.getParameter("u_email1") + "@" + req.getParameter("u_email2");
			u.setU_email(email);
			u.setU_nickname(req.getParameter("u_nickname"));
			u.setU_name(req.getParameter("u_name"));
			u.setU_phone(req.getParameter("u_phone"));
			u.setU_role(req.getParameter("u_role"));

			String u_addr1 = req.getParameter("u_addr1"); // 우편번호
			String u_addr2 = req.getParameter("u_addr2"); // 주소
			String u_addr3 = req.getParameter("u_addr3"); // 상세주소
			String u_addr = u_addr2 + "!!" + u_addr3 + "!!" + u_addr1; // 주소를 !! 로 구분
			// 서울시 강남구 강남동!!701호!!04034
			u.setU_addr(u_addr);

			// 회원가입시 사진은 선택사항
			if (savedFileName == "") { // 회원가입시 사진을 선택 안했을 경우
				// u_photo = "";
				// img 서버 저장 경로에 절대 겹치지 않을 법한 이미지 파일명으로 저장 후 해당 파일명을 DB에 넣기 (기본 프로필 설정)
				savedFileName = "defaultimage.png";
			}
			u.setU_photo(savedFileName);

			// pstmt.executeUpdate() , 쿼리가 한줄 변화 생기면 true(성공)
			if (ss.getMapper(UMapper.class).join(u) == 1) {
				req.setAttribute("r", "가입성공");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("r", "가입실패");
		}
	}

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

	// 회원가입 - 아이디 중복 검사
	public Users idCheck(User u) {
		return new Users(ss.getMapper(UMapper.class).idCheck(u));
	}

	// 회원가입 - 이메일 중복 검사
	public User emailCheck(User u) {
		return (User) ss.getMapper(UMapper.class).emailCheck(u);
	}

	// 회원가입 - 닉네임 중복 검사
	public Users nicknameCheck(User u) {
		return new Users(ss.getMapper(UMapper.class).nicknameCheck(u));
	}

	// 회원가입 - 전화번호 중복 검사
	public Users phoneCheck(User u) {
		return new Users(ss.getMapper(UMapper.class).phoneCheck(u));
	}

	// 로그인
	// login.jsp에서 → User자바빈 세터로 u_id, u_pw 값을 받은 상태
	public void login(User inputU, HttpServletRequest req, HttpServletResponse res) {

		// DB를 통해 받은 데이터
		User dbU = ss.getMapper(UMapper.class).login(inputU);

		if (dbU != null) {
			// DB로 받아온 데이터의 pw와 파라미터로 받아온 pw값이 같은 경우
			if (dbU.getU_pw().equals(inputU.getU_pw())) {

				// 아이디 저장 쿠키 설정
				String saveId = req.getParameter("saveId");
				// System.out.println(saveId);
				Cookie c = new Cookie("lastLoginId", dbU.getU_id());
				if (saveId == null) { // 체크박스 비어있으면 null이 저장됨
					c.setMaxAge(0);
					res.addCookie(c);
				} else { // 체크박스 클릭하면 on이 저장됨
					c.setMaxAge(60 * 60 * 24 * 30); // 30일 설정
					res.addCookie(c);
				}

				req.getSession().setAttribute("loginUser", dbU);
				req.getSession().setMaxInactiveInterval(60 * 30); // session유지 30분으로 설정
				req.setAttribute("r", "로그인성공");
			} else {
				req.setAttribute("r", "로그인실패(비밀번호오류)");
				req.setAttribute("loginMsg",
						"<script type=\"text/javascript\"> alert(\"비밀번호가 일치하지 않습니다.\"); </script>");
			}
		} else {
			req.setAttribute("r", "로그인실패(미등록)");
			req.setAttribute("loginMsg", "<script type=\"text/javascript\"> alert(\"등록되지 않은 아이디입니다.\"); </script>");
		}
	}

	// 로그인 callback
	public void callback(User u, HttpServletRequest req) {

		// 4. DB에 해당 유저가 존재하면 로그인, 미존재시 가입
		User dbU = ss.getMapper(UMapper.class).login(u);

		if (dbU == null) { // 회원가입
			if (ss.getMapper(UMapper.class).join(u) == 1) {

				req.setAttribute("r", "가입성공");

				String addr = "alcls0101@gmail.com"; // servlet-context.xml에 등록한
														// 보내는 사람의 이메일 계정

				String email = u.getU_email(); // 받는 사람의 이메일 계정

				String subject = u.getU_name() + "님 임시 비밀번호입니다.";
				// 보내는 이메일 제목

				String temp_pw = u.getU_pw(); // 임시 비밀번호

				String body = "안녕하세요. 해먹자를 이용해주셔서 감사합니다. " + "\n" + u.getU_name() + " 님의 임시 비밀번호는  " + temp_pw
						+ "  입니다.";
				// 보내는 이메일 내용

				mailService.sendEmail(email, addr, subject, body);

				req.setAttribute("welcomeMsg",
						"<script type=\"text/javascript\"> alert(\"정보변경을 위한 임시 비밀번호가 회원님의 메일로 전송되었습니다.\"); </script>");
				loginCheck(req);
				req.setAttribute("contentPage", "user/join/joinWelecome.jsp");
			}
		} else { // 로그인
			req.getSession().setAttribute("loginUser", dbU);
			req.getSession().setMaxInactiveInterval(60 * 30); // session유지 30분으로 설정
			req.setAttribute("r", "로그인성공");

			loginCheck(req);
			req.setAttribute("contentPage", "home.jsp");
		}
	}

	// 로그아웃
	public void logout(HttpServletRequest req) {
		req.getSession().setAttribute("loginUser", null);
		req.setAttribute("r", "로그아웃성공");
	}

	// 내정보 - 주소 분리하기
	public void divideAddr(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");
		String addr = u.getU_addr();
		String addr2[] = addr.split("!!");
		req.setAttribute("addr", addr2);
	}

	// 회원탈퇴
	public boolean remove(HttpServletRequest req) {

		try {
			String u_pw = req.getParameter("u_pw"); // 입력한 비밀번호 값
			User u = (User) req.getSession().getAttribute("loginUser"); // 현재 유저의 비밀번호
			if (u.getU_pw().equals(u_pw)) { // 입력한 비밀번호 == 현재 유저의 비밀번호
				u.setU_pw(u_pw);
			} else {
				req.setAttribute("updateMsg", "<script type=\"text/javascript\"> alert(\"회원탈퇴에 실패했습니다.\"); </script>");
				return false;
			}

			if (ss.getMapper(UMapper.class).remove(u) == 1) { // 탈퇴 성공시
				req.setAttribute("r", "회원탈퇴완료");
				req.setAttribute("loginMsg",
						"<script type=\"text/javascript\"> alert(\"회원탈퇴가 완료되었습니다. 해먹자를 이용해주셔서 감사합니다.\"); </script>");

				// 인코딩 → 디코딩
				String photo = URLDecoder.decode(u.getU_photo(), "utf-8");

				// 기본 프로필로 설정해둔 이미지 파일명이 아닌 경우에만 이미지 삭제
				if (!u.getU_photo().equals("defaultimage.png")) {
					// 남아있는 사진 파일 지우기
					String path = req.getSession().getServletContext().getRealPath("resources/img");
					new File(path + "/" + photo).delete();
				}

				return true;

			} else { // 탈퇴 실패시
				req.setAttribute("r", "회원탈퇴실패");
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("r", "회원탈퇴살패");
			return false;
		}
	}

	// 회원정보수정
	public void update(User u, HttpServletRequest req, MultipartFile file) {
		try {
			String path = req.getSession().getServletContext().getRealPath("resources/img");
			String savedFileName = "";
			if (file.getOriginalFilename().length()>0) {
				savedFileName = uploadFile(req, file);
			}
			
			// 현재 회원정보 (수정되기 전)
			User lu = (User) req.getSession().getAttribute("loginUser");
			// 기존 photo 파일명
			String oldFile = lu.getU_photo();
			// 새로받아온 photo 파일명
			String newFile = savedFileName;
			
			if (newFile == "") { // 사진수정 X
					newFile = oldFile;
			} 
			
			u.setU_photo(newFile);
			
			u.setU_id(req.getParameter("u_id"));
			u.setU_pw(req.getParameter("u_pw"));
			String newPw = req.getParameter("new_u_pw"); // 새 비밀번호
			
			u.setU_email(req.getParameter("u_email"));
			u.setU_nickname(req.getParameter("u_nickname"));
			u.setU_name(req.getParameter("u_name"));
			u.setU_phone(req.getParameter("u_phone"));
			u.setU_role(req.getParameter("u_role"));
			
			String u_addr1 = req.getParameter("u_addr1"); // 우편번호
			String u_addr2 = req.getParameter("u_addr2"); // 주소
			String u_addr3 = req.getParameter("u_addr3"); // 상세주소
			String u_addr = u_addr2 +"!!"+ u_addr3 +"!!"+ u_addr1; // 주소를  !! 로 구분
			// 서울시 강남구 강남동!!701호!!04034
			u.setU_addr(u_addr);
			
			// 회원정보수정성공
			if (ss.getMapper(UMapper.class).update(u) == 1) {
				req.setAttribute("r", "회원정보수정성공");
				
				// 새 비밀번호가 null값이 아니면서 유효성검사를 통과했으면 새로운 비밀번호로 변경 
				if ( !newPw.equals("") ) {
					u.setU_pw(newPw);
					ss.getMapper(UMapper.class).pwUpdate(u);
				}
				
				// DB정보 변경 → info.jsp에 반영하기 위해 새로운 정보를 session에 저장
				req.getSession().setAttribute("loginUser", u);
				// loginSuccess.jsp에도 반영
				loginCheck(req);
				req.setAttribute("updateMsg", "<script type=\"text/javascript\"> alert(\"회원정보가 변경되었습니다.\"); </script>");
				
				// 사진을 바꾼 상태 → 기존 사진 파일 지우기
				if (!oldFile.equals(newFile)) {
					
					// 기본 프로필로 설정해둔 이미지 파일명이 아닌 경우에만 이미지 삭제
					if(!oldFile.equals("defaultimage.png")) {
						oldFile = URLDecoder.decode(oldFile, "utf-8");
						new File(path + "/" + oldFile).delete();
						req.setAttribute("updateMsg", "<script type=\"text/javascript\"> alert(\"회원정보가 변경되었습니다.\"); </script>");
					}
				} 
				
			} else { // 회원정보수정실패
				if (!oldFile.equals(newFile)) {
					newFile = URLDecoder.decode(newFile, "utf-8");
					new File(path +"/" + newFile).delete();
				}
				req.setAttribute("r", "회원정보수정실패");
				req.setAttribute("updateMsg", "<script type=\"text/javascript\"> alert(\"입력에 오류가 있으니 다시 한 번 확인해 주세요.\"); </script>");

			}
		} catch (Exception e) {
//			e.printStackTrace();
			req.setAttribute("r", "회원정보수정실패");
			req.setAttribute("updateMsg", "<script type=\"text/javascript\"> alert(\"입력에 오류가 있으니 다시 한 번 확인해 주세요.\"); </script>");
		}
	}

	// 회원정보수정 - 이메일 중복 검사
	public Users emailCheckUpdate(User u, HttpServletRequest req) {
		User sessionData = (User) req.getSession().getAttribute("loginUser");
		u.setUpdate_email(sessionData.getU_email());
		return new Users(ss.getMapper(UMapper.class).emailCheckUpdate(u));
	}

	// 회원정보수정 - 닉네임 중복 검사
	public Users nicknameCheckUpdate(User u, HttpServletRequest req) {
		User sessionData = (User) req.getSession().getAttribute("loginUser");
		u.setUpdate_nickname(sessionData.getU_nickname());
		return new Users(ss.getMapper(UMapper.class).nicknameCheckUpdate(u));
	}

	// 회원정보수정 - 전화번호 중복 검사
	public Users phoneCheckUpdate(User u, HttpServletRequest req) {
		User sessionData = (User) req.getSession().getAttribute("loginUser");
		u.setUpdate_phone(sessionData.getU_phone());
		return new Users(ss.getMapper(UMapper.class).phoneCheckUpdate(u));
	}

	// 아이디 찾기
	public User findId(User inputU, HttpServletRequest req) {
		return ss.getMapper(UMapper.class).findId(inputU);
	}
}
