package com.team.prj.user;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private SqlSession ss;
	
	@Autowired
	private MailSender mailSender; // 메일 서비스 이용을 위해 가져온 라이브러리
	
	@Async
	public void sendEmail(String toAddress, String fromAddress,
							String subject, String msgBody) {
		SimpleMailMessage smm = new SimpleMailMessage(); // 메일 형식 설정 메서드
		smm.setFrom(fromAddress);	// 받는 사람 이메일 주소
		smm.setTo(toAddress);		// 보내는 사람 이메일 주소
		smm.setSubject(subject);	// 이메일 제목
		smm.setText(msgBody);		// 이메일 내용
		
		mailSender.send(smm);
	}
	
	
	// 비밀번호 찾기
	public User findPw(User inputU, HttpServletRequest req) {
		return ss.getMapper(UMapper.class).findPw(inputU);
	}
	
	// 임시 비밀번호를 위한 난수 생성
	public String mixNum() {
		int[] randomNum = new int[6];
		Random r = new Random();
		for (int i = 0; i < randomNum.length; i++) {
			randomNum[i] = r.nextInt(10);
			for (int j = 0; j < i; j++) {
				if (randomNum[i]==randomNum[j]) {
					i--;
				}
			}
		}
		
		String text = "";
		for (int i = 0; i < randomNum.length; i++) {
			text = text + randomNum[i];
		}
		
		return text;
	}
	
	// 임시 비밀번호 업데이트
	public void updatePw(User getUserInfo, HttpServletRequest req) {
		if (ss.getMapper(UMapper.class).pwUpdate(getUserInfo) == 1) {
			req.setAttribute("r", "회원정보수정성공");
		}
		else {
			req.setAttribute("r", "회원정보수정실패");
		}
	}
	

}
