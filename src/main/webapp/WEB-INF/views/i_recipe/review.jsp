<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>i_recipe/review.jsp</title>
<style type="text/css">
.star-rating {
	border: none;
	display: flex;
	flex-direction: row-reverse;
	font-size: 2em;
	justify-content: space-around;
	padding: 0 .2em;
	text-align: center;
	width: 5em;
}

.star-rating input {
	display: none;
}

.star-rating label {
	color: #ccc;
	cursor: pointer;
}

.star-rating :checked ~ label {
	color: #f90;
}

.star-rating label:hover, .star-rating label:hover ~ label {
	color: #fc0;
}

/* explanation */
article {
	background-color: #ffe;
	box-shadow: 0 0 1em 1px rgba(0, 0, 0, .25);
	color: #006;
	font-family: cursive;
	font-style: italic;
	margin: 4em;
	max-width: 30em;
	padding: 2em;
}

textarea {
	width: 1110px;
	height: 200px;
	padding: 10px;
	box-sizing: border-box;
	border: none;
	font-size: 20px;
	font-weight: 500;
	resize: none;
}

.reBtn {
	border: none;
	border-radius: 1px;
	background-color: #D8D8D8;
	width: 100px;
	height: 30px;
}
</style>
</head>
<body>
	<!-- 레시피 제목 / 별점 평균 / 리뷰 수 -->
	<div align="center">
		<h1>${h.r_title }</h1>
	</div>
	<hr>
	<table>
		<tr>
			<td>
				<h1 style="margin-left:10px; margin-bottom: 10px;">리뷰</h1>
			</td>
		</tr>
		<tr>
			<td style="padding-left:10px; color: orange; font-size: 15pt;">
				<c:set var="star" value="${h.r_star_avg }" />
			<c:choose>
				<c:when test="${star == 5 }">
							&#9733;&#9733;&#9733;&#9733;&#9733;(${h.r_star_avg })
						</c:when>
				<c:when test="${star >= 4 }">
							&#9733;&#9733;&#9733;&#9733;(${h.r_star_avg })
						</c:when>
				<c:when test="${star >= 3 }">
							&#9733;&#9733;&#9733;(${h.r_star_avg })
						</c:when>
				<c:when test="${star >= 2 }">
							&#9733;&#9733;(${h.r_star_avg })
						</c:when>
				<c:otherwise>
							&#9733;(${h.r_star_avg })
						</c:otherwise>
			</c:choose>
			</td>
			<td style="color: orange; font-size: 15pt;">리뷰 ${h.r_rev_cnt }개</td>
		</tr>
	</table>
	

	<!-- 리뷰 작성 - 리뷰를 이미 달지 않았을 경우에만 작성 가능 -->
	<c:if
		test="${h.r_writer != sessionScope.loginUser.u_id && revd == false && h.r_ended == 'O' && ph == true }">
		<form action="i_recipe.write.review" method="post" name="reviewForm"
			onsubmit="return reviewCheck();">
			<input name="token" value="${token }" type="hidden"> <input
				name="rv_rcp" value="${h.r_no }" type="hidden">
			<table border="1"
				style="border-collapse: collapse; margin-left: 10px;">
				<tr>
					<td class="star-rating"><input type="radio" id="5-stars"
						name="rv_star" value="5" /> <label for="5-stars" class="star">&#9733;</label>
						<input type="radio" id="4-stars" name="rv_star" value="4" /> <label
						for="4-stars" class="star">&#9733;</label> <input type="radio"
						id="3-stars" name="rv_star" value="3" /> <label for="3-stars"
						class="star">&#9733;</label> <input type="radio" id="2-stars"
						name="rv_star" value="2" /> <label for="2-stars" class="star">&#9733;</label>
						<input type="radio" id="1-star" name="rv_star" value="1" /> <label
						for="1-star" class="star">&#9733;</label></td>
				</tr>
				<tr style="border-top:none; border-bottom: none;">
					<td><textarea name="rv_text"></textarea></td>
				</tr>
				<tr>
					<td align="right" style="padding: 5px;">
						<button class="reBtn">등록</button>
					</td>
				</tr>
			</table>
		</form>
	</c:if>
	
	<hr>

	<c:if test="${ph == false }">
		레시피에 사진이 등록되지 않아 리뷰 작성이 불가합니다.
	</c:if>
	<!-- 리뷰 목록 -->
	<c:forEach var="rev" items="${reviews }">
		<table>
			<tr>
				<td style="color: gray">${rev.rv_writer }</td>
				<td style="color: orange; position: absolute; right: 10px;"><c:set
						var="star" value="${rev.rv_star }" /> <c:choose>
						<c:when test="${star == 5 }">
							&#9733;&#9733;&#9733;&#9733;&#9733;(${rev.rv_star })
						</c:when>
						<c:when test="${star == 4 }">
							&#9733;&#9733;&#9733;&#9733;(${rev.rv_star })
						</c:when>
						<c:when test="${star == 3 }">
							&#9733;&#9733;&#9733;(${rev.rv_star})
						</c:when>
						<c:when test="${star == 2 }">
							&#9733;&#9733;(${rev.rv_star})
						</c:when>
						<c:otherwise>
							&#9733;(${rev.rv_star })
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td style="font-size: 20pt;">${rev.rv_text }</td>
			</tr>
		</table>
		<hr>
	</c:forEach>

</body>
</html>