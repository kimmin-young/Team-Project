<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>i_recipe/writeHead.jsp</title>
<style type="text/css">
#writeInfo input {
	width: 1007px;
	height: 30px;
}

#writeInfo select {
	width: 100px;
	height: 30px;
}

#writeContent textarea {
	font-size: 10pt;
	font-weight: 500;
	width : 1110px;
	height: 150px;
	resize: none;
	border: none;
}

#writeContent button {
	position: absolute;
	right: 15px;
	width: 100px;
}
</style>
</head>
<body>
	<form name="headForm" action="i_recipe.write" method="post"
		enctype="multipart/form-data" onsubmit="return headCheck();">
		<input name="token" value="${token }" type="hidden"> <input
			name="from" value="${param.from }" type="hidden">
		<table style="margin: 10px;">
			<tr>
				<td width="100%">
					<table id="writeInfo">
						<tr>
							<td><select name="r_category">
									<option value="한식">한식</option>
									<option value="양식">양식</option>
									<option value="중식">중식</option>
									<option value="일식">일식</option>
									<option value="베이킹">베이킹</option>
									<option value="기타">기타</option>
							</select></td>
							<!-- 
							<td rowspan="2"><img
								src="resources/img/${sessionScope.loginUser.u_photo }"
								width="100px" height="100px"></td>
							 -->
							<td colspan="2" style="font: bold 30pt;"><input
								name="r_title" placeholder="레시피 제목 입력" maxlength="30"></td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td>
					<table id="writeContent">
						<tr>
							<td colspan="2"><textarea name="r_text"></textarea></td>
						</tr>
						<tr>
							<td><input type="file" name="file"><input
								name="p" value="${param.p }" type="hidden"> <input
								name="shape" value="${param.shape }" type="hidden"> <input
								name="sort" value="${param.sort }" type="hidden"> <input
								name="from" value="${param.from }" type="hidden">
							<td>
							<td><button>레시피 작성</button></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</form>

</body>
</html>
