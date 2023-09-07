package com.team.prj;

public class PageSelector {
	private String search;
	private String rcp;
	private int start;
	private int end;
	private String shape;
	private String sort;
	private String id;
	private String category;
	
	public PageSelector() {
		// TODO Auto-generated constructor stub
	}

	public PageSelector(String search, String rcp, int start, int end) {
		super();
		this.search = search;
		this.rcp = rcp;
		this.start = start;
		this.end = end;
	}
	

	public PageSelector(String search, String rcp, int start, int end, String shape, String sort) {
		super();
		this.search = search;
		this.rcp = rcp;
		this.start = start;
		this.end = end;
		this.shape = shape;
		this.sort = sort;
	}

	

	public PageSelector(String search, String rcp, int start, int end, String shape, String sort, String id) {
		super();
		this.search = search;
		this.rcp = rcp;
		this.start = start;
		this.end = end;
		this.shape = shape;
		this.sort = sort;
		this.id = id;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getRcp() {
		return rcp;
	}
	
	public void setRcp(String rcp) {
		this.rcp = rcp;
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

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}
