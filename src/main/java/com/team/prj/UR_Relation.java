package com.team.prj;

import java.math.BigDecimal;

public class UR_Relation {
	// 유저가 해당 게시글에 리뷰를 달았는지, 게시글 북마크를 했는지 등을 검색할 때 쓰는 객체
	// 유저와 게시글의 관계를 나타냄
	
	private String user; // 유저 아이디
	private BigDecimal recipe; // 게시글 일련번호
	
	public UR_Relation() {
		// TODO Auto-generated constructor stub
	}

	public UR_Relation(String user, BigDecimal recipe) {
		super();
		this.user = user;
		this.recipe = recipe;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public BigDecimal getRecipe() {
		return recipe;
	}

	public void setRecipe(BigDecimal recipe) {
		this.recipe = recipe;
	}
	
	
}
