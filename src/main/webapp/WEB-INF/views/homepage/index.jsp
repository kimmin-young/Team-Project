<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="resources/css/index.css">
<link rel="stylesheet" href="resources/css/user.css">
<link rel="stylesheet" href="resources/css/category.css">
<link rel="stylesheet" href="resources/css/recipe.css">
<link rel="stylesheet" href="resources/css/cs.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.css" />
<script
	src="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="resources/js/go.js"></script>
<script type="text/javascript" src="resources/js/jQuery.js"></script>
<script type="text/javascript" src="resources/js/prj_jQuery.js"></script>
<script type="text/javascript" src="resources/js/prj_Check.js"></script>
<script type="text/javascript" src="resources/js/ValidChecker.js"></script>
</head>
<body>

	<table id="homepageTbl">
		<form action="i_recipe.search" method="get">
			<tr>
				<td colspan="2" id="rogo"><a href="index"><img
						src="resources/img/rogo1.png" id="rogoImg"></a><a id="rogo"
					href="index">해먹자</a></td>
				<td id="search" valign="bottom"
					style="text-align: right; font-size: 15pt;">검색 : <input
					id="searchInput" type="text" name="search" maxlength="20"
					value="${search }"> <input type="hidden" name="shape"
					value="gallery"> <input type="hidden" name="sort"
					value="r_no"> <input type="hidden" name="rcp"
					value="r_title">
					<button style="width: 50px; height: 25px; font-weight: 800;">검색</button>
				</td>
			</tr>
		</form>
	</table>

	${loginMsg }
	<!-- 로그인 성공/실패 여부에 대한 alert 문구 -->
	<table id="mainTbl">
		<tr>
			<td rowspan="2" width="230px"
				style="position: absolute; z-index: 2; background-color: #F3F3F3;"><jsp:include
					page="category.jsp" /></td>
			<td rowspan="2" width="1137.5px"
				style="position: relative; z-index: 1; background-color: #F3F3F3; border-radius: 10px; left: -35px;"><jsp:include
					page="${contentPage }" /></td>
			<td style="height: 185px;"><jsp:include page="${loginPage }" />
				${r }</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td>
				<table class="NewTbl">
					<tr>
						<td colspan="2"
							style="height: 46.25px; text-align: center; font-size: 15pt; border: 1px solid #828282; background-color: #D8D8D8;">최신글</td>
					</tr>
					<tr style="height: 20px;">
						<td style="font-size: 15pt; padding: 10px;">NEW!!</td>
						<td align="right"><a
							href="i_recipe.go?shape=gallery&sort=r_no"
							style="font-family: monospace; font-size: 10pt; padding-right: 5px;">전체보기</a></td>
					</tr>
				</table>
				<table class="NewMainTbl">
					<c:set var="i" value="1" />
					<c:forEach var="rc" items="${nrcp }">
						<tr>
							<td width="15%" style="text-align: center;"><b>${i }</b></td>
							<td id="NEWTitle" width="60%"><a
								href="i_recipe.rcp.detail?p=1&shape=gallery&sort=r_no&r_no=${rc.r_no }&from=ircp"
								style="font-family: monospace; font-size: 13pt;">${rc.r_title }</a></td>
						</tr>
						<c:set var="i" value="${i+1}" />
					</c:forEach>
						<tr>
						</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
