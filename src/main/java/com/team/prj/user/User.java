package com.team.prj.user;

public class User {
	private String u_id;
	private String u_pw;
	private String u_email;
	private String u_nickname;
	private String u_name;
	private String u_phone;
	private String u_photo;
	private String u_addr;
	private String u_role;
	private String update_email;	// info.jsp 정보 수정에 사용하기 위해 session값을 담을 변수
	private String update_nickname;	// info.jsp 정보 수정에 사용하기 위해 session값을 담을 변수
	private String update_phone;	// info.jsp 정보 수정에 사용하기 위해 session값을 담을 변수
	
	public User() {
	}

	public User(String u_id, String u_pw, String u_email, String u_nickname, String u_name, String u_phone,
			String u_photo, String u_addr, String u_role, String update_email, String update_nickname,
			String update_phone) {
		super();
		this.u_id = u_id;
		this.u_pw = u_pw;
		this.u_email = u_email;
		this.u_nickname = u_nickname;
		this.u_name = u_name;
		this.u_phone = u_phone;
		this.u_photo = u_photo;
		this.u_addr = u_addr;
		this.u_role = u_role;
		this.update_email = update_email;
		this.update_nickname = update_nickname;
		this.update_phone = update_phone;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_pw() {
		return u_pw;
	}

	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public String getU_nickname() {
		return u_nickname;
	}

	public void setU_nickname(String u_nickname) {
		this.u_nickname = u_nickname;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_phone() {
		return u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public String getU_photo() {
		return u_photo;
	}

	public void setU_photo(String u_photo) {
		this.u_photo = u_photo;
	}

	public String getU_addr() {
		return u_addr;
	}

	public void setU_addr(String u_addr) {
		this.u_addr = u_addr;
	}

	public String getU_role() {
		return u_role;
	}

	public void setU_role(String u_role) {
		this.u_role = u_role;
	}

	public String getUpdate_email() {
		return update_email;
	}

	public void setUpdate_email(String update_email) {
		this.update_email = update_email;
	}

	public String getUpdate_nickname() {
		return update_nickname;
	}

	public void setUpdate_nickname(String update_nickname) {
		this.update_nickname = update_nickname;
	}

	public String getUpdate_phone() {
		return update_phone;
	}

	public void setUpdate_phone(String update_phone) {
		this.update_phone = update_phone;
	}

	

	
}
