////////////////////////////////////////////////////////// [user]

// 로그인
function goLogin() {
	location.href = "user.login.go";
}
// 회원가입 약관
function goJoinToS() {
	location.href = "user.join.tos";
}

// 회원가입 페이지
function goJoinSelect() {
	location.href = "user.join.select";
}

// 회원가입
function goJoin() {
	location.href = "user.join.go";
}

// 로그아웃
function logout() {
	if (confirm("로그아웃을 진행하시겠습니까?")) {
		location.href = "user.logout";
	}
}

// 내정보
function userInfoGo() {
	location.href = "user.info.go";
}

// 삭제
function remove() {
	var u_pw = prompt('본인 확인을 위해 비밀번호를 입력해 주세요.');

	if (u_pw != null) {
		if (confirm("회원탈퇴를 진행하시겠습니까?")) {
			location.href = "user.remove?u_pw=" + u_pw;
		}
	}
}

// 수정
function update() {
	if (confirm("회원정보를 수정하시겠습니까?")) {
		return true;
	} else {
		return false;
	}
}

// 로그인 페이지로
// function goLogin() {
// location.href = "user.login";
// }

// //////////////////////////////////////////////////////// [ find ]

// 아이디 찾기
function goFindId() {
	location.href = "user.find.id";
}

// 비밀번호 찾기
function goFindPw() {
	location.href = "user.find.pw";
}

//------------------------ i_recipe -------------------------------


//게시판 형태(만) 변경
function changeShape(shape) {
	
	 const URLSearch = new URLSearchParams(location.search);
	 URLSearch.set('shape', String(shape));
	 const newParam = URLSearch.toString();
	 // 파라미터 shape 값으로 쿼리스트링 변경
	 // newParam은 ?을 제외한 쿼리스트링 전체
	 
	 var here = window.location.pathname; // ? 이전 주소
	 location.href = here + "?" + newParam;
	 
}

//게시판 정렬(만) 변경
function changeSort(sort, shape) {
	
	const URLSearch = new URLSearchParams(location.search);
	URLSearch.set('shape', String(shape));
	URLSearch.set('sort', String(sort));
	const newParam = URLSearch.toString();
	// 파라미터 shape 값으로 쿼리스트링 변경
	// newParam은 ?을 제외한 쿼리스트링 전체
	
	var here = window.location.pathname; // ? 이전 주소
	location.href = here + "?" + newParam;
	
}


//레시피 쓰기 페이지로 이동
function goWrite() {
	location.href = "i_recipe.write.go";
}


//레시피 완성
function endRcp(no) {
	if (confirm("레시피를 완성하시겠습니까?\n" +
			"(* 이미지를 첨부하지 않은 글은 완성 후에도 리뷰를 받을 수 없습니다.)")) {
		location.href = "i_recipe.end?r_no=" + no;
	}
}

//머리글 수정
function updateHead(no){
	location.href = "i_recipe.update.head.go?r_no=" + no;
}

//머리글 삭제 (글 통째로 삭제)
function deleteRcp(no){
	if (confirm("삭제할 시 레시피 전체가 삭제됩니다. 삭제하시겠습니까?")) {
		location.href = "i_recipe.delete.head?r_no=" + no;
	}
}

//꼬리글 수정
function updateTail(no){
	location.href = "i_recipe.update.tail.go?t_no=" + no;
}

//꼬리글 삭제
function deleteTail(no){
	if (confirm("삭제하시겠습니까?")) {
		location.href = "i_recipe.delete.tail?t_no=" + no;
	}
}

//내 레시피 보기
function myIRecipe(){
	location.href = "i_recipe.myrecipe?shape=gallery&sort=r_no";
}

//------------------------- bookmark -------------------------------

function goBookmark(){
	location.href = "bookmark.go?shape=gallery&sort=r_no";
}

function addBookmark(no){
	
	const URLSearch = new URLSearchParams(location.search);
	URLSearch.set('b_rcp', String(no));
	const newParam = URLSearch.toString(); // 이전 페이지 파라미터 가져오기
	 
	alert('북마크에 추가되었습니다.');
	if(confirm("북마크함으로 이동하시겠습니까?")){
		location.href = "bookmark.add.go?sort=r_no&b_rcp=" + no; //확인 버튼
	}else{
		location.href = "bookmark.add?" + newParam; //취소 버튼
	}
	
}

function deleteBookmark(no){

	const URLSearch = new URLSearchParams(location.search);
	URLSearch.set('b_rcp', String(no));
	const newParam = URLSearch.toString(); // 이전 페이지 파라미터 가져오기
	 
	alert('북마크에서 삭제되었습니다.');
	location.href = "bookmark.delete?" + newParam;
}

////////////////////////////////////////////////////////// [ p_recipe ]

//페이징
function p_recipePaging(p) {
	location.href = "p_recipe.paging?p=" + p;
}

//공공 레시피 컨텐츠
function p_recipeContent(num,p) {
	location.href = "p_recipeContent.go?num=" + num + "&p=" + p;
}

//////////////////////////////////////////////////////////[ cs ]

//고객센터 페이지로
function csGo() {
	location.href="cs";
}

//고객센터 글쓰기
function csWrite() {
	location.href="cs.write";
}

//고객센터 글쓰기 취소
function csCancel() {
	if(confirm("글 작성을 취소하시겠습니까?")){
		location.href="cs";
	}	
}

//카테고리
function csCategory(categoryType) {
		location.href="cs.category?categoryType="+categoryType;
}

//페이징
function csPaging(p) {
	location.href="cs.paging?p="+p;
}

//고객센터 컨텐츠 페이지
function csContent(cs_num,p) {
	location.href="csContent.go?cs_num="+cs_num+"&p="+p;
}

//고객센터 컨텐츠 페이지 수정 취소
function csUpdateCancel(cs_num,p) {
	if(confirm("글 변경을 취소하시겠습니까?")){
		location.href="csContent.go?cs_num="+cs_num+"&p="+p;
	}	
}

//고객센터 컨텐츠 페이지 삭제
function csRemove(cs_num) {
	if(confirm("해당 글을 삭제하시겠습니까?")){
		location.href="csContent.remove?cs_num="+cs_num;
	}	
}

//고객센터 컨텐츠 페이지 댓글 삭제
function csReplyRemove() {
	if(confirm("댓글을 삭제하시겠습니까?")){
		return true;
	} else {
		return false;
	}
}

//////////////////////////////////////////////////////////[ cs 관리자 ]

//고객 센터 페이지
function csManageGo() {
	location.href="cs.manage";
}

//카테고리
function csManageCategory(categoryType) {
		location.href="cs.manage.category?categoryType="+categoryType;
}

//페이징
function csManagePaging(p) {
	location.href="cs.manage.paging?p="+p;
}

//컨텐츠 페이지
function csManageContent(cs_num,p) {
	location.href="csContent.manage.go?cs_num="+cs_num+"&p="+p;
}
