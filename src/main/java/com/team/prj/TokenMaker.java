package com.team.prj;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class TokenMaker {
	public static void makeToken(HttpServletRequest req) {
		// 새로고침 : 방금 한 요청 그대로 다시 하기
		// 요청할 때 마다 토큰값을 비교해서 중복되면 insert안하기
		
		// 글쓰기를 할 때마다 달라질만한값을 토큰
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		String token = sdf.format(d);
		req.setAttribute("token", token);
	}
}
