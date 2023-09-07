package com.team.prj.cs;

public class CSSelector {
	private String searchCs;
	private String searchType;
	private String categoryType;
	private String id;
	private int start;
	private int end;
	
	public CSSelector() {
	}

	public CSSelector(String searchCs, String searchType, String categoryType, String id, int start, int end) {
		super();
		this.searchCs = searchCs;
		this.searchType = searchType;
		this.categoryType = categoryType;
		this.id = id;
		this.start = start;
		this.end = end;
	}

	public String getSearchCs() {
		return searchCs;
	}

	public void setSearchCs(String searchCs) {
		this.searchCs = searchCs;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	
}
