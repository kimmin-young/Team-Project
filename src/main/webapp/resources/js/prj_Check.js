// 로그인 체크
function loginCheck() {
	var idInput = document.loginForm.u_id;
	var pwInput = document.loginForm.u_pw;
	
	// id 빈칸 x
	if ( isEmpty(idInput) ) {
		alert("아이디를 입력해 주세요.");
		idInput.focus();
		return false;
	}
	// id 3글자 이상
	else if( lessThan(idInput, 3) ) {
		alert("3~15글자 이내로 입력해 주세요.");
		idInput.focus();
		return false;
	} 
	// id 영문, 숫자 가능
	else if( containsJSC2(idInput) ){
		alert("영문, 숫자만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	// id 첫 글자는 영문만
	else if( containsAtoZ(idInput) ){
		alert("첫 글자는 영문만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	// pw 빈칸 x
	else if ( isEmpty(pwInput) ) {
		alert("비밀번호를 입력해 주세요.");
		pwInput.focus();
		return false;
	}
	// pw 6글자 이상
	else if( lessThan(pwInput, 6) ) {
		alert("6~15글자 이내로 입력해 주세요.");
		pwInput.focus();
		return false;
	} 
	// pw 영문, 숫자, 특수문자 가능
	else if( containsJSC3(pwInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
//		pwInput.value = "";
		pwInput.focus();
		return false;
	}
	return true;
}


///////////////////////////////////////////////////////////////////////////[ 회원가입 ]

// 회원가입 체크
function joinCheck() {
	var idInput = document.joinForm.u_id;
	var pwInput = document.joinForm.u_pw;
	var pwchkInput = document.joinForm.u_pw_chk;
	var email1Input = document.joinForm.u_email1;
	var email2Input = document.joinForm.u_email2;
	var emailChkInput = document.joinForm.u_emailChk;
	var nicknameInput = document.joinForm.u_nickname;
	var nameInput = document.joinForm.u_name;
	var phoneInput = document.joinForm.u_phone;
	var addr1Input = document.joinForm.u_addr1;
	var addr2Input = document.joinForm.u_addr2;
	var addr3Input = document.joinForm.u_addr3;

////////////////////////////////////////////// [ 아이디 ]
	// id 빈칸 x
	if ( isEmpty(idInput) ) {
		alert("아이디를 입력해 주세요.");
		idInput.focus();
		return false;
	}
	// id 3글자 이상
	else if( lessThan(idInput, 3) ) {
		alert("3~15글자 이내로 입력해 주세요.");
		idInput.focus();
		return false;
	} 
	// id 영문, 숫자 가능
	else if( containsJSC2(idInput) ){
		alert("영문, 숫자만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	// id 첫 글자는 영문만
	else if( containsAtoZ(idInput) ){
		alert("첫 글자는 영문만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 비밀번호 ]
	// pw 빈칸 x
	else if ( isEmpty(pwInput) ) {
		alert("비밀번호를 입력해 주세요.");
		pwInput.focus();
		return false;
	}
	// pw 6글자 이상
	else if( lessThan(pwInput, 6) ) {
		alert("6~15글자 이내로 입력해 주세요.");
		pwInput.focus();
		return false;
	} 
	// pwchk 빈칸 x
	else if ( isEmpty(pwchkInput) ) {
		alert("비밀번호를 입력해 주세요.");
		pwchkInput.focus();
		return false;
	}
	// pwchk 6글자 이상
	else if( lessThan(pwchkInput, 6) ) {
		alert("6~15글자 이내로 입력해 주세요.");
		pwchkInput.focus();
		return false;
	} 
	// pw, pwchk 값이 다르면
	else if ( notEquals(pwInput, pwchkInput) ) {
		alert("비밀번호가 같지 않습니다.");
		pwchkInput.focus();
		return false;
	}
	// pw 영문, 숫자, 특수문자 가능
	else if( containsJSC3(pwInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
//		pwInput.value = "";
		pwInput.focus();
		return false;
	}
	// pwchk 영문, 숫자, 특수문자 가능
	else if( containsJSC3(pwchkInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
//		pwchkInput.value = "";
		pwchkInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 이메일 ]
	// email1 빈칸 x
	else if ( isEmpty(email1Input) ) {
		alert("이메일을 입력해 주세요.");
		email1Input.focus();
		return false;
	}
	
	// email1 영문, 숫자 사용 가능
	else if ( containsJSC2(email1Input) ) {
		alert("영문, 숫자만 가능합니다.");
//		email1Input.value = "";
		email1Input.focus();
		return false;
	}
	// email2 빈칸 x
	else if ( isEmpty(email2Input) ) {
		alert("이메일을 완료해 주세요.");
		email2Input.focus();
		return false;
	}
	
	// email2 - xxx.xxx (naver.com)와 같은 정규식이 아니라면
	else if ( (/^[a-z]+\.[a-z]{2,3}$/).test(email2Input.value) == false ) {
		alert("사용할 수 없는 이메일 형식입니다.");
//		email2Input.value = "";
		email2Input.focus();
		return false;
	}
	
	else if ( isEmpty(emailChkInput) ) {
		alert('이메일 인증이 필요합니다.');
		return false;
	}
	
////////////////////////////////////////////// [ 닉네임 ]
	// nickname 빈칸 x
	else if (isEmpty(nicknameInput)) {
		alert("닉네임을 입력해 주세요.");
		nicknameInput.focus();
		return false;
	}
	// nickname 2글자 이상
	else if( lessThan(nicknameInput, 2) ) {
		alert("2~10글자 이내로 입력해 주세요.");
		nicknameInput.focus();
		return false;
	}
	// nickname 특수문자 제외 전부 사용 가능
	else if ( (/[^a-zA-Z0-9ㄱ-힣]/g).test(nicknameInput.value) == true ) {
		alert("빈 칸, 특수문자는 사용할 수 없습니다.");
//		nicknameInput.value = "";
		nicknameInput.focus();
		return false;
	}

////////////////////////////////////////////// [ 이름 ]
	// name 빈칸 x 
	else if (isEmpty(nameInput)) {
		alert("이름을 입력해 주세요.");
		nameInput.focus();
		return false;
	 }
	// name 2글자 이상
	else if( lessThan(nameInput, 2) ) {
		alert("2~6글자 이내로 입력해 주세요.");
		nameInput.focus();
		return false;
	}
	// name 한글만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣]/g).test(nameInput.value) == true ) {
		alert("한글만 가능합니다.");
//		nameInput.value = "";
		nameInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 전화번호 ]
//	prj_jQuery.js - phoneHyphen()
	
	// phone 빈칸 x
	else if (isEmpty(phoneInput)) {
		alert("전화번호를 입력해 주세요.");
		phoneInput.focus();
		return false;
	}
	// phone 11글자 이상
	else if( lessThan(phoneInput, 11) ) {
		alert("전화번호를 끝까지 입력해 주세요.");
		phoneInput.focus();
		return false;
	}
	// 첫 글자 0을 제외한 글자가 적혀있으면
	else if( containZero(phoneInput) ) {
		alert("전화번호를 제대로 입력해 주세요.");
		phoneInput.value = "";
		phoneInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 사진 ]
//	prj_jQuery.js - limitFileVolumeEvent()
	
////////////////////////////////////////////// [ 주소 ]
	// addr 빈칸 x 
	else if ( isEmpty(addr1Input) || isEmpty(addr2Input) ) {
		alert("주소를 입력해 주세요.");
		addr1Input.focus();
		return false;
	}
	// addr 빈칸 x 
	else if (isEmpty(addr3Input)) {
			alert("상세 주소를 입력해 주세요.");
			addr3Input.focus();
			return false;
	}
	// addr 한글, 숫자, 빈 칸만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s]/g).test(addr1Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
		addr1Input.value = "";
		addr1Input.focus();
		return false;
	}
	// addr 한글, 숫자, 빈 칸만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s-]/g).test(addr2Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
		addr2Input.value = "";
		addr2Input.focus();
		return false;
	}
	// addr 한글, 숫자, 빈 칸, -만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s-]/g).test(addr3Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
//		addr3Input.value = "";
		addr3Input.focus();
		return false;
	}
	
	return true;
}

///////////////////////////////////////////////////////////////////////////[ 회원정보수정 ]

// 회원정보수정 체크
function infoCheck() {
	var pwInput = document.infoForm.u_pw;
	var pwchkInput = document.infoForm.u_pw_chk;
	var newpwInput = document.infoForm.new_u_pw;
	var emailInput = document.infoForm.u_email;
	var nicknameInput = document.infoForm.u_nickname;
	var phoneInput = document.infoForm.u_phone;
	var addr1Input = document.infoForm.u_addr1;
	var addr2Input = document.infoForm.u_addr2;
	var addr3Input = document.infoForm.u_addr3;
	
////////////////////////////////////////////// [ 비밀번호 ]
	// pw 빈칸 x
	if ( isEmpty(pwInput) ) {
		alert("비밀번호를 입력해 주세요.");
		pwInput.focus();
		return false;
	}
	// pw 6글자 이상
	else if( lessThan(pwInput, 6) ) {
		alert("6~15글자 이내로 입력해 주세요.");
		pwInput.focus();
		return false;
	} 
	// pw 영문, 숫자, 특수문자 가능
	else if( containsJSC3(pwInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
//		pwInput.value = "";
		pwInput.focus();
		return false;
	}
	// pwchk 빈칸 x
	else if ( isEmpty(pwchkInput) ) {
		alert("비밀번호를 입력해 주세요.");
		pwchkInput.focus();
		return false;
	}
	// pwchk 6글자 이상
	else if( lessThan(pwchkInput, 6) ) {
		alert("6~15글자 이내로 입력해 주세요.");
		pwchkInput.focus();
		return false;
	} 
	// pwchk 영문, 숫자, 특수문자 가능
	else if( containsJSC3(pwchkInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
//		pwchkInput.value = "";
		pwchkInput.focus();
		return false;
	}
	// pw, pwchk 값이 다르면
	else if ( notEquals(pwInput, pwchkInput) ) {
		alert("비밀번호가 같지 않습니다.");
		pwchkInput.focus();
		return false;
	}
	// newpw 
	else if (newpwInput.value.length>0 && newpwInput.value.length<6) {
		alert("6~15글자 이내로 입력해 주세요.");
		newpwInput.focus();
		return false;
	}
	// newpw 영문, 숫자, 특수문자 가능
	else if( containsJSC3(newpwInput) ) {
		alert("영문 , 숫자 , 특수문자만 가능합니다.");
		newpwInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 이메일 ]
	// email 빈칸 x
	else if ( isEmpty(emailInput) ) {
		alert("이메일을 입력해 주세요.");
		emailInput.focus();
		return false;
	}
	
	// email - 이메일 정규식이 아니라면
	else if ( (/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,30})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/).test(emailInput.value) == false ) {
		alert("사용할 수 없는 이메일 형식입니다.");
//		emailInput.value = "";
		emailInput.focus();
		return false;
	}
////////////////////////////////////////////// [ 닉네임 ]
	// nickname 빈칸 x
	else if (isEmpty(nicknameInput)) {
		alert("닉네임을 입력해 주세요.");
		nicknameInput.focus();
		return false;
	}
	// nickname 2글자 이상
	else if( lessThan(nicknameInput, 2) ) {
		alert("2~10글자 이내로 입력해 주세요.");
		nicknameInput.focus();
		return false;
	}
	// nickname 특수문자 제외 전부 사용 가능
	else if ( (/[^a-zA-Z0-9ㄱ-힣]/g).test(nicknameInput.value) == true ) {
		alert("빈 칸, 특수문자는 사용할 수 없습니다.");
//		nicknameInput.value = "";
		nicknameInput.focus();
		return false;
	}

////////////////////////////////////////////// [ 전화번호 ]
//	prj_jQuery.js - phoneHyphen()
	
	// phone 빈칸 x
	else if (isEmpty(phoneInput)) {
		alert("전화번호를 입력해 주세요.");
		phoneInput.focus();
		return false;
	}
	// phone 11글자 이상
	else if( lessThan(phoneInput, 11) ) {
		alert("전화번호를 끝까지 입력해 주세요.");
		phoneInput.focus();
		return false;
	}
	//
	else if( containZero(phoneInput) ) {
		alert("전화번호를 제대로 입력해 주세요.");
		phoneInput.value = "";
		phoneInput.focus();
		return false;
	}
	
////////////////////////////////////////////// [ 사진 ]
//	prj_jQuery.js - limitFileVolumeEvent()
	
////////////////////////////////////////////// [ 주소 ]
	// addr 빈칸 x 
	else if ( isEmpty(addr1Input) || isEmpty(addr2Input) ) {
		alert("주소를 입력해 주세요.");
		addr1Input.focus();
		return false;
	}
	// addr 빈칸 x 
	else if (isEmpty(addr3Input)) {
			alert("상세 주소를 입력해 주세요.");
			addr3Input.focus();
			return false;
	}
	// addr 한글, 숫자, 빈 칸만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s]/g).test(addr1Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
		addr1Input.value = "";
		addr1Input.focus();
		return false;
	}
	// addr 한글, 숫자, 빈 칸만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s-]/g).test(addr2Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
		addr2Input.value = "";
		addr2Input.focus();
		return false;
	}
	// addr 한글, 숫자, 빈 칸, -만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣0-9\s-]/g).test(addr3Input.value) == true ) {
		alert("한글, 숫자만 가능합니다.");
//		addr3Input.value = "";
		addr3Input.focus();
		return false;
	}
	
	return true;
}

///////////////////////////////////////////////////////////////////////////[ 찾기 ]

// 아이디 찾기 체크
function findIdCheck() {
	
	var nameInput = document.findIdForm.u_name;
	var emailInput = document.findIdForm.u_email;

	// name 빈칸 x
	if ( isEmpty(nameInput) ) {
		alert("이름을 입력해 주세요.");
		nameInput.focus();
		return false;
	}
	// name 2글자 이상
	else if( lessThan(nameInput, 2) ) {
		alert("2~6글자 이내로 입력해 주세요.");
		nameInput.focus();
		return false;
	}
	// name 한글만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣]/g).test(nameInput.value) == true ) {
		alert("한글만 가능합니다.");
//		nameInput.value = "";
		nameInput.focus();
		return false;
	}
	// email 빈칸 x
	else if ( isEmpty(emailInput) ) {
		alert("이메일을 입력해 주세요.");
		emailInput.focus();
		return false;
	}
	// email - 이메일 정규식이 아니라면
	else if ( (/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,30})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/).test(emailInput.value) == false ) {
		alert("사용할 수 없는 이메일 형식입니다.");
//		emailInput.value = "";
		emailInput.focus();
		return false;
	}
	
	return true;
}


// 비밀번호 찾기 체크
function findPwCheck() {
	
	var nameInput = document.findPwForm.u_name;
	var idInput = document.findPwForm.u_id;
	var emailInput = document.findPwForm.u_email;

	// name 빈칸 x
	if ( isEmpty(nameInput) ) {
		alert("이름을 입력해 주세요.");
		nameInput.focus();
		return false;
	}
	// name 2글자 이상
	else if( lessThan(nameInput, 2) ) {
		alert("2~6글자 이내로 입력해 주세요.");
		nameInput.focus();
		return false;
	}
	// name 한글만 사용 가능
	else if( (/[^ㄱ-ㅎ가-힣]/g).test(nameInput.value) == true ) {
		alert("한글만 가능합니다.");
//		nameInput.value = "";
		nameInput.focus();
		return false;
	}
	// id 빈칸 x
	else if ( isEmpty(idInput) ) {
		alert("아이디를 입력해 주세요.");
		idInput.focus();
		return false;
	}
	// id 3글자 이상
	else if( lessThan(idInput, 3) ) {
		alert("3~15글자 이내로 입력해 주세요.");
		idInput.focus();
		return false;
	}
	// id 영문, 숫자 가능
	else if( containsJSC2(idInput) ){
		alert("영문, 숫자만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	// id 첫 글자는 영문만
	else if( containsAtoZ(idInput) ){
		alert("첫 글자는 영문만 가능합니다.");
//		idInput.value = "";
		idInput.focus();
		return false;
	}
	// email 빈칸 x
	else if ( isEmpty(emailInput) ) {
		alert("이메일을 입력해 주세요.");
		emailInput.focus();
		return false;
	}
	// email - 이메일 정규식이 아니라면
	else if ( (/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,30})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/).test(emailInput.value) == false ) {
		alert("사용할 수 없는 이메일 형식입니다.");
//		emailInput.value = "";
		emailInput.focus();
		return false;
	}
	
	return true;
}

///////////////////////////////////////////////////////////////////////////[ CS ]

function csWriteCheck() {
	
	var categoryInput = document.csWriteForm.cs_category;
	var titleInput = document.csWriteForm.cs_title;
	var textInput = document.csWriteForm.cs_text;

	if ( categoryInput.value =='카테고리' ) {
		alert("카테고리를 선택해 주세요.");
		categoryInput.focus();
		return false;
	}
	else if ( isEmpty(titleInput) ) {
		alert("제목을 입력해 주세요.");
		titleInput.focus();
		return false;
	}
	else if ( isEmpty(textInput) ) {
		alert("내용을 입력해 주세요.");
		textInput.focus();
		return false;
	}
	
	return true;
}


function csUpdateCheck() {
	
	var categoryInput = document.csUpdateForm.cs_category;
	var titleInput = document.csUpdateForm.cs_title;
	var textInput = document.csUpdateForm.cs_text;

	if ( categoryInput.value =='카테고리' ) {
		alert("카테고리를 선택해 주세요.");
		categoryInput.focus();
		return false;
	}
	else if ( isEmpty(titleInput) ) {
		alert("제목을 입력해 주세요.");
		titleInput.focus();
		return false;
	}
	else if ( isEmpty(textInput) ) {
		alert("내용을 입력해 주세요.");
		textInput.focus();
		return false;
	}
	
	return true;
}


function csReplyCheck() {
	
	var textInput = document.csReplyForm.csr_text;

	if ( isEmpty(textInput) ) {
		alert("댓글을 입력해 주세요.");
		textInput.focus();
		return false;
	}
	
	return true;
}

//-------------------- i_recipe ----------------------------------

//머리글 체크
function headCheck() {
	var titleInput = document.headForm.r_title;
	var textInput = document.headForm.r_text;
	
	if (isEmpty(titleInput)) {
		alert("제목을 입력하세요.");
		titleInput.value = "";
		titleInput.focus();
		return false;
	} else if (isEmpty(textInput)) {
		alert("내용을 입력하세요.");
		textInput.value = "";
		textInput.focus();
		return false;
	 }
	
	
	return true;
}

//꼬리글 체크
function tailCheck() {
	var textInput = document.tailForm.t_text;
	var photoInput = document.tailForm.t_photo;
	
	if (isEmpty(textInput)) {
		alert("내용을 입력하세요.");
		textInput.value = "";
		textInput.focus();
		return false;
	 }
	
	return true;
}

//리뷰 체크
function reviewCheck() {
	var textInput = document.reviewForm.rv_text;
	
	if (isEmpty(textInput)) {
		alert("내용을 입력하세요.");
		textInput.value = "";
		textInput.focus();
		return false;
	}
		
	
	return true;
}

