<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/csUpdate.jsp</title>
<!-- 서머노트를 위해 추가해야할 부분 -->
<script src="resources/summernote/summernote-lite.js"></script>
<script src="resources/summernote/lang/summernote-ko-KR.js"></script>
<link rel="stylesheet" href="resources/summernote/summernote-lite.css">
</head>
<body>

	<form method="post" action="cs.update" id="smarteditorForm"
		name="csUpdateForm" onsubmit="return csUpdateCheck();">
		<div align="center">
			제목 : <select name="cs_category">
				<option value="${csc.cs_category }">${csc.cs_category }</option>
				<option value="카테고리">카테고리</option>
				<option value="컨텐츠">컨텐츠</option>
				<option value="회원">회원</option>
				<option value="사이트이용">사이트이용</option>
				<option value="기타">기타</option>
			</select> <input type="text" name="cs_title" style="width: 590px;"
				autocomplete="off" maxlength="40" value="${csc.cs_title }">
		</div>
		<div align="center">
			<textarea id="summernote" rows="20" name="cs_text" maxlength="3000">${csc.cs_text }</textarea>
			<button id="csBtn" style="right: 120px;">작성</button>
			<button id="csBtn" type="button"
				onclick="csUpdateCancel(${csc.cs_num},${curPage });">취소</button>
		</div>
		<input name="cs_num" value="${csc.cs_num }" type="hidden"> <input
			name="token" value="${token }" type="hidden">
	</form>




	<script>
$(document).ready(function() {

	var toolbar = [
			// 글꼴 설정
	   		 ['fontname', ['fontname']],
	   		 // 글자 크기 설정
	    	['fontsize', ['fontsize']],
		    // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
		    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
		    // 글자색
		    ['color', ['forecolor','color']],
		    // 표만들기
		    ['table', ['table']],
		    // 글머리 기호, 번호매기기, 문단정렬
		    ['para', ['ul', 'ol', 'paragraph']],
		    // 줄간격
		    ['height', ['height']],
		    // 그림첨부, 링크만들기, 동영상첨부
		    ['insert',['picture','link','video']],
		    // 코드보기, 확대해서보기, 도움말
		    ['view', ['codeview','fullscreen', 'help']]
		  ];

	var setting = {
            width : 1000,
            height : 500,
            minHeight : null,
            maxHeight : null,
            focus : true,
            lang : 'ko-KR',
            toolbar : toolbar,
            fontNames: ['Arial', 'Arial Black', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
        	fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
            dialogsInBody: true,
            callbacks : { //여기 부분이 이미지를 첨부하는 부분
            onImageUpload : function(files, editor,
            welEditable) {
            for (var i = files.length - 1; i >= 0; i--) {
            uploadSummernoteImageFile(files[i],
            this);
            		}
            	}
            }
         };

        $('#summernote').summernote(setting);
        });
        
		function uploadSummernoteImageFile(file, el) {
			data = new FormData();
			data.append("file", file);
			$.ajax({
				data : data,
				type : "POST",
				url : "uploadSummernoteImageFile",
// 				dataType:"JSON",
				contentType : false,
				processData : false,
				enctype : 'multipart/form-data',
				success : function(data) {
					$(el).summernote('editor.insertImage', data.url);
				}
			});
		
		}

</script>



</body>
</html>












