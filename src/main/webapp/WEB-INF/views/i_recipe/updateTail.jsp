<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>i_recipe/recipeDetail</title>
</head>
<body>
	<c:set var="no" value="${h.r_no }" />
	<!-- 머리 게시글 영역 -->
	<table class="content" id="headInfo">
		<tr>
			<td style="color: orange;">${h.r_no }</td>
			<td style="font: bold 30pt;">${h.r_title }</td>
			<td align="right"><fmt:formatDate value="${h.r_date }"
					type="both" dateStyle="long" timeStyle="short" /></td>
			<td style="color: gray">조회 ${h.r_seen }</td>
		</tr>
		<tr>
			<td colspan="2" style="color: gray;">${nick }</td>
			<td align="right">${h.r_category }</td>
			<td align="center"><a href="i_recipe.review?r_no=${h.r_no }">리뷰
					${h.r_rev_cnt }</a> 별점 ${h.r_star_avg }</td>

			<c:if test="${sessionScope.loginUser != null }">
				<c:choose>
					<c:when test="${bmkd == false }">
						<!-- 북마크되어있지 않을 때만 -->
						<td align="right"><button onclick="addBookmark(${h.r_no });">북마크
								등록</button></td>
					</c:when>
					<c:otherwise>
						<!-- 북마크되어 있을 때는 -->
						<td align="right"><button
								onclick="deleteBookmark(${h.r_no });">북마크 해제</button></td>
					</c:otherwise>
				</c:choose>
			</c:if>
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
			<td>${h.r_text }</td>
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
	<!-- 수정 요청이 들어온 꼬리만 수정, 아닌 것은 그냥 보여주기 -->
	<c:forEach var="tl" items="${h.r_tails }">
		<c:choose>
			<c:when test="${tl.t_no == param.t_no }">
				<br>
				<hr>
				<br>
				<h3>단계 수정</h3>
				<form action="i_recipe.update.tail" name="tailForm"
					onsubmit="return tailCheck();" enctype="multipart/form-data"
					method="post">
					<input name="t_no" value="${param.t_no }" type="hidden"> <input
						name="p" value="1" type="hidden"> <input
						name="shape" value="gallery" type="hidden"> <input
						name="sort" value="r_no" type="hidden"> <input
						name="from" value="ircp" type="hidden">
					<table id="writeTail">
						<tr>
							<td colspan="2"><textarea name="t_text">${tl.t_text }</textarea></td>
						</tr>
						<tr>
							<td><input type="file" name="file"> <br>사진을
								첨부하지 않으면 기존 사진으로 유지됩니다.
							<td>
							<td><button>수정</button></td>
						</tr>
					</table>
				</form>
			</c:when>
			<c:otherwise>
				<br>
				<hr>
				<br>
				<table>
					<tr>
						<td>
							<table id="tailContent">
								<tr>
									<td>${tl.t_text }</td>
								</tr>
								<tr>
									<td><c:if test="${tl.t_photo != 'defaultThumbnail.png' }">
											<c:choose>
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
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
	</c:forEach>


	<!-- 꼬리 게시글 작성 영역 -->
	<!-- 본인의 게시글이고, 레시피가 완성되지 않은 상태여야만 보임 -->
	<c:if
		test="${h.r_writer == sessionScope.loginUser.u_id && h.r_ended == 'X' }">

	</c:if>



</body>
</html>