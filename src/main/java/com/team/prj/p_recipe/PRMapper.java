package com.team.prj.p_recipe;

import java.util.List;

public interface PRMapper {

	public abstract int getAllPRCount();

	public abstract int getSearchPRCount(PRSelector prSel);

	public abstract List<PRecipe> getPRecipe(PRSelector prSel);

	public abstract int updatePRecipe(PRecipe pr);

	public abstract PRecipe getPRecipeContent(PRecipe pr);
}