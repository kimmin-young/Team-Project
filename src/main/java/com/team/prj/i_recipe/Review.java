package com.team.prj.i_recipe;

import java.math.BigDecimal;

public class Review {
	private BigDecimal rv_no;
	private BigDecimal rv_star;
	private String rv_text;
	private BigDecimal rv_rcp;
	private String rv_writer;
	
	public Review() {
		// TODO Auto-generated constructor stub
	}

	public Review(BigDecimal rv_no, BigDecimal rv_star, String rv_text, BigDecimal rv_rcp, String rv_writer) {
		super();
		this.rv_no = rv_no;
		this.rv_star = rv_star;
		this.rv_text = rv_text;
		this.rv_rcp = rv_rcp;
		this.rv_writer = rv_writer;
	}

	public BigDecimal getRv_no() {
		return rv_no;
	}

	public void setRv_no(BigDecimal rv_no) {
		this.rv_no = rv_no;
	}

	public BigDecimal getRv_star() {
		return rv_star;
	}

	public void setRv_star(BigDecimal rv_star) {
		this.rv_star = rv_star;
	}

	public String getRv_text() {
		return rv_text;
	}

	public void setRv_text(String rv_text) {
		this.rv_text = rv_text;
	}

	public BigDecimal getRv_rcp() {
		return rv_rcp;
	}

	public void setRv_rcp(BigDecimal rv_rcp) {
		this.rv_rcp = rv_rcp;
	}

	public String getRv_writer() {
		return rv_writer;
	}

	public void setRv_writer(String rv_writer) {
		this.rv_writer = rv_writer;
	}
	
	
}
