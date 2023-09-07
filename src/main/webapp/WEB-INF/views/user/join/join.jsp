<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user/join.jsp</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
</head>
<body>
	<div align="center" style="margin-top: 40px;">
		<h1>해먹자 회원가입</h1>
	</div>
	<div align="center"
		style="font-size: 15px; color: gray; margin-bottom: 40px;">해먹자
		회원이 되어 레시피를 공유해보세요!</div>

	<form action="user.join" method="post" enctype="multipart/form-data"
		onsubmit="return joinCheck();" name="joinForm">
		<table border="1"
			style="margin: 0 auto; border-collapse: collapse; border-radius: 10px; border-style: hidden; box-shadow: 0 0 0 1px #C3C3C3;"
			id="joinTbl">

			<tr style="height: 50px;">
				<th style="border-top-left-radius: 10px;">아이디</th>
				<td><input type="text" name="u_id" id="u_id_value"
					maxlength="15" autocomplete="off" placeholder=" 영문, 숫자  가능! "
					class="joinInput">
					<div id="u_id_check" style="font-size: 14px; margin-left: 40px;">&nbsp;</div></td>
			</tr>

			<tr style="height: 40px;">
				<th>비밀번호</th>
				<td>
					<div class="passwordBtn">
						<input type="password" name="u_pw" maxlength="15"
							autocomplete="off" placeholder=" 비밀번호 입력  " id="changeType">
						<i class="bi bi-eye-slash"></i>
					</div>
				</td>
			</tr>

			<tr style="height: 40px;">
				<th>비밀번호 확인</th>
				<td>
					<div class="passwordBtn">
						<input type="password" name="u_pw_chk" maxlength="15"
							autocomplete="off" placeholder=" 비밀번호 입력 " id="changeType2">
						<i class="bi bi-eye-slash"></i>
					</div>
				</td>
			</tr>

			<tr style="height: 60px;">
				<th>이메일</th>
				<td><input type="text" name="u_email1" style="width: 100px;"
					id="u_email1_input" maxlength="20" autocomplete="off"><a
					style="font-family: sans-serif;"> @ </a><input type="hidden"
					name="u_email2" style="width: 100px;" id="u_email2_input"
					maxlength="15"> <select id="u_email_select">
						<option value="" selected disabled hidden>메일 선택</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="nate.com">nate.com</option>
						<option value="직접입력">직접 입력</option>
				</select>
					<button type="button" id="emailBtn" style="color: #353535;">
						인증번호 받기</button>
					<div style="font-size: 13px; color: #4374D9; margin-left: 40px;">*
						비밀번호 분실 시 필요한 정보이므로, 최초 가입 시 인증이 필요합니다.</div>
					<div>
						<input type="password" placeholder="인증번호  입력"
							style="font-size: 15px;" disabled="disabled" maxlength="6"
							id="authenticationNumberInput">
						<button type="button" style="display: none; color: #353535;"
							id="authenticationNumberButton">확인</button>
						<span id="authenticationResult" style="font-size: 14px;"></span>
					</div> <input type="hidden" name="u_emailChk" id="u_emailChk"></td>
			</tr>

			<tr style="height: 70px;">
				<th>닉네임</th>
				<td><input type="text" name="u_nickname" id="u_nickname_value"
					maxlength="10" autocomplete="off"
					placeholder=" 2~10자 , 영문, 숫자, 한글 가능! ">
					<div id="u_nickname_check"
						style="font-size: 14px; margin-left: 40px;">&nbsp;</div></td>
			</tr>

			<tr style="height: 50px;">
				<th>이름</th>
				<td><input type="text" name="u_name" maxlength="6"
					autocomplete="off" placeholder=" 이름 입력 "></td>
			</tr>

			<tr style="height: 50px;">
				<th>전화번호</th>
				<td><input type="text" name="u_phone" id="u_phone_value"
					maxlength="13" autocomplete="off" placeholder=" 숫자만 입력 ">
					<div id="u_phone_check" style="font-size: 14px; margin-left: 40px;">&nbsp;</div></td>
			</tr>

			<tr style="height: 50px;">
				<th>사진(선택)</th>
				<td><input type="file" name="file" id="fileVolume"></td>
			</tr>

			<tr style="height: 110px;">
				<th style="border-bottom-left-radius: 10px;">주소</th>
				<td><input type="text" name="u_addr1" maxlength="34"
					id="uaddr1" autocomplete="off" placeholder=" 주소 입력 (클릭) "
					style="margin-bottom: 1px;"><br> <input type="text"
					name="u_addr2" maxlength="34" id="uaddr2" autocomplete="off"
					placeholder=" - " style="margin-top: 1px; margin-bottom: 1px;"><br>
					<input type="text" name="u_addr3" maxlength="34" autocomplete="off"
					placeholder=" 상세 주소 입력 " style="margin-top: 1px;"></td>
			</tr>

			<input type="hidden" name="u_role" value="u">
		</table>
		<table align="center" style="padding: 20px;">
			<tr>
				<td colspan="2" style="text-align: center;">
					<button type="submit" id="joinButton" disabled="disabled">회원가입</button>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>