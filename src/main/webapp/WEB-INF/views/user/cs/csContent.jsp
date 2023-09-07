<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/csContent.jsp</title>
</head>
<body>

	<div align="center">
		<div align="left" style="width: 915px; margin-bottom: 10px;">
			<b>
				<a style="font-size:14pt; font-family: sans-serif; color: #aaa9a9;" href="cs">고객센터로</a>
			</b>
		</div>
	</div>

	<div align="center">
		<div align="left" style="width: 80%;">

			<form method="post" action="cs.update.page">
				<div>
					<div align="left" style="display: inline;">${csc.cs_title }</div>
					<div style="display: inline; float: right;">
						${sessionScope.loginUser.u_nickname } <img
							src="resources/img/${sessionScope.loginUser.u_photo }"
							width="20px" height="20px" style="display: inline;">
					</div>
					<div>
						<div style="display: inline;">카테고리: ${csc.cs_category }</div>
						<div style="display: inline; float: right;">
							<fmt:formatDate value="${csc.cs_when }"
								pattern="yyyy-MM-dd a hh:mm" />
						</div>
						<div style="display: inline; float: right;">조회:
							${csc.cs_views } &nbsp;&nbsp;</div>
					</div>

					<div align="left" style="border-top: 1px solid #A6A6A6;">
						${csc.cs_text }</div>

					<div align="right">
						<input type="hidden" value="${csc.cs_num }" name="cs_num">
						<input type="hidden" value="${curPage }" name="curPage">
						<button>수정</button>
						<button type="button" onclick="csRemove(${csc.cs_num })">삭제</button>
					</div>
				</div>
			</form>

			<!-- 댓글 목록 / 삭제 -->
			<div align="left"
				style="border: 1px solid #A6A6A6; margin-top: 20px; height: 30px;">
				<b>댓글 [${replyCount }]</b>
			</div>
			<c:forEach var="reply" items="${csr }">
				<form action="csReply.remove" method="post">
					<div>
					<c:choose>
						<c:when test="${sessionScope.loginUser.u_id == reply.csr_writer}">
					
						<div align="left" style="margin-top: 20px; height: 30px;">
							<img src="resources/img/${sessionScope.loginUser.u_photo }" width="20px"
								height="20px" style="display: inline;">
							${reply.csr_nickname } /
							<fmt:formatDate value="${reply.csr_when }"
								pattern="yyyy-MM-dd a hh:mm" />
						</div>
						<div>${reply.csr_text }</div>
						
						</c:when>
						<c:otherwise>
						
						<div align="left" style="margin-top: 20px; height: 30px;">
							<img src="resources/img/${reply.csr_photo }" width="20px"
								height="20px" style="display: inline;">
							${reply.csr_nickname } /
							<fmt:formatDate value="${reply.csr_when }"
								pattern="yyyy-MM-dd a hh:mm" />
						</div>
						<div>${reply.csr_text }</div>
						
						</c:otherwise>						
					</c:choose>
						
						<div align="right">
							<input type="hidden" name="token" value="${token }"> <input
								type="hidden" name="p" value="${curPage }"> <input
								type="hidden" name="csr_num" value="${reply.csr_num }">
							<input type="hidden" name="csr_cs_num"
								value="${reply.csr_cs_num }">
							<c:if
								test="${reply.csr_writer == sessionScope.loginUser.u_id || sessionScope.loginUser.u_role == 'm' }">
								<button type="submit" onclick="return csReplyRemove()">삭제</button>
							</c:if>
						</div>
					</div>
				</form>
			</c:forEach>
			<!-- 댓글 등록 -->
			<form action="cs.reply.write" method="post" name="csReplyForm"
				onsubmit="return csReplyCheck();">
				<div style="margin-top: 5px;">
					<input type="hidden" name="token" value="${token }"> <input
						type="hidden" name="p" value="${curPage }"> <input
						type="hidden" name="csr_cs_num" value="${csc.cs_num }"> <input
						type="hidden" name="csr_writer"
						value="${sessionScope.loginUser.u_id }">
					<textarea name="csr_text"
						style="resize: none; width: 695px; height: 50px;"></textarea>
					<div align="right">
						<button>등록</button>
					</div>
				</div>
			</form>


		</div>
	</div>
	<br>

	<!-- 카테고리 -->
	<div align="center">
		<table class="cs">
			<tr align="center">
				<td align="center" style="border-right: 1px solid #A6A6A6;">
					<button onclick="csCategory('')" value="">전체</button>
				</td>
				<td align="center" style="border-right: 1px solid #A6A6A6;">
					<button onclick="csCategory('컨텐츠')" value="컨텐츠">컨텐츠</button>
				</td>
				<td align="center" style="border-right: 1px solid #A6A6A6;">
					<button onclick="csCategory('회원')" value="회원">회원</button>
				</td>
				<td align="center" style="border-right: 1px solid #A6A6A6;">
					<button onclick="csCategory('사이트이용')" value="사이트이용">사이트이용</button>
				</td>
				<td align="center" style="border-right: 1px solid #A6A6A6;">
					<button onclick="csCategory('기타')" value="기타">기타</button>
				</td>
			</tr>
		</table>
	</div>
	<br>

	<!-- 고객센터 글 -->
	<div align="center">
		<table align="center" width="80%"
			style="border-top: 1px solid #A6A6A6; border-bottom: 1px solid #A6A6A6;">
			<tr height="50px" style="background-color: #BDBDBD;">
				<th>글번호</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>닉네임</th>
				<th>조회수</th>
				<th>날짜</th>
			</tr>
			<c:forEach var="cs" items="${csposts }">
				<c:if test="${cs.cs_writer == sessionScope.loginUser.u_id }">
					<c:choose>
						<c:when test="${param.cs_num == cs.cs_num}">
							<tr height="50px" bgcolor="#D8D8D8">
								<td align="center">${cs.cs_num }</td>
								<c:if test="${cs.cs_category == '컨텐츠' }">
									<td align="center">컨텐츠</td>
								</c:if>
								<c:if test="${cs.cs_category == '회원' }">
									<td align="center">회원</td>
								</c:if>
								<c:if test="${cs.cs_category == '사이트이용' }">
									<td align="center">사이트이용</td>
								</c:if>
								<c:if test="${cs.cs_category == '기타' }">
									<td align="center">기타</td>
								</c:if>
								<td align="center" style="width: 300px; cursor: pointer;"
									onclick="csContent(${cs.cs_num},${curPage });">
									${cs.cs_title } <c:if test="${cs.cs_reply != 0 }">
										<b>[${cs.cs_reply }]</b>
									</c:if>
								</td>
								<td align="center" style="width: 100px;">${sessionScope.loginUser.u_nickname }</td>
								<td align="center" style="width: 50px;">${cs.cs_views }</td>
								<td align="center"><fmt:formatDate value="${cs.cs_when }"
										pattern="yyyy-MM-dd a hh:mm:ss" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr height="50px">
								<td align="center">${cs.cs_num }</td>
								<c:if test="${cs.cs_category == '컨텐츠' }">
									<td align="center">컨텐츠</td>
								</c:if>
								<c:if test="${cs.cs_category == '회원' }">
									<td align="center">회원</td>
								</c:if>
								<c:if test="${cs.cs_category == '사이트이용' }">
									<td align="center">사이트이용</td>
								</c:if>
								<c:if test="${cs.cs_category == '기타' }">
									<td align="center">기타</td>
								</c:if>
								<td align="center" style="width: 300px;"
									onclick="csContent(${cs.cs_num},${curPage });">
									${cs.cs_title } <c:if test="${cs.cs_reply != 0 }"> [${cs.cs_reply }] </c:if>
								</td>
								<td align="center" style="width: 100px;">${sessionScope.loginUser.u_nickname }</td>
								<td align="center" style="width: 50px;">${cs.cs_views }</td>
								<td align="center"><fmt:formatDate value="${cs.cs_when }"
										pattern="yyyy-MM-dd a hh:mm:ss" /></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
		</table>
	</div>

	<!-- 글 작성 -->
	<div align="center" style="margin-top: 15px;">
		<div align="right" style="width: 700px;">
			<button onclick="csWrite();">글쓰기</button>
		</div>
	</div>

	<!-- 검색 -->
	<form action="cs.search" method="get">
		<table align="center">
			<tr>
				<td><select name="searchType">
						<c:if test="${searchType == 'cs_title' }">
							<option value="cs_title" selected>제목</option>
							<option value="cs_text">내용</option>
						</c:if>
						<c:if test="${searchType == 'cs_text' }">
							<option value="cs_title">제목</option>
							<option value="cs_text" selected>내용</option>
						</c:if>
				</select></td>
				<td><input type="text" name="searchCs" maxlength="20"
					value="${searchCs }"></td>
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
		<li style="display: inline-block;" onclick="csPaging(${1});">◀◀</li>

		<!-- 이전 페이지 -->
		<c:if test="${startNum != 1 }">
			<li style="display: inline-block;"
				onclick="csPaging(${startNum-10 });">이전</li>
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
								onclick="csPaging(${startNum+i });">${startNum+i }</li>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="0">
							<li
								style="display: inline-block;
							color: ${(curPage==startNum+i)?'#FF5E00':'black'};"
								onclick="csPaging(${startNum+i });">${startNum+i }</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="0" end="9">
					<li
						style="display: inline-block;
					color: ${(curPage==startNum+i)?'#FF5E00':'black'}; "
						onclick="csPaging(${startNum+i });">${startNum+i }</li>
				</c:forEach>
			</c:otherwise>
		</c:choose>

		<!-- 다음 페이지 -->
		<c:if test="${startNum+9 < allPageCount }">
			<li style="display: inline-block;"
				onclick="csPaging(${startNum+10 });">다음</li>
		</c:if>

		<!-- 마지막 페이지 -->
		<li style="display: inline-block;"
			onclick="csPaging(${allPageCount });">▶▶</li>

	</ul>

	<hr>

</body>
</html>