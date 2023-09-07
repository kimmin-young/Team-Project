<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>find/findIdNext.jsp</title>
</head>
<body>
	<div style="padding-top:100px; padding-left: 450px; display: flex; align-items: center;">
		<img src="resources/img/rogo1.png" id="rogoImgS"><a id="rogoS">해먹자</a>
	</div>
	<hr width="700px">

	<table style="margin: auto; margin-bottom: 80px;">
		<tr>
			<td colspan="2" align="center" style="height: 100px;">
				<div style="font-size: 20px;">
					<b> ${msg } </b>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="1" align="center">
				<button style="width:150px; cursor: pointer; margin-bottom: 20px;" onclick="goFindPw();">비밀번호
					찾기</button>
			</td>
		</tr>
	</table>


</body>
</html>