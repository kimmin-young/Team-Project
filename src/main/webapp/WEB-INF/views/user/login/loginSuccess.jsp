<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/loginSuccess.jsp</title>
<style type="text/css">
#dropdown {
	position: relative;
	display: inline-block;
	width: 60px;
}

#dropdown:hover #dropMenu {
	display: block;
}

#dropMenu {
	width: 194px;
	height: 120px;
	position: absolute;
	border: 1px solid #CFCFCF;
	border-radius: 5px;
	background-color: #ededed;
	display: none;
	z-index: 1;
	text-shadow: 1px 1px 1px #BDBDBD;
	font-weight: 350; 
	text-decoration: none;
}

#dropMenu a {
	color:#686868;
	text-decoration: none;
	display: block;
	font-family: initial;
	margin: 7px;
}

#dropMenu div:hover a {
	background-color:#9E9E9E;
	border-radius: 5px;
	font-weight: 900;
	color: white;
}

.infoNickname {
	font-size: 14pt;
}

.infoNickname a {
	font-family: monospace;
}
</style>
</head>
<body>
	<table style="border-collapse: collapse; width: 105%; background-color: #F3F3F3; border: 1px solid #828282; border-bottom: none;">
		<tr>
			<td colspan="2" style="text-align:center; padding-left:10px; height:46.25px; font-size: 15pt; background-color: #D8D8D8; border-bottom: 1px solid #828282">프로필</td>
		</tr>
		<tr>
			<td style="width: 100px; padding: 10px;"><div
					class="infoPhoto">
					<img src="resources/img/${sessionScope.loginUser.u_photo }"
						width="100px" height="100px">
				</div></td>

			<c:choose>
				<c:when test="${sessionScope.loginUser.u_role == 'm' }">
					<td colspan="2"><div class="infoNickname">${sessionScope.loginUser.u_nickname }<a><span
								style="font-size: 13pt;"> 님</span> <br> <span
								style="font-size: 13pt;"> 환영합니다!</span></a>
						</div>
						<div style="color: #5D5D5D; font-size: 12px;">
							<b>(관리자)</b>
						</div></td>
				</c:when>
				<c:otherwise>
					<td colspan="2" class="infoNickname"><div
							style="font-size: 14pt;">${sessionScope.loginUser.u_nickname }<a><span
								style="font-size: 13pt;"> 님</span> <br> <span
								style="font-size: 13pt;">환영합니다!</span></a>
						</div></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>
	<table style="width:105%; background-color: #F3F3F3; border: 1px solid #828282;">
		<tr>
			<td style="width: 100%"><div id="dropdown">
					<button style="width: 145px; height: 35px; cursor: pointer; margin-left: 5px; margin-bottom: 5px; margin-top: 5px;">내정보</button>
					<div id="dropMenu">
						<div onclick="userInfoGo();">
							<a style="font-size: 12pt; cursor: pointer;" class="InfoBtn">마이페이지</a>
						</div>
						<div onclick="goBookmark();">
							<a style="font-size: 12pt; cursor: pointer;" class="InfoBtn">북마크</a>
						</div>
						<div onclick="myIRecipe();">
							<a style="font-size: 12pt; cursor: pointer;" class="InfoBtn">
								내 개인 레시피 보기</a>
						</div>
						<c:if test="${sessionScope.loginUser.u_role == 'u' }">
							<div onclick="csGo();" style="height: 25px;">
								<a style="font-size: 12pt; cursor: pointer;" class="InfoBtn">
									고객센터</a>
							</div>
						</c:if>
						<c:if test="${sessionScope.loginUser.u_role == 'm' }">
							<div onclick="csManageGo()" style="height: 25px;">
								<a style="font-size: 12pt; cursor: pointer;" class="InfoBtn">
									고객센터(m)</a>
							</div>
						</c:if>
					</div>
				</div></td>
			<td><button id="logoutBtn" onclick="logout();" class="InfoBtn"
					style="cursor: pointer; width: 145px; height: 35px; margin-right: 5px; margin-bottom: 5px; margin-top: 5px;">로그아웃</button></td>
		</tr>
	</table>

</body>
</html>