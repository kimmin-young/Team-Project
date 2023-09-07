package com.team.prj.i_recipe;

import java.math.BigDecimal;
import java.util.List;

import com.team.prj.PageSelector;
import com.team.prj.UR_Relation;
import com.team.prj.user.User;

public interface IRMapper {
	
	public abstract List<Recipe> bestRecipe();
	public abstract List<Recipe> seenList();
	public abstract List<Recipe> newList();
	
	// 전체or검색한 게시글수 가져오기
	public abstract int getAllRcpCount();
	public abstract Integer getSearchRcpCount(PageSelector pSel);
	public abstract Integer getCatRcpCount(PageSelector pSel);
	public abstract Integer getSearchCatRcpCount(PageSelector pSel);
	public abstract Integer getRcpCountById(PageSelector pSel);
	public abstract Integer getSearchRcpCountById(PageSelector pSel);
	
	// 해당 유저가 해당 게시글에 남긴 리뷰수 보기
	public abstract int getYourRevCount(UR_Relation uRel);
	
	// 해당 글에 사진이 있는지 보기
	public abstract List<Recipe> getHeadPhoto(Recipe rc);
	public abstract List<Tail> getTailPhoto(Recipe rc);
	
	// 유저 아이디로 닉네임 불러오기
	public abstract String getNickById(User u);
	
	// 쓰기
	public abstract int writeRcp(Recipe rc);
	public abstract int writeTail(Tail t);
	public abstract int writeRev(Review rv);
	
	
	
	// 페이징한 목록 불러오기
	public abstract List<Recipe> getRcp(PageSelector pSel);
	public abstract List<Recipe> getRcpByCat(PageSelector pSel);
	public abstract List<Recipe> getRcpById(PageSelector pSel);
	public abstract List<Review> getRev(Recipe rc);
	
	// 레시피 번호,머리와 꼬리 불러오기
	public abstract List<Recipe> getHeadNo(Recipe rc);
	public abstract List<Recipe> getHead(Recipe rc);
	public abstract List<Tail> getTail(Recipe rc);
	public abstract List<Tail> getOneTail(Tail t);
	// 해당 레시피 리뷰 수 가져오기
	public abstract int getAllRevCount(Recipe rc);
	
	// 레시피 별점 평균 불러오기
	public abstract BigDecimal getStarAvg(Recipe rc);
	
	
	
	// 수정
	public abstract int updateRcp(Recipe rc);
	public abstract int endRcp(Recipe rc);
	public abstract int updateSeen(Recipe rc);
	public abstract int updateStar(Recipe rc);
	public abstract int updateTail(Tail t);
	public abstract int updateRev(Review rv);
	
	// 삭제
	public abstract int deleteRcp(Recipe rc);
	public abstract int deleteTail(Tail t);
	public abstract int deleteRev(Review rv);
	
	
}
