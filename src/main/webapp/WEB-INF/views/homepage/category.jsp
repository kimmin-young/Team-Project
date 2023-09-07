<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CATEGORY</title>
<style>
body, ul, li {
	margin: 0;
	list-style: none;
}

ul {
	padding-left: 0px;
}

a {
	font-size: 15pt;
	color: inherit;
	text-decoration: none;
}
</style>
</head>
<body>
	<div style="border: 1px solid #828282;">
		<div style="width: 100%; font-size:15pt; text-align: center; border-bottom: 1px solid #828282; padding-top:10px;  padding-bottom: 10px; background-color: #D8D8D8;">레시피</div>
		<div class="side-bar">
			<ul>
				<li><a href="i_recipe.go?shape=gallery&sort=r_no">개인 레시피</a>
					<ul style="width: 150px;">
						<li><a href="i_recipe.go?shape=gallery&sort=r_no">전체</a></li>
						<li><a href="i_recipe.go?category=한식&shape=gallery&sort=r_no">한식</a></li>
						<li><a href="i_recipe.go?category=일식&shape=gallery&sort=r_no">일식</a></li>
						<li><a href="i_recipe.go?category=중식&shape=gallery&sort=r_no">중식</a></li>
						<li><a href="i_recipe.go?category=양식&shape=gallery&sort=r_no">양식</a></li>
						<li><a
							href="i_recipe.go?category=베이킹&shape=gallery&sort=r_no">베이킹</a></li>
						<li><a href="i_recipe.go?category=기타&shape=gallery&sort=r_no">기타</a></li>
					</ul></li>
				<li><a href="p_recipe.go">공공 레시피</a>
					<ul style="width: 150px;">
						<li><a href="p_recipe.go">전체</a></li>
						<li><a href="p_recipe.search?rcp=RCP_PAT&search=밥">밥</a></li>
						<li><a href="p_recipe.search?rcp=RCP_PAT&search=국">국&찌개</a></li>
						<li><a href="p_recipe.search?rcp=RCP_PAT&search=반찬">반찬</a></li>
						<li><a href="p_recipe.search?rcp=RCP_PAT&search=후식">후식</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</body>
</html>