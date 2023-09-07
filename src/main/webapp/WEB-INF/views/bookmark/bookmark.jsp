<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bookmark.jsp</title>
<link rel="stylesheet"
   href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<style type="text/css">
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
   padding-top: 40px;
}

.listSubText {
   font-size: 8px;
}

#recipeTitle {
   display: inline-block;
   overflow: hidden;
   text-overflow: ellipsis;
   white-space: nowrap;
   width: 100px;
}

.bi-list {
   font-size: 15pt;
   font-weight: 900;
}

.bi-image {
   font-size: 15pt;
   font-weight: 900;
}
</style>
</head>
<body>
   <c:set var="i" value="0" />
   <c:set var="j" value="5" />
   <c:set var="from" value="bmk" />
   <c:choose>
      <c:when test="${!empty param.search }">
         <c:set var="s" value="&rcp=${param.rcp }&search=${param.search }" />
      </c:when>
      <c:otherwise>
         <c:set var="s" value="#" />
      </c:otherwise>
   </c:choose>

   <!-- 상단 버튼 -->
   <table align="center" id="btnTbl">
      <tr>
         <td></td>
         <td align="right" style="width: 809px;"><c:choose>
               <c:when test="${sort == 'r_no'}">
                  <form method="get" style="margin-bottom: 1px;">
                     <input type="hidden" name="p" value="${curPage }">
                     <input type="hidden" name="shape" value="${shape }">
                     <c:if test="${!empty param.rcp }">
                        <input type="hidden" name="rcp" value="${param.rcp }">
                     </c:if>
                     <c:if test="${!empty param.search }">
                        <input type="hidden" name="search" value="${param.search }">
                     </c:if>
                     <select name="sort">
                        <option value="r_no" selected>등록순</option>
                        <option value="r_star_avg">별점순</option>
                        <option value="r_seen">조회수순</option>
                     </select>
                     <button onclick="changeSort(p, ort, shape);">정렬</button>
                  </form>
               </c:when>
               <c:when test="${sort == 'r_star_avg'}">
                  <form method="get">
                     <input type="hidden" name="p" value="${curPage }">
                     <input type="hidden" name="shape" value="${shape }">
                     <c:if test="${!empty param.rcp }">
                        <input type="hidden" name="rcp" value="${param.rcp }">
                     </c:if>
                     <c:if test="${!empty param.search }">
                        <input type="hidden" name="search" value="${param.search }">
                     </c:if>
                     <select name="sort">
                        <option value="r_no">등록순</option>
                        <option value="r_star_avg" selected>별점순</option>
                        <option value="r_seen">조회수순</option>
                     </select>
                     <button onclick="changeSort(p, sort, shape);">정렬</button>
                  </form>
               </c:when>
               <c:otherwise>
                  <form method="get">
                     <input type="hidden" name="p" value="${curPage }">
                     <input type="hidden" name="shape" value="${shape }">
                     <c:if test="${!empty param.rcp }">
                        <input type="hidden" name="rcp" value="${param.rcp }">
                     </c:if>
                     <c:if test="${!empty param.search }">
                        <input type="hidden" name="search" value="${param.search }">
                     </c:if>
                     <select name="sort">
                        <option value="r_no">등록순</option>
                        <option value="r_star_avg">별점순</option>
                        <option value="r_seen" selected>조회수순</option>
                     </select>
                     <button onclick="changeSort(p, sort, shape);">정렬</button>
                  </form>
               </c:otherwise>
            </c:choose></td>
         <td align="right"><c:choose>
               <c:when test="${shape == 'list'}">
                  <!-- 리스트형일때 -->
                  <button>
                     <i class="bi bi-list"></i>
                  </button>
                  <button onclick="changeShape('gallery');">
                     <i class="bi bi-image"></i>
                  </button>
               </c:when>
               <c:otherwise>
                  <!-- 갤러리(기본)일때 -->
                  <button onclick="changeShape('list');">
                     <i class="bi bi-list"></i>
                  </button>
                  <button>
                     <i class="bi bi-image"></i>
                  </button>
               </c:otherwise>
            </c:choose></td>
      </tr>
   </table>

   <!-- 게시판 본체 -->
   <c:choose>
      <c:when test="${shape == 'list'}">
         <!-- 리스트형 게시판일 때 -->
         <table id="contentTbl_1" align="center" border="1">
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
               <tr>
                  <td align="center" width="5%" style="color: orange;">${rc.r_no }</td>
                  <c:choose>
                     <c:when test="${rc.r_photo == 'defaultThumbnail.png'}">
                        <td width="7%" align="center"><img width="50px"
                           height="50px" src="resources/img/s_defaultThumbnail.png"></td>
                     </c:when>
                     <c:when test="${rc.r_example == 'O' }">
                        <td width="7%" align="center"><img width="50px"
                           height="50px" src="${rc.r_photo }"></td>
                     </c:when>
                     <c:otherwise>
                        <td width="7%" align="center"><img width="50px"
                           height="50px" src="resources/img/s_${rc.r_photo }"></td>
                     </c:otherwise>
                  </c:choose>
                  <td align="center" width="7%">[ ${rc.r_category } ]</td>
                  <td align="left" width="40%" style="font: bold"><a
                     href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=bmk${s }"><b>${rc.r_title }</b></a>
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
            </c:forEach>
         </table>
      </c:when>
      <c:otherwise>
         <!-- 갤러리형 게시판일 때(기본) -->
         <table align="center">
            <c:forEach var="rc" items="${rcps }">
               <c:choose>
                  <c:when test="${rc != null && fn:length(rcps) > 0 }">

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
                                       href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=bmk${s }">
                                          <img class="Rimg"
                                          src="resources/img/s_defaultThumbnail.png">
                                    </a></td>
                                 </c:when>
                                 <c:when test="${rc.r_example == 'O' }">
                                    <td><a
                                       href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=bmk${s }">
                                          <img width="100px" height="100px" class="Rimg"
                                          src="${rc.r_photo }">
                                    </a></td>
                                 </c:when>
                                 <c:otherwise>
                                    <td><a
                                       href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=bmk${s }">
                                          <img class="Rimg" src="resources/img/s_${rc.r_photo }">
                                    </a></td>
                                 </c:otherwise>
                              </c:choose>

                           </tr>
                           <tr>
                              <td id="recipeTitle"><a
                                 href="i_recipe.rcp.detail?p=${curPage }&shape=${shape }&sort=${sort }&r_no=${rc.r_no }&from=bmk${s }">
                                    <b>${rc.r_title }</b>
                              </a></td>
                           </tr>
                           <tr>
                              <td class="listSubText" style="color: orange;">별점
                                 ${rc.r_star_avg }</td>
                           </tr>
                           <tr>
                              <td class="listSubText" style="color: gray;">${rc.r_writer }</td>
                           </tr>
                           <tr>
                              <td class="listSubText" style="color: gray;"><fmt:formatDate
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
      </c:otherwise>
   </c:choose>



   <br>

   <!-- 페이징 처리를 위한 변수 세팅  -->
   <c:set var="conNum" value="${conNum }" />
   <c:set var="page" value="${(empty param.p)? 1 : conNum }" />
   <c:set var="startNum" value="${page - (page-1) % 10 }" />
   <c:set var="allPageCount" value="${allPageCount }" />

   <!-- 페이징 -->
   <ul style="text-align: center;">

      <!-- 첫 페이지 -->
      <li style="display: inline-block;"><a
         href="bookmark.page?p=1&shape=${shape }&sort=${sort }${f }${s }">◀◀</a></li>
      <!-- 이전 페이지 -->
      <c:if test="${startNum != 1 }">
         <li style="display: inline-block;"><a
            href="bookmark.page?p=${startNum-10 }&shape=${shape }&sort=${sort }${f }${s }">이전</a></li>
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
                        href="bookmark.page?p=${startNum+i }&shape=${shape }&sort=${sort }${f }${s }">${startNum+i }</a>
                     </li>
                  </c:forEach>
               </c:when>
               <c:otherwise>
                  <c:forEach var="i" items="0">
                     <li
                        style="display: inline-block; color: ${(curPage==startNum+i)?'#FF5E00':'black'};">
                        <a
                        href="bookmark.page?p=${startNum+i }&shape=${shape }&sort=${sort }${f }${s }">${startNum+i }</a>
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
                  href="bookmark.page?p=${startNum+i }&shape=${shape }&sort=${sort }${f }${s }">${startNum+i }</a>
               </li>
            </c:forEach>
         </c:otherwise>
      </c:choose>

      <!-- 다음 페이지 -->
      <c:if test="${startNum+9 < allPageCount }">
         <li style="display: inline-block;"><a
            href="bookmark.page?p=${startNum+10 }&shape=${shape }&sort=${sort }${f }${s }">다음</a></li>
      </c:if>

      <!-- 마지막 페이지 -->
      <li style="display: inline-block;"><a
         href="bookmark.page?p=${allPageCount }&shape=${shape }&sort=${sort }${f }${s }">▶▶</a></li>

   </ul>

   <!-- 검색 -->
   <form action="bookmark.search" method="get">
      <table align="center">
         <tr align="center">
            <c:choose>
               <c:when test="${param.rcp == 'r_text' }">
                  <td><select name="rcp">
                        <option value="r_title">제목</option>
                        <option value="r_text" selected>소개</option>
                        <option value="r_category">카테고리</option>
                  </select></td>
               </c:when>
               <c:when test="${param.rcp == 'r_category' }">
                  <td><select name="rcp">
                        <option value="r_title">제목</option>
                        <option value="r_text">소개</option>
                        <option value="r_category" selected>카테고리</option>
                  </select></td>
               </c:when>
               <c:otherwise>
                  <td><select name="rcp">
                        <option value="r_title" selected>제목</option>
                        <option value="r_text">소개</option>
                        <option value="r_category">카테고리</option>
                  </select></td>
               </c:otherwise>
            </c:choose>
            <td><input type="text" name="search" maxlength="20"
               value="${search }"> <input type="hidden" name="shape"
               value="${shape }"> <input type="hidden" name="sort"
               value="${sort }"></td>
            <td>
               <button>검색</button>
            </td>
         </tr>
      </table>
   </form>


   
   <br>
   <br>
   <br>
</body>
</html>