package com.team.prj.bookmark;

import java.math.BigDecimal;

public class Bookmark {
	private BigDecimal b_no;
	private String b_user;
	private BigDecimal b_rcp;
	
	public Bookmark() {
		// TODO Auto-generated constructor stub
	}

	public Bookmark(BigDecimal b_no, String b_user, BigDecimal b_rcp) {
		super();
		this.b_no = b_no;
		this.b_user = b_user;
		this.b_rcp = b_rcp;
	}

	public BigDecimal getB_no() {
		return b_no;
	}

	public void setB_no(BigDecimal b_no) {
		this.b_no = b_no;
	}

	public String getB_user() {
		return b_user;
	}

	public void setB_user(String b_user) {
		this.b_user = b_user;
	}

	public BigDecimal getB_rcp() {
		return b_rcp;
	}

	public void setB_rcp(BigDecimal b_rcp) {
		this.b_rcp = b_rcp;
	}
	
	
}
