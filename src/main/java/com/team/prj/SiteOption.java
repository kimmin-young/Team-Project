package com.team.prj;

// 한 페이지당 보여줄 게시물 갯수와 관련된 자바빈
// servlet-context.mxl에 bean객체로 등록
public class SiteOption {
	private int recipePerPage;
	// ...
	
	public SiteOption() {
	}

	public SiteOption(int recipePerPage) {
		super();
		this.recipePerPage = recipePerPage;
	}

	public int getRecipePerPage() {
		return recipePerPage;
	}

	public void setRecipePerPage(int recipePerPage) {
		this.recipePerPage = recipePerPage;
	}

}
