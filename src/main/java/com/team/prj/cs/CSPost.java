package com.team.prj.cs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CSPost {
	
	// cs table (게시글)
	private BigDecimal cs_num;		// 게시글 번호
	private String cs_category;		// 게시글 분류
	private String cs_title;		// 게시글 제목
	private String cs_text;			// 게시글 내용
	private String cs_writer;		// 게시글 작성자
	private BigDecimal cs_views;	// 게시글 조회수
	private Date cs_when;			// 게시글 작성 시간
	private BigDecimal cs_reply;    // 게시글 댓글 갯수
	
	// csr table (댓글)
//	private List<CSReply> csr_replys;
	
	public CSPost() {
	}

	public CSPost(BigDecimal cs_num, String cs_category, String cs_title, String cs_text, String cs_writer,
			BigDecimal cs_views, Date cs_when, BigDecimal cs_reply) {
		super();
		this.cs_num = cs_num;
		this.cs_category = cs_category;
		this.cs_title = cs_title;
		this.cs_text = cs_text;
		this.cs_writer = cs_writer;
		this.cs_views = cs_views;
		this.cs_when = cs_when;
		this.cs_reply = cs_reply;
	}

	public BigDecimal getCs_num() {
		return cs_num;
	}

	public void setCs_num(BigDecimal cs_num) {
		this.cs_num = cs_num;
	}

	public String getCs_category() {
		return cs_category;
	}

	public void setCs_category(String cs_category) {
		this.cs_category = cs_category;
	}

	public String getCs_title() {
		return cs_title;
	}

	public void setCs_title(String cs_title) {
		this.cs_title = cs_title;
	}

	public String getCs_text() {
		return cs_text;
	}

	public void setCs_text(String cs_text) {
		this.cs_text = cs_text;
	}

	public String getCs_writer() {
		return cs_writer;
	}

	public void setCs_writer(String cs_writer) {
		this.cs_writer = cs_writer;
	}

	public BigDecimal getCs_views() {
		return cs_views;
	}

	public void setCs_views(BigDecimal cs_views) {
		this.cs_views = cs_views;
	}

	public Date getCs_when() {
		return cs_when;
	}

	public void setCs_when(Date cs_when) {
		this.cs_when = cs_when;
	}

	public BigDecimal getCs_reply() {
		return cs_reply;
	}

	public void setCs_reply(BigDecimal cs_reply) {
		this.cs_reply = cs_reply;
	}

}
