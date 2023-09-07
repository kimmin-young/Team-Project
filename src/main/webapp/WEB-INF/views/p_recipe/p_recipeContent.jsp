<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>recipe/p_recipeContent.jsp</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
	<a name="top"> </a>

	<div
		style="position: fixed; left: 300px; bottom: 75px; z-index: 50; cursor: pointer;">
		<a title="맨 위로" href="#top"><i class="bi bi-arrow-up-square"
			style="font-size: 20pt;"></i></a>
	</div>
	<div
		style="position: fixed; left: 300px; bottom: 40px; z-index: 50; cursor: pointer;">
		<a title="맨 아래로" href="#target"><i class="bi bi-arrow-down-square"
			style="font-size: 20pt;"></i></a>
	</div>



	<table>
		<tr>
			<td>
				<div align="left">
					<div style="width: 500px;">
						<b> <a
							style="font-family: sans-serif; font-size: 14pt; color: #aaa9a9;"
							href="p_recipe.go">공용레시피로</a>
						</b>
					</div>
				</div>
			</td>
		</tr>
	</table>

	<hr>

	<div style="font-size: 15px; text-align: left; padding-left: 5px;">
		<b>${pr.RCP_NM }</b>
	</div>

	<hr>

	<table
		style="width: 600px; padding-left: 5px; padding-top: 40px; padding-bottom: 5px;"
		align="left">
		<tr style="font-size: 15px;">
			<td>조리방법</td>
		</tr>
		<tr style="color: #5D5D5D; font-size: 12px;">
			<td>${pr.RCP_WAY }</td>
		</tr>
	</table>


	<table
		style="border-top: solid #5D5D5D; width: 600px; padding-left: 5px;"
		align="left">
		<tr style="font-size: 15px;">
			<td>요리종류</td>
		</tr>
		<tr style="font-size: 12px;">
			<td>${pr.RCP_PAT }</td>
		</tr>
	</table>

	<table
		style="border-top: solid #5D5D5D; width: 600px; padding-left: 5px;"
		align="left">
		<tr style="font-size: 15px;">
			<td>열량</td>
		</tr>
		<tr style="color: #5D5D5D; font-size: 12px">
			<td>${pr.INFO_ENG }</td>
		</tr>
	</table>

	<table
		style="border-top: solid #5D5D5D; width: 600px; padding-left: 5px;"
		align="left">
		<tr style="font-size: 15px;">
			<td>재료정보</td>
		</tr>
		<tr style="color: #5D5D5D; font-size: 12px">
			<td>${pr.RCP_PARTS_DTLS }</td>
		</tr>
	</table>

	<table
		style="border-top: solid #5D5D5D; border-bottom: solid #5D5D5D; width: 600px; padding-left: 5px;"
		align="left">
		<tr style="font-size: 15px;">
			<td>저감조리법</td>
		</tr>
		<tr style="color: #5D5D5D; font-size: 12px">
			<td>${pr.RCP_NA_TIP }</td>
		</tr>
	</table>

	<table
		style="width: 85%; border-spacing: 0 15px; padding-left: 5px; padding-bottom: 10px;"
		align="left">
		<c:forEach var="i" items="${manual }" varStatus="status">
			<tr>
				<td><img src="${manual_img[status.index] }" width="250px"
					height="170px"></td>
				<td valign="top"
					style="padding-top: 20px; color: #5D5D5D; font-size: 12px;">${i }</td>
			</tr>
		</c:forEach>

	</table>


	<hr width="100%">




	<!-- 공공 레시피 테이블 -->
	<table border="1" align="center"
		style="width: 80%; border-collapse: collapse; margin-top: 40px;">
		<tr style="border-bottom: 1px solid;">
			<th style="width: 6%; border-bottom: 1px solid; padding: 5px;">글번호</th>
			<th style="width: 7%;">이미지</th>
			<th style="width: 10%;">종류</th>
			<th>메뉴명</th>
			<th style="width: 8%;">조회수</th>
		</tr>
		<c:forEach var="pr" items="${precipes }">
			<tr>
				<td align="center" style="color: orange;">${pr.num }</td>
				<td align="center"><img src="${pr.ATT_FILE_NO_MAIN }"
					class="PRimg"></td>
				<td align="center">${pr.RCP_PAT }</td>
				<td align="center" onclick="p_recipeContent(${pr.num},${curPage });"
					style="cursor: pointer;">
					<div class="ellipsis" style="text-align: center;">${pr.RCP_NM }</div>
				</td>
				<td align="center">${pr.views }</td>
			</tr>
		</c:forEach>
	</table>

	<a name="target"> </a>
	<!-- 스크롤이동을 위한 앵커태그 -->

	<!-- 검색 -->
	<form action="p_recipe.search" method="get">
		<table align="center">
			<tr>
				<td><select name="rcp">
						<c:if test="${rcp == 'RCP_NM' }">
							<option value="RCP_NM" selected>메뉴명</option>
							<option value="RCP_PAT">종류</option>
						</c:if>
						<c:if test="${rcp == 'RCP_PAT' }">
							<option value="RCP_NM">메뉴명</option>
							<option value="RCP_PAT" selected>종류</option>
						</c:if>
				</select></td>
				<td><input type="text" name="search" maxlength="20"
					value="${search }"></td>
				<td>
					<button>검색</button>
				</td>
			</tr>
		</table>
	</form>


	<!-- 페이징 처리를 위한 변수 세팅  -->
	<c:set var="conNum" value="${conNum }" />
	<c:set var="page" value="${(empty param.p)?1:conNum }" />
	<c:set var="startNum" value="${page-(page-1)%10 }" />
	<c:set var="allPageCount" value="${allPageCount }" />

	<!-- 페이징 -->
	<ul style="text-align: center;">

		<!-- 첫 페이지 -->
		<li style="display: inline-block;" onclick="p_recipePaging(${1});">◀◀</li>

		<!-- 이전 페이지 -->
		<c:if test="${startNum != 1 }">
			<li style="display: inline-block;"
				onclick="p_recipePaging(${startNum-10 });">이전</li>
		</c:if>

		<!-- 페이징 번호 -->
		<!-- 검색결과가 없으면  -->
		<!-- end>0, JspTagException이라는 오류가 뜸  -->
		<!-- 검색어에 해당하는 결과가 없어도 1페이지는 나오게 처리  (items=0) forEach 부분-->
		<c:choose>
			<c:when test="${startNum+9 > allPageCount }">
				<c:choose>
					<c:when test="${allPageCount>0 }">
						<c:forEach var="i" begin="0" end="${(allPageCount%10)-1 }">
							<li
								style="display: inline-block;
							color: ${(curPage==startNum+i)?'#FF5E00':'black'};"
								onclick="p_recipePaging(${startNum+i });">${startNum+i }</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="0">
							<li
								style="display: inline-block;
							color: ${(curPage==startNum+i)?'#FF5E00':'black'};"
								onclick="p_recipePaging(${startNum+i });">${startNum+i }</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="0" end="9">
					<li
						style="display: inline-block;
					color: ${(curPage==startNum+i)?'#FF5E00':'black'}; "
						onclick="p_recipePaging(${startNum+i });">${startNum+i }</li>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<!-- 다음 페이지 -->
		<c:if test="${startNum+9 < allPageCount }">
			<li style="display: inline-block;"
				onclick="p_recipePaging(${startNum+10 });">다음</li>
		</c:if>

		<!-- 마지막 페이지 -->
		<li style="display: inline-block;"
			onclick="p_recipePaging(${allPageCount });">▶▶</li>

	</ul>

	<hr>




</body>
</html>