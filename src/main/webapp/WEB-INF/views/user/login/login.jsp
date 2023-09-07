<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/login.jsp</title>
<style type="text/css">
.inputIdPw::-webkit-input-placeholder {
	text-align: center;
}

#loginFailTbl {
	padding-left: 25%;
}

.login-wrapper {
	width: 400px;
	height: 320px;
	padding-top: 40px;
	padding-left: 45px;
	padding-right: 45px;
	box-sizing: border-box;
}

.login-wrapper>h2 {
	font-size: 30px;
	margin-bottom: 20px;
}

.inputIdPw {
	width: 100%;
	height: 48px;
	padding: 0 10px;
	box-sizing: border-box;
	margin-bottom: 16px;
	border-radius: 6px;
	background-color: #F8F8F8;
}

#login-form>input::placeholder {
	color: #D2D2D2;
}

.naverHr {
	border: none;
	border-top: 1px solid gray;
	text-align: center;
	overflow: visible;
	width: 310px;
	color: gray;
}

.naverHr::after {
	content: "소셜미디어 계정으로 가입";
	position: relative;
	top: -10px;
	background-color: #EAEAEA;
	padding: 0 10px;
}
</style>
</head>
<body>
	<table style="margin: 0 auto;">
		<tr>
			<td>
				<div class="login-wrapper">
					<h2>Login</h2>
					<form action="user.login" method="post"
						onsubmit="return loginFailCheck();" name="loginFailForm"
						id="login-form">

						<input type="text" name="u_id" class="inputIdPw"
							autocomplete="off" maxlength="15" placeholder="아이디를 입력하세요."
							value="${cookie.lastLoginId.value }" tabindex="1"> <input
							type="password" name="u_pw" class="inputIdPw"
							placeholder="비밀번호를 입력하세요." tabindex="2" tabindex="2"> <input
							type="checkbox" id="saveId" name="saveId"
							${empty cookie.lastLoginId.value ? "":"checked" } tabindex="3">
						<label for="saveId">아이디 저장하기 </label>
						<button
							style="margin: auto; display: block; width: 100%; height: 30px; font-weight: 900;"
							tabindex="4">로그인</button>
					</form>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<table style="width: 400px;">
					<tr>
						<td>
							<hr class="naverHr">
						</td>
					</tr>
					<tr>
						<td>
							<!-- 네이버 로그인 화면으로 이동 시키는 URL --> <!-- 네이버 로그인 화면에서 ID, PW를 올바르게 입력하면 callback 메소드 실행 요청 -->
							<div class="text-center" align="center">
								<a href="${naver_url }" tabindex="4"><img
									src="resources/img/naverLogin.png" width="310px" height="60px">
								</a>
							</div>

							<div id="naver_id_login"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<hr style="width: 310px;">
			</td>
		</tr>
		<tr>
			<td>
				<div style="text-align: center; margin-bottom: 40px;">
					<div
						style="margin: 0 auto; width: 320px; padding-top: 10px; padding-bottom: 10px;">
						<span style="display: inline-block; padding-right: 10px;">
							<button onclick="goFindId();" tabindex="5">아이디찾기</button>
						</span> <span style="display: inline-block; padding-right: 10px;">
							<button onclick="goFindPw();" tabindex="6">비밀번호 찾기</button>
						</span> <span style="display: inline-block; padding-right: 10px;">
							<button onclick="goJoinSelect();" tabindex="7">회원가입</button>
						</span>
					</div>
				</div>
			</td>
		</tr>

	</table>


</body>
</html>