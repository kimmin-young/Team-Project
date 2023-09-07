<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>i_recipe/recipeDetail</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<style type="text/css">
.content {
	display: block;
	margin-left: auto;
	margin-right: auto;
}

#contentTbl_1 {
	width: 80%;
	border-top: 1px solid;
	border-collapse: collapse;
}

#contentTbl_1 th {
	border-bottom: 1px solid;
	padding: 5px;
}

#contentTbl_1 td {
	border-bottom: 0.5px dashed gray;
	border-left: 1px solid gray;
	padding: 5px;
}

#contentTbl_1 th:first-child, #contentTbl td:first-child {
	border-left: none;
}

#contentTbl_1 td:last-child {
	border-bottom: 1px solid;
}

#btnTbl {
	width: 80%;
	padding-bottom: 5px;
}

.listSubText {
	font-size: 8px;
}

#headInfo {
	margin-top: -13px;
	width: 1135px;
}

#cateInfo {
	width: 5%;
	font-size: 12pt;
	color: orange;
	text-shadow: 1px 2px 0px black;
}

.bi bi-bookmark-plus, .bi bi-bookmark-dash-fill {
	font-size: 20pt;
}

#writeTail textarea {
	font-size: 10pt;
	font-weight: 500;
	width: 800px;
	height: 120px;
	resize: none;
	border: none;
	width: 800px;
}
</style>
</head>
<body>
	<a name="top"> </a>
	<c:set var="no" value="${h.r_no }" />
	<c:choose>
		<c:when test="${!empty param.search }">
			<c:set var="s" value="&search=${param.search }" />
		</c:when>
		<c:otherwise>
			<c:set var="s" value="#" />
		</c:otherwise>
	</c:choose>

	<c:set var="par"
		value="p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=${param.from }${c }${s }" />

	<div align="left" style="width: 500px;">
		<b> <a href="i_recipe.go?${par }"
			style="font-family: sans-serif; font-size: 14pt; color: #aaa9a9;">개인레시피로</a>
		</b>
	</div>

	<hr>

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

	<c:if test="${sessionScope.loginUser != null }">
		<c:choose>
			<c:when test="${bmkd == false }">
				<!-- 북마크되어있지 않을 때만 -->
				<div
					style="position: fixed; left: 297px; bottom: 110px; z-index: 50; cursor: pointer;">
					<span><a title="북마크 등록" onclick="addBookmark(${h.r_no });"><img
							src="resources/img/bookmark.png"
							style="width: 33px; height: 31px;"></a></span>

				</div>
			</c:when>
			<c:otherwise>
				<!-- 북마크되어 있을 때는 -->
				<div
					style="position: fixed; left: 297px; bottom: 110px; z-index: 50; cursor: pointer;">
					<span><a title="북마크 해제"
						onclick="deleteBookmark(${h.r_no });"><img
							src="resources/img/bookmark_fill.png"
							style="width: 33px; height: 31px;"></a></span>

				</div>
			</c:otherwise>
		</c:choose>
	</c:if>

	<!-- 머리 게시글 영역 -->
	<table class="content" id="headInfo">
		<tr>
			<td id="cateInfo">${h.r_category }</td>
			<td style="font-size: 12pt; width: 60%;">${h.r_title }</td>
			<td colspan="3"
				style="font-family: sans-serif; text-align: right; width: 410px;"><fmt:formatDate
					value="${h.r_date }" type="both" dateStyle="long" timeStyle="short" /></td>
		</tr>
		<tr>
			<td colspan="2" style="font-family: sans-serif;"><img
				src="resources/img/${sessionScope.loginUser.u_photo }" width="20px"
				height="20px" style="display: inline;"> ${nick }</td>

			<td
				style="color: orange; text-align: right; font-family: sans-serif;"><a
				style="color: black; text-align: right; font-size: 12pt;"
				href="i_recipe.review?r_no=${h.r_no }">조회 ${h.r_seen } 리뷰
					(${h.r_rev_cnt })</a> <a style="font-size: 12pt;">별점</a> <c:set
					var="star" value="${h.r_star_avg }" /> <c:choose>
					<c:when test="${star == 5 }">
						<a style="font-weight: bold;">&#9733;&#9733;&#9733;&#9733;&#9733;</a>(${h.r_star_avg })
						</c:when>
					<c:when test="${star >= 4 }">
						<a style="font-weight: bold;">&#9733;&#9733;&#9733;&#9733;</a>(${h.r_star_avg })
						</c:when>
					<c:when test="${star >= 3 }">
						<a style="font-weight: bold;">&#9733;&#9733;&#9733;</a>(${h.r_star_avg })
						</c:when>
					<c:when test="${star >= 2 }">
						<a style="font-weight: bold;">&#9733;&#9733;</a>(${h.r_star_avg })
						</c:when>
					<c:otherwise>
						<a style="font-weight: bold;">&#9733;</a>(${h.r_star_avg })
						</c:otherwise>
				</c:choose></td>
		</tr>
	</table>
	<table style="position: absolute; right: 10px; top: 105px;">
		<tr>
			<c:if test="${h.r_writer == sessionScope.loginUser.u_id }">
				<td align="right"><button onclick="updateHead(${h.r_no });">수정</button></td>
			</c:if>
			<c:if
				test="${sessionScope.loginUser.u_role == 'm' || h.r_writer == sessionScope.loginUser.u_id }">
				<td align="right"><button onclick="deleteRcp(${h.r_no });">삭제</button></td>
			</c:if>
		</tr>
	</table>
	<hr>
	<br>
	<table class="content" id="headContent">
		<tr>
			<td style="font-size: 12pt;">${h.r_text }</td>
		</tr>
		<tr>
			<td><c:if test="${h.r_photo != 'defaultThumbnail.png'}">
					<c:choose>
						<c:when test="${h.r_example == 'O' }">
							<img src="${h.r_photo }" style="width: 50%; height: 50%;">
						</c:when>
						<c:otherwise>
							<img src="resources/img/${h.r_photo }"
								style="width: 50%; height: 50%;">
						</c:otherwise>
					</c:choose>
				</c:if>
			<td>
		</tr>
	</table>
	<!-- 꼬리 게시글 영역 -->
	<c:forEach var="tl" items="${h.r_tails }">
		<br>

		<br>
		<table>
			<tr>
				<td>
					<table class="content" id="tailContent">
						<tr>
							<td colspan="2" style="font-size: 12pt;">${tl.t_text }</td>
						</tr>
						<tr>
							<td><c:if test="${tl.t_photo != 'defaultThumbnail.png'}">
									<c:choose>
										<c:when test="${tl.t_photo == 'defaultThumbnail.png'}"></c:when>
										<c:when test="${tl.t_example == 'O' }">
											<img src="${tl.t_photo }" style="width: 50%; height: 50%;">
										</c:when>
										<c:otherwise>
											<img src="resources/img/${tl.t_photo }"
												style="width: 50%; height: 50%;">
										</c:otherwise>
									</c:choose>
								</c:if>
							<td>
							<td valign="bottom"><c:if
									test="${h.r_writer == sessionScope.loginUser.u_id }">
									<button onclick="updateTail(${tl.t_no});"
										style="position: absolute; right: 60px;">수정</button>

									<button onclick="deleteTail(${tl.t_no});"
										style="position: absolute; right: 10px;">삭제</button>
									<!-- 이 부분 버튼들은 나중에 작은 아이콘 같은 것으로 교체하면 좋을 것 같네요 -->
								</c:if></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:forEach>


	<!-- 꼬리 게시글 작성 영역 -->
	<!-- 본인의 게시글이고, 레시피가 완성되지 않은 상태여야만 보임 -->
	<c:if
		test="${h.r_writer == sessionScope.loginUser.u_id && h.r_ended == 'X' }">
		<h3 class="content">다음 단계 쓰기</h3>
		<form action="i_recipe.tail.write" name="tailForm"
			onsubmit="return tailCheck();" enctype="multipart/form-data"
			method="post">
			<input name="t_head" value="${h.r_no }" type="hidden">
			<table class="content" id="writeTail">
				<tr>
					<td colspan="2"><textarea name="t_text"></textarea></td>
				</tr>
				<tr>
					<td colspan="2"><input type="file" name="file">
						<button style="float: right;">작성</button></td>

				</tr>
			</table>
		</form>
	</c:if>
	<br>

	<!-- 레시피 완성 버튼 -->
	<!-- 본인의 게시글이어야만 보임 -->
	<c:if
		test="${h.r_writer == sessionScope.loginUser.u_id && h.r_ended == 'X'}">
		<table class="content">
			<tr>
				<td align="center">
					<button onclick="endRcp(${h.r_no});">레시피 완성!</button>
				</td>
			</tr>
		</table>
	</c:if>

	<br>

	<!-- 리뷰 테이블 -->
	<table>
		<tr>
			<td><a style="color: black; text-align: right;"
				href="i_recipe.review?r_no=${h.r_no }">조회 ${h.r_seen } 리뷰
					(${h.r_rev_cnt })</a></td>
		</tr>
	</table>
	<hr>

	<a name="target"> </a>
	<!-- 스크롤이동을 위한 앵커태그 -->

	<!-- 리스트형 게시판일 때 -->
	<c:if test="${!empty param.search }">
		<h3 align="center">
			"<i style="color: orange;">${param.search }</i>"(으)로 검색한 결과
		</h3>
	</c:if>
	<table id="contentTbl_1" align="center" border="1" style="width: 100%">
		<thead>
			<tr>
				<th>글번호</th>
				<th>썸네일</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>별점</th>
				<th>작성자</th>
				<th>등록일자</th>
				<th>조회수</th>
			</tr>
		</thead>
		<c:forEach var="rc" items="${rcps }">
			<c:choose>
				<c:when test="${h.r_no == rc.r_no }">
					<!-- 해당 게시물에만 색깔처리 -->
					<tr style="background-color: Antiquewhite;">
						<td align="left" width="5%" style="color: orange;">${rc.r_no }</td>
						<c:choose>
							<c:when test="${rc.r_photo == 'defaultThumbnail.png'}">
								<td align="center" width="5%"><img width="50px"
									height="50px" src="resources/img/s_defaultThumbnail.png"></td>
							</c:when>
							<c:when test="${rc.r_example == 'O' }">
								<td align="center" width="5%"><img width="50px"
									height="50px" src="${rc.r_photo }"></td>
							</c:when>
							<c:otherwise>
								<td align="center" width="5%"><img width="50px"
									height="50px" src="resources/img/s_${rc.r_photo }"></td>
							</c:otherwise>
						</c:choose>
						<td align="left" width="10%">[ ${rc.r_category } ]</td>
						<td align="left" width="40%" style="font: bold"><a href="#"><b>${rc.r_title }</b></a>
							<c:if test="${rc.r_ended == 'O' }">
								<span> [${rc.r_rev_cnt }] </span>
								<span style="color: orange">[완성]</span>
							</c:if></td>
						<c:choose>
							<c:when test="${rc.r_ended == 'O' }">
								<c:choose>
									<c:when test="${rc.r_star_avg == null}">
										<td align="left" width="10%">별점 0.00</td>
									</c:when>
									<c:otherwise>
										<td align="left" width="10%">별점 ${rc.r_star_avg }</td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td width="10%"></td>
							</c:otherwise>
						</c:choose>
						<td align="right" width="10%" style="color: gray">${rc.r_writer }</td>
						<td align="right" align="center" style="color: gray"><fmt:formatDate
								value="${rc.r_date }" type="date" /></td>
						<td align="right" width="8%" style="color: gray">조회
							${rc.r_seen }</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td align="left" width="5%" style="color: orange;">${rc.r_no }</td>
						<c:choose>
							<c:when test="${rc.r_photo == 'defaultThumbnail.png'}">
								<td align="center" width="5%"><img width="50px"
									height="50px" src="resources/img/s_defaultThumbnail.png"></td>
							</c:when>
							<c:when test="${rc.r_example == 'O' }">
								<td align="center" width="5%"><img width="50px"
									height="50px" src="${rc.r_photo }"></td>
							</c:when>
							<c:otherwise>
								<td align="center" width="5%"><img width="50px"
									height="50px" src="resources/img/s_${rc.r_photo }"></td>
							</c:otherwise>
						</c:choose>
						<td align="left" width="10%">[ ${rc.r_category } ]</td>

						<c:choose>
							<c:when test="${!empty param.category }">
								<c:set var="c" value="&category=${param.category }" />
							</c:when>
							<c:otherwise>
								<c:set var="c" value="" />
							</c:otherwise>
						</c:choose>
						<td align="left" width="40%" style="font: bold"><a
							href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=${param.from }${c }${s }"><b>${rc.r_title }</b></a>
							<c:if test="${rc.r_ended == 'O' }">
								<span> [${rc.r_rev_cnt }] </span>
								<span style="color: orange">[완성]</span>
							</c:if></td>
						<c:choose>
							<c:when test="${rc.r_ended == 'O' }">
								<c:choose>
									<c:when test="${rc.r_star_avg == null}">
										<td align="left" width="10%">별점 0.00</td>
									</c:when>
									<c:otherwise>
										<td align="left" width="10%">별점 ${rc.r_star_avg }</td>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<td width="10%"></td>
							</c:otherwise>
						</c:choose>
						<td align="right" width="10%" style="color: gray">${rc.r_writer }</td>
						<td align="right" align="center" style="color: gray"><fmt:formatDate
								value="${rc.r_date }" type="date" /></td>
						<td align="right" width="8%" style="color: gray">조회
							${rc.r_seen }</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</table>

	<br>

	<c:choose>
		<c:when test="${param.from == 'my' }">
			<c:set var="f" value="&from=my" />
		</c:when>
		<c:when test="${param.from == 'bmk' }">
			<c:set var="f" value="&from=bmk" />
		</c:when>
		<c:when test="${param.from == 'cat' }">
			<c:set var="f" value="&from=cat&category=${param.category }" />
		</c:when>
		<c:otherwise>
			<c:set var="f" value="&from=ircp" />
		</c:otherwise>
	</c:choose>

	<!-- 페이징 처리를 위한 변수 세팅  -->
	<c:set var="conNum" value="${conNum }" />
	<c:set var="page" value="${(empty param.p)? 1 : conNum }" />
	<c:set var="startNum" value="${page - (page-1) % 10 }" />
	<c:set var="allPageCount" value="${allPageCount }" />

	<!-- 페이징 -->
	<ul style="text-align: center;">

		<!-- 첫 페이지 -->
		<li style="display: inline-block;"><a
			href="i_recipe.rcp.detail?p=1&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">◀◀</a></li>
		<!-- 이전 페이지 -->
		<c:if test="${startNum != 1 }">
			<li style="display: inline-block;"><a
				href="i_recipe.rcp.detail?p=${startNum-10 }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">이전</a></li>
		</c:if>

		<!-- 페이징 번호 -->
		<c:choose>
			<c:when test="${startNum+9 > allPageCount }">
				<c:choose>
					<c:when test="${allPageCount>0 }">
						<c:forEach var="i" begin="0" end="${(allPageCount%10)-1 }">
							<li
								style="display: inline-block; color: ${(curPage==startNum+i)?'#FF5E00':'black'};">
								<a
								href="i_recipe.rcp.detail?p=${startNum+i }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">${startNum+i }</a>
							</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="0">
							<li
								style="display: inline-block; color: ${(curPage==startNum+i)?'#FF5E00':'black'};">
								<a
								href="i_recipe.rcp.detail?p=${startNum+i }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">${startNum+i }</a>
							</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="0" end="9">
					<li
						style="display: inline-block; color: ${(curPage==startNum+i)?'#FF5E00':'black'};">
						<a
						href="i_recipe.rcp.detail?p=${startNum+i }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">${startNum+i }</a>
					</li>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<!-- 다음 페이지 -->
		<c:if test="${startNum+9 < allPageCount }">
			<li style="display: inline-block;"><a
				href="i_recipe.rcp.detail?p=${startNum+10 }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">다음</a></li>
		</c:if>

		<!-- 마지막 페이지 -->
		<li style="display: inline-block;"><a
			href="i_recipe.rcp.detail?p=${allPageCount }&shape=${shape }&sort=${sort }&r_no=${param.r_no }${f }${s }">▶▶</a></li>

	</ul>

	<br>



</body>
</html>