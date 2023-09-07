<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/joinWelecome.jsp</title>
</head>
<body>
	<div style="padding-top:100px; padding-left: 450px; display: flex; align-items: center;">
		<img src="resources/img/rogo1.png" id="rogoImgS"><a id="rogoS">해먹자</a>
	</div>

	<hr style="margin-bottom: 30px;" width="800px">

	${welcomeMsg }
	<div align="center">
		<span style="font-size: 30px;">회원가입이</span> <br> <span
			style="font-size: 30px;">완료되었습니다.</span> <br> <br> <span
			style="font-size: 15px;">환영합니다!</span>
	</div>
	<br>
	<br>
	<br>
	<div align="center" style="padding-bottom: 80px;">
		<a href="index"><button class="WelcomeBtn">홈으로</button></a>&nbsp;&nbsp;&nbsp;
		<a href="user.login.go"><button class="WelcomeBtn">로그인</button></a>
	</div>
</body>
</html>