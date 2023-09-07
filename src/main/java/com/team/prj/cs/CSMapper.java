package com.team.prj.cs;

import java.util.List;

import com.team.prj.user.User;

public interface CSMapper {

	// 유저
		public abstract int getAllCspCount(User u);					// 해당 회원의 전체 글 갯수
		public abstract int getSearchCSPCount(CSSelector csSel);	// 검색어에 해당하는 글 갯수
		
		public abstract List<CSPost> getCsp(CSSelector cssel);		// 글 가져오기
		
		public abstract int writeCsp(CSPost csp);					// 글 작성

		public abstract int updateCsViews(CSPost csp);				// 컨텐츠 페이지 조회수 증가
		public abstract CSPost getCsContent(CSPost csp);			// 컨텐츠 페이지 데이터 가져오기
		public abstract List<CSReply> getCsReply(CSReply csr);		// 컨텐츠 페이지 댓글 가져오기
		public abstract int getCsReplyCount(CSReply csr);			// 컨텐츠 페이지 댓글 갯수 가져오기
		public abstract List<User> getCsReplyWriter(CSReply csr);	// 컨텐츠 페이지 댓글 갯수 가져오기

		public abstract int updateCs(CSPost csp);					// 컨텐츠 페이지 수정
		public abstract int removeCS(CSPost csp);					// 컨텐츠 페이지 삭제

		public abstract int wrtieCsReply(CSReply csr);				// 컨텐츠 페이지 댓글 등록
		public abstract int deleteCsReply(CSReply csr);				// 컨텐츠 페이지 댓글 삭제

		// 관리자
		public abstract int getAllCspCountManage();
		public abstract int getSearchCSPCountManage(CSSelector csSel);

		public abstract List<CSPost> getCspManage(CSSelector cssel);

		public abstract int deleteCsReplyManage(CSReply csr);


}
