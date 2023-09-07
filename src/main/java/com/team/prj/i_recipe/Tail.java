package com.team.prj.i_recipe;

import java.math.BigDecimal;

public class Tail {
	private BigDecimal t_no;
	private BigDecimal t_head;
	private String t_date;
	private String t_text;
	private String t_photo;
	private String t_example;
	
	
	public Tail() {
		// TODO Auto-generated constructor stub
	}

	public Tail(BigDecimal t_no, BigDecimal t_head, String t_text, String t_photo, String t_date, String t_example) {
		super();
		this.t_no = t_no;
		this.t_head = t_head;
		this.t_text = t_text;
		this.t_photo = t_photo;
		this.t_date = t_date;
		this.t_example = t_example;
	}

	public BigDecimal getT_no() {
		return t_no;
	}

	public void setT_no(BigDecimal t_no) {
		this.t_no = t_no;
	}

	public BigDecimal getT_head() {
		return t_head;
	}

	public void setT_head(BigDecimal t_head) {
		this.t_head = t_head;
	}

	public String getT_text() {
		return t_text;
	}

	public void setT_text(String t_text) {
		this.t_text = t_text;
	}

	public String getT_photo() {
		return t_photo;
	}

	public void setT_photo(String t_photo) {
		this.t_photo = t_photo;
	}

	public String getT_date() {
		return t_date;
	}

	public void setT_date(String t_date) {
		this.t_date = t_date;
	}

	public String getT_example() {
		return t_example;
	}

	public void setT_example(String t_example) {
		this.t_example = t_example;
	}
	
	
}
