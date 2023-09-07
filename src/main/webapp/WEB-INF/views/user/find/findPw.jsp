<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>find/findPw.jsp</title>
<style type="text/css">
.findPw::-webkit-input-placeholder {
	text-align: center;
}
</style>
</head>
<body>
	${findPw }
	<div style="padding-top:100px; padding-left: 450px; display: flex; align-items: center;">
		<img src="resources/img/rogo1.png" id="rogoImgS"><a id="rogoS">해먹자</a>
	</div>
	<hr width="700px">

	<form action="user.find.pw.next" method="post"
		onsubmit="return findPwCheck();" name="findPwForm">
		<table style="margin: auto; margin-bottom:80px; width: 250px; height: 200px;">
			<tr>
				<td colspan="2" align="center"><h1>비밀번호 찾기</h1></td>
			</tr>
			<tr>
				<td align="center">이름</td>
				<td><input type="text" name="u_name" class="findPw"
					style="width: 90%;" type="text" placeholder="이름을 입력하세요."
					maxlength="6"></td>
				</td>
			</tr>
			<tr>
				<td align="center">아이디</td>
				<td><input type="text" name="u_id" class="findPw"
					style="width: 90%;" type="text" placeholder="아이디를 입력하세요."
					maxlength="15"></td>
				</td>
			</tr>
			<tr>
				<td align="center">이메일</td>
				<td><input type="text" name="u_email" class="findPw"
					style="width: 90%;" type="text" placeholder="이메일을 입력하세요.">
				</td>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button>비밀번호 찾기</button>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>

