<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/index.css">
<link rel="stylesheet" href="resources/css/member.css">
<script type="text/javascript" src="resources/js/jQuery.js"></script>
<script type="text/javascript" src="resources/js/go.js"></script>
<script type="text/javascript" src="resources/js/kim_jQuery.js"></script>
</head>
<body class="dark">

<table id="mainTitle">
	<tr>
		<td> <a href="index"> SFF ZONE </a> </td>
		<td> ${r }</td>
	</tr>
</table>
<hr>
 
<table id="mainMenu">
	<tr>
		<td align="right"> <jsp:include page="${loginPage }" /> </td>
 		<td> <button class="switchBtn">dark</button> </td>
	</tr>
</table>

<table id="sideMenu">
	<tr>
		<td valign="top">사이드 메뉴</td>
	</tr>
</table>

<table id="mainContent">
		<tr>
			<td align="center"><jsp:include page="${contentPage }"></jsp:include>
			</td>
		</tr>
	</table>

</body>
</html>