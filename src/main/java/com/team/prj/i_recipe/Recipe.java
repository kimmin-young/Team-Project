package com.team.prj.i_recipe;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Recipe {
	//테이블 컬럼
	private BigDecimal r_no;
	private String r_title;
	private String r_category;
	private Date r_date;
	private String r_text;
	private String r_photo;
	private String r_writer;
	private String r_ended;
	private BigDecimal r_seen;
	private BigDecimal r_star_avg;
	private String r_example;
	
	private List<Tail> r_tails;
	private int r_rev_cnt;

//	private List<Review> r_reviews; //리뷰들
	
	public Recipe() {
		// TODO Auto-generated constructor stub
	}


	public Recipe(BigDecimal r_no, String r_title, String r_category, Date r_date, String r_text, String r_photo,
			String r_writer, String r_ended, BigDecimal r_seen, List<Tail> r_tails, int r_rev_cnt, BigDecimal r_star_avg, String r_example) {
		super();
		this.r_no = r_no;
		this.r_title = r_title;
		this.r_category = r_category;
		this.r_date = r_date;
		this.r_text = r_text;
		this.r_photo = r_photo;
		this.r_writer = r_writer;
		this.r_ended = r_ended;
		this.r_seen = r_seen;
		this.r_tails = r_tails;
		this.r_rev_cnt = r_rev_cnt;
		this.r_star_avg = r_star_avg;
		this.r_example = r_example;
	}


	public BigDecimal getR_no() {
		return r_no;
	}

	public void setR_no(BigDecimal r_no) {
		this.r_no = r_no;
	}

	public String getR_title() {
		return r_title;
	}

	public void setR_title(String r_title) {
		this.r_title = r_title;
	}

	public String getR_category() {
		return r_category;
	}

	public void setR_category(String r_category) {
		this.r_category = r_category;
	}

	public Date getR_date() {
		return r_date;
	}

	public void setR_date(Date r_date) {
		this.r_date = r_date;
	}

	public String getR_text() {
		return r_text;
	}

	public void setR_text(String r_text) {
		this.r_text = r_text;
	}

	public String getR_photo() {
		return r_photo;
	}

	public void setR_photo(String r_photo) {
		this.r_photo = r_photo;
	}

	public String getR_writer() {
		return r_writer;
	}

	public void setR_writer(String r_writer) {
		this.r_writer = r_writer;
	}

	public List<Tail> getR_tails() {
		return r_tails;
	}

	public void setR_tails(List<Tail> r_tails) {
		this.r_tails = r_tails;
	}

//	public List<Review> getR_reviews() {
//		return r_reviews;
//	}
//
//	public void setR_reviews(List<Review> r_reviews) {
//		this.r_reviews = r_reviews;
//	}

	

	public String getR_ended() {
		return r_ended;
	}



	public int getR_rev_cnt() {
		return r_rev_cnt;
	}



	public void setR_rev_cnt(int r_rev_cnt) {
		this.r_rev_cnt = r_rev_cnt;
	}



	public void setR_ended(String r_ended) {
		this.r_ended = r_ended;
	}




	public BigDecimal getR_star_avg() {
		return r_star_avg;
	}




	public void setR_star_avg(BigDecimal r_star_avg) {
		this.r_star_avg = r_star_avg;
	}


	public BigDecimal getR_seen() {
		return r_seen;
	}


	public void setR_seen(BigDecimal r_seen) {
		this.r_seen = r_seen;
	}


	public String getR_example() {
		return r_example;
	}


	public void setR_example(String r_example) {
		this.r_example = r_example;
	}
	
	
	
	
}
