// 다음에서 제공하는 주소 API 서비스 이벤트
function connectSearchAddressEvent() {
	$("#uaddr1, #uaddr2").click(function() {
		new daum.Postcode({
			oncomplete : function(data) {
				$("#uaddr1").val(data.zonecode);
				$("#uaddr2").val(data.roadAddress);
			}
		}).open();
	});
}

// /////////////////////////////////////////////////////////////////////////[
// 회원가입+회원정보수정 ]

// [회원가입 이용약관시] 버튼 체크를 위한 이벤트
function agreeToSEvent() {
	$(function() {

		$("#ToS_1")
				.click(
						function() {
							$("#ToS_2")
									.click(
											function() {

												if (($("#ToS_1:checked").val() == "on")
														&& ($("#ToS_2:checked")
																.val() == "on")) {
													$("#ToSButton").removeAttr(
															"disabled");
												} else {
													$("#ToSButton").attr(
															"disabled",
															"disabled");
												}
											});
						});

		$("#ToS_2")
				.click(
						function() {
							$("#ToS_1")
									.click(
											function() {

												if (($("#ToS_1:checked").val() == "on")
														&& ($("#ToS_2:checked")
																.val() == "on")) {
													$("#ToSButton").removeAttr(
															"disabled");
												} else {
													$("#ToSButton").attr(
															"disabled",
															"disabled");
												}
											});
						});

	});
}

// [회원가입시] 아이디 중복확인을 위한 이벤트
function connectIdCheckEvent() {
	$("#u_id_value").keyup(function() {
		var u_id = $(this).val().toLowerCase();
		// http://localhost/chungminip/member.id.check?c_id=kmy 확인가능
		// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
		$.getJSON("user.id.check?u_id=" + u_id, function(userJSON) {

			if (u_id.length > 2) { // 아이디 3글자 이상

				// 아이디가 없으면
				if (userJSON.user[0] == null) {
					$("#u_id_check").text("사용가능한 아이디입니다.");
					$("#u_id_check").css("color", "#2F9D27");
					$("#joinButton").removeAttr("disabled");
				}
				// 아이디가 있으면
				else {
					$("#u_id_check").text("사용중인 아이디입니다.");
					$("#u_id_check").css("color", "#FF3636");
					$("#joinButton").attr("disabled", "disabled");
				}

			} else {
				$("#u_id_check").text("\u00A0"); // 빈 칸
			}

		});
	});
}

// [회원가입시] "메일 선택" -> "직접 입력" 선택 시 input 보이게 하는 이벤트
function emailInputChange() {
	$(function() {

		$("#u_email_select").click(function() {
			var val = $(this).val();

			if (val == '직접입력') {
				$("#u_email2_input").val('');
				$("#u_email2_input").attr("type", "text");
			} else {
				$("#u_email2_input").val(val);
				$("#u_email2_input").attr("type", "hidden");
			}

		});
	});
}

// [회원가입시] 이메일 중복확인/인증을 위한 이벤트
function connectEmailCheckEvent() {
	$("#emailBtn").click(
			function() {

				var u_email1 = $("#u_email1_input").val();
				var u_email2 = $("#u_email2_input").val();
				var u_email = u_email1 + "@" + u_email2;
				// alert(u_email); // 입력받은 값 확인

				// 정규식이 아니라면
				if ((/^[a-z]+\.[a-z]{2,3}$/).test(u_email2) == false
						|| (/[^a-z0-9]/g).test(u_email1) == true) {
					alert('사용할 수 없는 이메일 형식입니다.');
					$("#u_email2_input").val('');
				} else { // 정규식이 맞다면
					$.getJSON("user.email.check?u_email=" + u_email, function(
							userJSON) {

						if (userJSON == "1") { // 중복 O
							alert('이미 존재하는 이메일입니다.');
							// alert(userJSON);
						} else { // 중복 X
							alert('인증번호가 발송되었습니다.');

							// 인증번호가 발송되면 인증번호 칸이 활성화되고 '확인' 버튼이 보이게 됨
							$("#authenticationNumberInput").attr("disabled",
									false);
							$("#authenticationNumberButton").css("display",
									"inline");

							// '확인' 버튼을 눌렀을 때
							$("#authenticationNumberButton").click(
									function() {
										// '일치'하면 회원가입 가능
										if (userJSON == $(
												"#authenticationNumberInput")
												.val()) {
											$("#authenticationResult").css(
													"color", "#2F9D27");
											$("#authenticationResult").text(
													"일치");
											// 유효성검사 u_emailChk 값이 null만 아니면 통과
											$("#u_emailChk").attr("value",
													"asdf");
										} else {
											$("#authenticationResult").css(
													"color", "#FF3636");
											$("#authenticationResult").text(
													"불일치");
											$("#u_emailChk").attr("value", "");
										}
									});

						}
					});
				}
			});
}

