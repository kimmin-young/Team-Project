<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HOME</title>
<style type="text/css">
#recipeTitle {
	display: inline-block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 150px;
}
</style>
</head>
<body>
	<table id="contentTbl">
		<tr id="BR_tr">
			<td>
				<table style="border-top-right-radius: 10px; width: 1135px; height: 100%; border-collapse: collapse;" border="1">
					<tr>
						<td style="text-align:center; font-size: 15pt; padding:10px; background-color: #D8D8D8;">BEST RECIPE</td>
					</tr>
					<tr>
						<td><jsp:include page="bestR.jsp" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 가운데 조회수순 레시피 --> <c:set var="i" value="0" /> <c:set var="j"
					value="5" />
				<table style="width: 100%; margin-left: 10px;" id="RTbl">
					<tr>
						<td colspan="4" style="font-size:15pt; display: inline-block;">많이 본
								레시피</td>
						<td><a
							href="i_recipe.go?shape=gallery&sort=r_seen" style="font-family:monospace; font-size:10pt; position: absolute; top:400px; right: 20px;">전체보기</a></td>
					</tr>
					<c:forEach var="rc" items="${srcp }">
						<c:choose>
							<c:when test="${rc != null && fn:length(srcp) > 0 }">

								<c:if test="{i%j == 0}">
									<!-- 첫 요소이면 tr 여는태그 넣기 -->
									<tr>
								</c:if>
								<td>
									<table>
										<tr>
											<c:choose>
												<c:when test="${rc.r_photo == 'defaultThumbnail.png'}">
													<td><a
														href="i_recipe.rcp.detail?p=1&sort=r_seen&r_no=${rc.r_no }&from=ircp"><img
															class="Rimg" src="resources/img/s_defaultThumbnail.png"></a></td>
												</c:when>
												<c:when test="${rc.r_example == 'O' }">
													<td><a
														href="i_recipe.rcp.detail?p=1&sort=r_seen&r_no=${rc.r_no }&from=ircp"><img
															class="Rimg" height="100px" width="100px"
															src="${rc.r_photo }"></a></td>
												</c:when>
												<c:otherwise>
													<td><a
														href="i_recipe.rcp.detail?p=1&sort=r_seen&r_no=${rc.r_no }&from=ircp"><img
															class="Rimg" src="resources/img/s_${rc.r_photo }"></a></td>
												</c:otherwise>
											</c:choose>

										</tr>
										<tr>
											<td id="recipeTitle"><a
												href="i_recipe.rcp.detail?p=1&sort=r_seen&r_no=${rc.r_no }&from=ircp"><b>${rc.r_title }</b></a>
											</td>
										</tr>
										<tr>
											<td class="listSubText" style="color: orange; font-size: 8pt;">별점 <c:set
													var="star" value="${rc.r_star_avg }" /> <c:choose>
													<c:when test="${star == 5 }">
							&#9733;&#9733;&#9733;&#9733;&#9733;(${rc.r_star_avg })
						</c:when>
													<c:when test="${star == 4 }">
							&#9733;&#9733;&#9733;&#9733;(${rc.r_star_avg })
						</c:when>
													<c:when test="${star == 3 }">
							&#9733;&#9733;&#9733;(${rc.r_star_avg })
						</c:when>
													<c:when test="${star == 2 }">
							&#9733;&#9733;(${rc.r_star_avg })
						</c:when>
													<c:otherwise>
							&#9733;(${rc.r_star_avg })
						</c:otherwise>
												</c:choose></td>
										</tr>
										<tr>
											<td class="listSubText" style="color: gray; font-size: 8pt;">${rc.r_writer }</td>
										</tr>
										<tr>
											<td class="listSubText" style="color: gray; font-size: 8pt;"><fmt:formatDate
													value="${rc.r_date }" type="date" /> 조회 ${rc.r_seen }</td>
										</tr>
									</table>
								</td>
								<c:if test="${i%j == j-1}">
									<!-- 마지막 요소이면 tr 닫는태그 넣기 -->
									</tr>
								</c:if>
								<c:set var="i" value="${i+1}" />

							</c:when>
							<c:otherwise>
								<tr>
									<td>존재하지 않습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</table>

			</td>
		</tr>
	</table>


</body>
</html>