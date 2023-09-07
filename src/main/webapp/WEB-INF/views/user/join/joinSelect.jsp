<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/joinSelect.jsp</title>
<style type="text/css">
.SelectDiv {
	padding: 15px;
}

.naverHr {
	border: none;
	border-top: 1px solid gray;
	text-align: center;
	overflow: visible;
	width: 350px;
	color: gray;
}

.naverHr::after {
	content: "소셜미디어 계정으로 가입";
	position: relative;
	top: -10px;
	background-color: #EAEAEA;
	padding: 0 10px;
}

.joinHr {
	border: none;
	border-top: 1px solid gray;
	text-align: center;
	overflow: visible;
	width: 350px;
	color: gray;
}

.joinHr::after {
	content: "회원가입하기";
	position: relative;
	top: -10px;
	background-color: #EAEAEA;
	padding: 0 10px;
}

.naverJoinDiv {
	float: left;
	width: 50%;
}

.joinDiv {
	float: left;
	width: 50%;
}

.updown {
	position: absolute;
	right: 569.75px;
	top: 364.75px;
	border-left:1px solid gray;
	height: 100px;
}
</style>
</head>
<body>
	<div
		style="width: 100%; height: 500px; margin-top: 20%;">
		<div align="center" style="font-size: 30px; margin-bottom: 20px;">해먹자 회원가입</div>
		<div align="center"
			style="font-size: 15px; color: gray; margin-bottom: 40px;">해먹자
			회원이 되어 레시피를 공유해보세요!</div>


		<div class="naverJoinDiv">
			<hr class="naverHr">
			<div align="center" class="SelectDiv">
				<a href="${naver_url }"> <img alt="네이버회원가입"
					src="resources/img/btnJ.png" width="270px" height="80px"></a>
			</div>
		</div>

		<div class="updown"></div>

		<div class="joinDiv">
			<hr class="joinHr">
			<div align="center" class="SelectDiv">
				<a href="user.join.tos"> <img alt="일반회원가입"
					src="resources/img/join.png" width="270px" height="80px"></a>
			</div>
		</div>
	</div>
</body>
</html>