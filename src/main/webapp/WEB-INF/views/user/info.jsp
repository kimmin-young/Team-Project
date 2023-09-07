<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/info.jsp</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
	${updateMsg }
	<!-- 수정 성공/실패 여부에 대한 alert 문구 -->
	<table id="InfoTbl">
		<form action="user.update" method="post" enctype="multipart/form-data"
			onsubmit="return infoCheck();" name="infoForm">
			<tr>
				<th>아이디</th>
				<td><input type="text" name="u_id" id="u_id_value"
					readonly="readonly" value="${sessionScope.loginUser.u_id }"
					class="ellipsis3" style="font-weight: 700;"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<div>
						<input type="password" name="u_pw" maxlength="15"
							autocomplete="off" placeholder=" 비밀번호 입력  ">
					</div>
				</td>
			</tr>
			<tr>
				<th>새 비밀번호 <span style="font-size: 9px;">(선택)</span>
				</th>
				<td>
					<div class="passwordBtn">
						<input type="password" name="new_u_pw" maxlength="15"
							autocomplete="off" id="changeType" placeholder=" 변경을 원하면 입력 "
							id="new_u_pw" style="font-weight: 700;"> <i
							class="bi bi-eye-slash"></i>
					</div>
				</td>
			</tr>

			<tr>
				<th>새 비밀번호 확인 <span style="font-size: 9px;">(선택)</span>
				</th>
				<td>
					<div class="passwordBtn">
						<input type="password" name="new_u_pw_chk" maxlength="15"
							autocomplete="off" id="changeType2" placeholder=" 변경을 원하면 입력 "
							style="font-weight: 700;"> <i class="bi bi-eye-slash"></i>
					</div>
				</td>
			</tr>
			<tr style="height: 80px;">
				<th>이메일&nbsp;&nbsp;</th>
				<td><input type="text" name="u_email" id="u_email_value_update"
					maxlength="40" autocomplete="off" placeholder=" 이메일 입력 "
					value="${sessionScope.loginUser.u_email }"
					style="font-weight: 700; font-family: sans-serif;">
					<div id="u_email_check" style="font-size: 14px;">&nbsp;</div></td>
			</tr>
			<tr style="height: 80px;">
				<th>닉네임</th>
				<td><input type="text" name="u_nickname"
					id="u_nickname_value_update" maxlength="10" autocomplete="off"
					placeholder=" 2~10자 , 영문, 숫자, 한글 가능! "
					value="${sessionScope.loginUser.u_nickname }"
					style="font-weight: 700;">
					<div id="u_nickname_check" style="font-size: 14px;">&nbsp;</div></td>
			</tr>
			<tr style="height: 80px;">
				<th>이름</th>
				<td><input type="text" name="u_name" readonly="readonly"
					value="${sessionScope.loginUser.u_name }" style="font-weight: 700;"></td>
			</tr>
			<tr style="height: 80px;">
				<th>전화번호</th>
				<td><input type="text" name="u_phone" id="u_phone_value_update"
					maxlength="13" autocomplete="off" placeholder=" 숫자만 입력 "
					value="${sessionScope.loginUser.u_phone }"
					style="font-weight: 700;">
					<div id="u_phone_check" style="font-size: 14px;">&nbsp;</div></td>
			</tr>

			<tr style="height: 150px;">
				<th>사진 <span style="font-size: 9px;">(선택)</span>
				</th>
				<td><input type="file" name="file" id="fileVolume" value="1" style="border: 0;">
					<br> <img
					src="resources/img/${sessionScope.loginUser.u_photo }"
					width="100px" height="100px"></td>
			</tr>

			<tr style="height: 90px;">
				<th>주소</th>
				<td><input type="text" name="u_addr1" value="${addr[2] }"
					maxlength="34" id="uaddr1" autocomplete="off"
					placeholder=" 주소 입력 (클릭) " style="font-weight: 700;"> <br>
					<input type="text" name="u_addr2" value="${addr[0] }"
					maxlength="34" id="uaddr2" autocomplete="off" placeholder=" - "
					style="font-weight: 700;"> <br> <input type="text"
					name="u_addr3" value="${addr[1] }" maxlength="34"
					autocomplete="off" placeholder=" 상세 주소 입력 "
					style="font-weight: 700;"> <br></td>
			</tr>

			<tr>
				<td><input type="hidden" name="u_role"
					value="${sessionScope.loginUser.u_role }"></td>
			</tr>
	</table>
	<table id="InfoBtn" style="width: 765px; padding-top:20px; padding-bottom: 80px;">
		<tr>
			<td>
				<button style="float: right;" onclick="return update();">수정</button>

				</form>
				<button style="float: right;" onclick="remove();">삭제</button>
			</td>
		</tr>
	</table>

</body>
</html>