// [회원가입시] 닉네임 중복확인을 위한 이벤트
function connectNicknameCheckEvent() {
	$("#u_nickname_value").keyup(
			function() {
				var u_nickname = $(this).val();
				// http://localhost/chungminip/member.id.check?c_id=kmy 확인가능
				// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
				$
						.getJSON(
								"user.nickname.check?u_nickname=" + u_nickname,
								function(userJSON) {

									if (u_nickname.length > 1) { // 닉네임 2글자
										// 이상

										// 닉네임이 없으면
										if (userJSON.user[0] == null) {
											$("#u_nickname_check").text(
													"사용가능한 닉네임입니다.");
											$("#u_nickname_check").css("color",
													"#2F9D27");
											$("#joinButton").removeAttr(
													"disabled");
										}
										// 닉네임이 있으면
										else {
											$("#u_nickname_check").text(
													"사용중인 닉네임입니다.");
											$("#u_nickname_check").css("color",
													"#FF3636");
											$("#joinButton").attr("disabled",
													"disabled");
										}

									} else {
										$("#u_nickname_check").text("\u00A0"); // 빈 칸
									}

								});
			});
}

// [회원가입시] 전화번호 중복확인을 위한 이벤트
function connectPhoneCheckEvent() {
	$("#u_phone_value").keyup(function() {
		var u_phone = $(this).val();
		// http://localhost/chungminip/member.id.check?c_id=kmy 확인가능
		// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
		$.getJSON("user.phone.check?u_phone=" + u_phone, function(userJSON) {

			if (u_phone.length > 10) { // 전화번호가 11글자 이상

				// 전화번호가 없으면
				if (userJSON.user[0] == null) {
					$("#u_phone_check").text("사용가능한 전화번호입니다.");
					$("#u_phone_check").css("color", "#2F9D27");
					$("#joinButton").removeAttr("disabled");
				}
				// 전화번호가 있으면
				else {
					$("#u_phone_check").text("사용중인 전화번호입니다.");
					$("#u_phone_check").css("color", "#FF3636");
					$("#joinButton").attr("disabled", "disabled");
				}

			} else {
				$("#u_phone_check").text("\u00A0"); // 빈 칸
			}

		});
	});
}

// [회원정보수정시] 이메일 중복확인을 위한 이벤트
function updateEmailCheckEvent() {
	$("#u_email_value_update")
			.keyup(
					function() {
						var u_email = $(this).val();
						// http://localhost/chungminip/member.id.check?c_id=kmy
						// 확인가능
						// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
						$
								.getJSON(
										"user.email.check.update?u_email="
												+ u_email,
										function(userJSON) {

											// 이메일 유효성검사에 부합하면
											if ((/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,30})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/)
													.test(u_email) == true) {

												// 이메일이 없으면
												if (userJSON.user[0] == null) {
													$("#u_email_check").text(
															"사용가능한 이메일입니다.");
													$("#u_email_check").css(
															"color", "#2F9D27");
												}
												// 이메일이 있으면
												else {
													$("#u_email_check").text(
															"사용중인 이메일입니다.");
													$("#u_email_check").css(
															"color", "#FF3636");
												}

											} else {
												$("#u_email_check").text(
														"\u00A0"); // 빈 칸
											}

										});
					});
}

// [회원정보수정시] 닉네임 중복확인을 위한 이벤트
function updateNicknameCheckEvent() {
	$("#u_nickname_value_update").keyup(
			function() {
				var u_nickname = $(this).val();
				// http://localhost/chungminip/member.id.check?c_id=kmy 확인가능
				// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
				$
						.getJSON("user.nickname.check.update?u_nickname="
								+ u_nickname,
								function(userJSON) {

									if (u_nickname.length > 1) { // 닉네임 2글자
										// 이상

										// 닉네임이 없으면
										if (userJSON.user[0] == null) {
											$("#u_nickname_check").text(
													"사용가능한 닉네임입니다.");
											$("#u_nickname_check").css("color",
													"#2F9D27");
										}
										// 닉네임이 있으면
										else {
											$("#u_nickname_check").text(
													"사용중인 닉네임입니다.");
											$("#u_nickname_check").css("color",
													"#FF3636");
										}

									} else {
										$("#u_nickname_check").text("\u00A0"); // 빈 칸
									}

								});
			});
}

// [회원정보수정시] 전화번호 중복확인을 위한 이벤트
function updatePhoneCheckEvent() {
	$("#u_phone_value_update").keyup(
			function() {
				var u_phone = $(this).val();
				// http://localhost/chungminip/member.id.check?c_id=kmy 확인가능
				// ( 요청주소?파라미터=값&파라미터=값&... , 값을 받아오는데 성공하면 발동하는 콜백함수 )
				$
						.getJSON(
								"user.u_phone_value_update?u_phone=" + u_phone,
								function(userJSON) {

									if (u_phone.length > 10) { // 전화번호가 11글자 이상

										// 전화번호가 없으면
										if (userJSON.user[0] == null) {
											$("#u_phone_check").text(
													"사용가능한 전화번호입니다.");
											$("#u_phone_check").css("color",
													"#2F9D27");
										}
										// 전화번호가 있으면
										else {
											$("#u_phone_check").text(
													"사용중인 전화번호입니다.");
											$("#u_phone_check").css("color",
													"#FF3636");
										}

									} else {
										$("#u_phone_check").text("\u00A0"); // 빈 칸
									}

								});
			});
}

