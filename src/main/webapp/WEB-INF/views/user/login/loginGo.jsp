<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/loginGo.jsp</title>
</head>
<body>

	<div>
		<table border="1" style="border-collapse: collapse;" width="320px" height="120">
			<tr>
				<td colspan="2"
					style="text-align: center; height: 46.25px; background-color: #D8D8D8; border-bottom: 1px solid #828282"></td>
			</tr>
			<tr>
				<td style="border-bottom-style: hidden;">
					<div align="center">
						<button class="goLoginBtn" style="width: 95%;"
							onclick="goLogin();">
							<span>로그인</span>
						</button>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center" style="border: none;">
						<button class="goLoginBtn" style="width: 95%"
							onclick="goJoinSelect();">
							<span>회원가입</span>
						</button>
					</div>
				</td>
			</tr>
		</table>
	</div>

</body>
</html>