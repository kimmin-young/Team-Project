package com.team.prj.bookmark;


import java.util.List;

import com.team.prj.PageSelector;
import com.team.prj.i_recipe.Recipe;
import com.team.prj.user.User;

public interface BMapper {
	
	public abstract Integer getBmkCount(User u);
	public abstract Integer getSearchBmkCount(PageSelector pSel);
	
	public abstract int bookmarked(Bookmark b);
	public abstract int addBmk(Bookmark b);
	public abstract List<Recipe> getBmk(PageSelector pSel);
	public abstract int deleteBmk(Bookmark b);
}
