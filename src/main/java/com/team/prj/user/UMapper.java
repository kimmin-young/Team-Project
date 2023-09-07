package com.team.prj.user;

import java.util.List;

public interface UMapper {
	
	public abstract int join(User u);						// 회원가입
	public abstract List<User> idCheck(User u);				// 회원가입 - 아이디 중복 검사
	public abstract User emailCheck(User u);				// 회원가입 - 이메일 중복 검사
	public abstract List<User> nicknameCheck(User u);		// 회원가입 - 닉네임 중복 검사
	public abstract List<User> phoneCheck(User u);			// 회원가입 - 전화번호 중복 검사
	public abstract User login(User u);						// 로그인
	public abstract int remove(User u);						// 회원탈퇴
	public abstract int update(User u);						// 회원정보수정
	public abstract int pwUpdate(User u);					// 비밀번호수정
	public abstract List<User> emailCheckUpdate(User u);	// 회원정보수정 - 이메일 중복 검사
	public abstract List<User> nicknameCheckUpdate(User u);	// 회원정보수정 - 닉네임 중복 검사
	public abstract List<User> phoneCheckUpdate(User u);	// 회원정보수정 - 전화번호 중복 검사
	public abstract User findId(User u);					// 아이디찾기
	public abstract User findPw(User inputU);				// 비밀번호찾기
}
