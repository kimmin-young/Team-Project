package com.team.prj.bookmark;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.prj.PageSelector;
import com.team.prj.SiteOption;
import com.team.prj.i_recipe.IRMapper;
import com.team.prj.i_recipe.Recipe;
import com.team.prj.user.User;

@Service
public class BDAO {
	private int allBmkCount;

	@Autowired
	private SqlSession ss;

	@Autowired
	private SiteOption so;
	
	
	// 해당 유저의 북마크 게시글 수를 확인
	public void countAllBmk(HttpServletRequest req) {
		User u = (User)req.getSession().getAttribute("loginUser");
		allBmkCount = ss.getMapper(BMapper.class).getBmkCount(u);
	}

	// 해당 유저가 해당 글을 북마크했는지 판별
	public void bookmarked (Recipe rc, HttpServletRequest req) {
		Bookmark b = new Bookmark();
		User u = (User) req.getSession().getAttribute("loginUser");
		b.setB_user(u.getU_id());
		b.setB_rcp(rc.getR_no());
		
		if (ss.getMapper(BMapper.class).bookmarked(b) >=1) {
			req.setAttribute("bmkd", true);
			System.out.println(req.getAttribute("bmkd"));
		} else {
			req.setAttribute("bmkd", false);
			System.out.println(req.getAttribute("bmkd"));
		}
	}
	
	// 북마크 추가
	public void addBmk(Bookmark b, HttpServletRequest req) {
		try {
			User u = (User) req.getSession().getAttribute("loginUser");
			b.setB_user(u.getU_id());

			if (ss.getMapper(BMapper.class).addBmk(b) == 1) {
				req.setAttribute("r", "북마크 추가 성공");
			} else {
				req.setAttribute("r", "북마크 추가 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "북마크 추가 실패 (DB연결)");
		}
	}

	// 북마크 리스트 보기
	public List<Recipe> getBmk(int page, String shape, String sort, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("loginUser");

		String nickname = u.getU_nickname();
		String id = u.getU_id();
		System.out.println("id : " + id);
		
		req.setAttribute("curPage", page);
		req.setAttribute("shape", shape);
		req.setAttribute("sort", sort);
		
		String search = (String) req.getSession().getAttribute("search");
		String rcp = (String) req.getSession().getAttribute("rcp");
		
		int rcpCount = 0;
		if (search == null) { //검색 x 전체조회시
			rcpCount = allBmkCount;
			search = "";
		} else {
			PageSelector pSel2 = new PageSelector(search, rcp, 0, 0, shape, sort, id);
			rcpCount = ss.getMapper(BMapper.class).getSearchBmkCount(pSel2);
		}
		
		req.setAttribute("rcpCount", rcpCount); // 조회한 글 갯수
		System.out.println(allBmkCount);
		
		int allPageCount = (int) Math.ceil((double) rcpCount / so.getRecipePerPage());
		req.setAttribute("allPageCount", allPageCount); // 페이지 개수
		System.out.println(req.getAttribute("allPageCount"));
		
		int start = (page - 1) * so.getRecipePerPage() + 1;
		int end = (page == allPageCount) ? rcpCount : start + so.getRecipePerPage() - 1;
		
		System.out.println("start="+start+"&end="+end);
		
		PageSelector pSel = new PageSelector(search, rcp, start, end, shape, sort, id);
		List<Recipe> recipes = ss.getMapper(BMapper.class).getBmk(pSel);
		
		for (Recipe recipe : recipes) {
			System.out.println("레시피 제목: "+ recipe.getR_title());
			recipe.setR_writer(nickname); // 아이디를 닉네임으로 바꾸기
		}
		
		return recipes;
	}

	// 북마크 삭제
	public void deleteBmk(Bookmark b, HttpServletRequest req) {
		try {
			User u = (User) req.getSession().getAttribute("loginUser");
			b.setB_user(u.getU_id());
			
			if (ss.getMapper(BMapper.class).deleteBmk(b) == 1) {
				req.setAttribute("r", "북마크 삭제 성공");
			} else {
				req.setAttribute("r", "북마크 삭제 실패");
			}
		} catch (Exception e) {
			req.setAttribute("r", "북마크 삭제 실패(DB)");
		}
	}

	// 해당 회원 북마크 전체 삭제(회원탈퇴시)
}