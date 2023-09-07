<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>i_recipe/updateHead.jsp</title>
</head>
<body>
	<form name="headForm" action="i_recipe.update.head" method="post"
		enctype="multipart/form-data" onsubmit="return headCheck();">
		<input name="r_no" value="${h.r_no }" type="hidden"> 
		<input name="p" value="1" type="hidden"> 
		<input name="shape" value="gallery" type="hidden"> 
		<input name="sort" value="r_no" type="hidden"> 
		<input name="from" value="ircp" type="hidden">
		<table>
			<tr>
				<td>
					<table id="writeInfo">
						<tr>
							<td colspan="2" style="font:bold 30pt;"><input name="r_title" value="${h.r_title }"
								maxlength="30"></td>
						</tr>
						<tr>
							<td style="color: gray;">${sessionScope.loginUser.u_nickname }</td>
							<td><select name="r_category">
									<option value="한식">한식</option>
									<option value="양식">양식</option>
									<option value="중식">중식</option>
									<option value="일식">일식</option>
									<option value="베이킹">베이킹</option>
									<option value="기타">기타</option>
							</select></td>
						</tr>
					</table>
				</td>
			</tr>
			<hr>
			<tr>
				<td>
					<table id="writeContent">
						<tr>
							<td colspan="2"><textarea name="r_text">${h.r_text }</textarea></td>
						</tr>
						<tr>
							<td><input type="file" name="file">
								<br>사진을 첨부하지 않으면 기존 사진으로 유지됩니다.<td>
							<td><button>수정</button></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</form>

</body>
</html>