// [회원가입, 회원정보수정시] 파일 용량을 제한, 유효성 검사하는 이벤트
function limitFileVolumeEvent() {
	$("#fileVolume").on("change", function() {
		let maxSize = 20 * 1024 * 1024; // 20MB 제한
		let fileSize = this.files[0].size; // 업로드한 파일 용량

		// 파일 크기 제한
		if (fileSize > maxSize) {
			alert('첨부파일 사이즈는 20MB 이내로 가능합니다.');
			$(this).val(''); // 업로드한 파일 제거
			return;
		}

		// 유효성 검사
		if ($(this).val() != "") {
			var ext = $(this).val().split('.').pop().toLowerCase();
			if ($.inArray(ext, [ 'gif', 'png', 'jpg', 'jpeg', 'bmp' ]) == -1) {
				alert('지원하지 않는 형식입니다.');
				$(this).val('');
				return;
			}
		}
	});
}

// [회원가입,회원정보수정시] 전화번호에 하이픈 추가, 유효성검사
function phoneHyphen() {
	$("#u_phone_value, #u_phone_value_update").keyup(
			function() {
				$(this).val(
						$(this).val().replace(/[^0-9]/g, '').replace(
								/(^02|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,
								"$1-$2-$3").replace("--", "-"));

				// /[^0-9]/g, '' : 모든 범위(g)에서 0~9가 아닌 글자는 빈칸으로 대체
				// ^02 : 02로 시작
				// ^0[0-9]{2} : 0으로 시작하고 뒤에 숫자 2개(0~9) ex) 031, 043
				// ([0-9]+)? : 문자열 맨 끝에서 자른 숫자 4개를 제외한 나머지 중간에 있는 숫자를 하나의 그룹으로
				// 인식
				// ([0-9]{4})$ : 숫자 4개(0~9)로 끝남
				//
				// + : 최소 한개 or 여러개
				// ? : 앞에 있는 () 그룹을 하나로 인식해 없거나 최대 한개
				// $ : 문자열의 끝을 의미
			});
}

// 비밀번호 보이기/숨기기 이벤트
function changePwEvent() {

	$('.passwordBtn i').on(
			'click',
			function() {
				$('input').toggleClass('active');
				if ($('input').hasClass('active')) {
					$(this).attr('class', "bi bi-eye").prev('input').attr(
							'type', "text");
				} else {
					$(this).attr('class', "bi bi-eye-slash").prev('input')
							.attr('type', 'password');
				}
			});

}

//x 글자 이상이면 ...표시
function ellipsis() {
	$(function(){
			// 글자의 개념이 아니라 공간의 개념 (확실치 않음)
			// 해당 공간을 벗어나면 text-overflow 표시
			// id로 설정하면 첫번째 글 목록만 해당되고 class로 설정해야 모든 글 목록에 해당됨
		
		 	// $(".ellipsis").css("border", "1px solid #BDBDBD");
			$(".ellipsis").css("width","200px");
			$(".ellipsis").css("overflow","hidden");
			$(".ellipsis").css("text-overflow","ellipsis");
			$(".ellipsis").css("white-space","nowrap");
			
	});
}

function ellipsis2() {
	$(function(){
			// 글자의 개념이 아니라 공간의 개념 (확실치 않음)
			// 해당 공간을 벗어나면 text-overflow 표시
			// id로 설정하면 첫번째 글 목록만 해당되고 class로 설정해야 모든 글 목록에 해당됨
		
		 	// $(".ellipsis").css("border", "1px solid #BDBDBD");
			$(".ellipsis2").css("width","100px");
			$(".ellipsis2").css("overflow","hidden");
			$(".ellipsis2").css("text-overflow","ellipsis");
			$(".ellipsis2").css("white-space","nowrap");
			
	});
}

//x 글자 이상이면 ...표시
function ellipsis3() {
	$(function(){
			// 글자의 개념이 아니라 공간의 개념 (확실치 않음)
			// 해당 공간을 벗어나면 text-overflow 표시
			// id로 설정하면 첫번째 글 목록만 해당되고 class로 설정해야 모든 글 목록에 해당됨
		
		 	// $(".ellipsis").css("border", "1px solid #BDBDBD");
			$(".ellipsis").css("width","400px");
			$(".ellipsis").css("overflow","hidden");
			$(".ellipsis").css("text-overflow","ellipsis");
			$(".ellipsis").css("white-space","nowrap");
			
	});
}

$(function() {
	phoneHyphen();
	agreeToSEvent();
	connectIdCheckEvent();
	emailInputChange();
	connectEmailCheckEvent();
	connectNicknameCheckEvent();
	connectPhoneCheckEvent();
	updateEmailCheckEvent();
	updateNicknameCheckEvent();
	updatePhoneCheckEvent();
	limitFileVolumeEvent();
	connectSearchAddressEvent();
	changePwEvent();
	ellipsis();
	ellipsis2();
	ellipsis3();
});
