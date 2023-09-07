package com.team.prj.cs;

import java.math.BigDecimal;
import java.util.Date;

public class CSReply {
	private BigDecimal csr_num;		// 댓글 번호
	private String csr_text;		// 댓글 내용
	private String csr_writer;		// 댓글 작성자
	private Date csr_when;			// 작성 시간
	private BigDecimal csr_cs_num;	// 댓글이 있는 글번호
	
	private String csr_nickname;	// 댓글 작성자 닉네임
	private String csr_photo;		// 댓글 작성자 프로필사진

	public CSReply() {
	}

	public CSReply(BigDecimal csr_num, String csr_text, String csr_writer, Date csr_when, BigDecimal csr_cs_num) {
		super();
		this.csr_num = csr_num;
		this.csr_text = csr_text;
		this.csr_writer = csr_writer;
		this.csr_when = csr_when;
		this.csr_cs_num = csr_cs_num;
	}
	
	public CSReply(BigDecimal csr_num, String csr_text, String csr_writer, Date csr_when, BigDecimal csr_cs_num, String csr_nickname, String csr_photo) {
		super();
		this.csr_num = csr_num;
		this.csr_text = csr_text;
		this.csr_writer = csr_writer;
		this.csr_when = csr_when;
		this.csr_cs_num = csr_cs_num;
		this.csr_nickname = csr_nickname;
		this.csr_photo = csr_photo;
	}

	public BigDecimal getCsr_num() {
		return csr_num;
	}

	public void setCsr_num(BigDecimal csr_num) {
		this.csr_num = csr_num;
	}

	public String getCsr_text() {
		return csr_text;
	}

	public void setCsr_text(String csr_text) {
		this.csr_text = csr_text;
	}

	public String getCsr_writer() {
		return csr_writer;
	}

	public void setCsr_writer(String csr_writer) {
		this.csr_writer = csr_writer;
	}

	public Date getCsr_when() {
		return csr_when;
	}

	public void setCsr_when(Date csr_when) {
		this.csr_when = csr_when;
	}

	public BigDecimal getCsr_cs_num() {
		return csr_cs_num;
	}

	public void setCsr_cs_num(BigDecimal csr_cs_num) {
		this.csr_cs_num = csr_cs_num;
	}
	
	

	public String getCsr_nickname() {
		return csr_nickname;
	}

	public void setCsr_nickname(String csr_nickname) {
		this.csr_nickname = csr_nickname;
	}

	public String getCsr_photo() {
		return csr_photo;
	}

	public void setCsr_photo(String csr_photo) {
		this.csr_photo = csr_photo;
	}

}
