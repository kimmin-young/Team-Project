<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<title>Insert title here</title>
<style>
.swiper {
	width: 90%;
	height: 300px;
	padding-bottom: 20px;
	padding-top: 20px;
}

.swiper-slide {
	text-align: center;
	font-size: 18px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.swiper-slide img {
	display: block;
	width: 100%;
	height: 300px;;
	object-fit: cover;
}

.swiper-button-next, .swiper-button-prev {
	top: 210px;
	color: #CACACA;
}

.swiper-pagination-bullet {
	background-color: black;
}

.swiper-wrapper p {
	position: absolute;
	left: 20px;
	top: 0px;
	color: white;
	-webkit-text-stroke-width: 1px;
	-webkit-text-stroke-color: black;
}
</style>
</head>
<body>
	<div>
		<div class="swiper mySwiper">
			<div class="swiper-wrapper">

				<c:forEach var="b" items="${brcp }">
					<div class="swiper-slide">
						<a style="display: block; width: 100%; height: 100%;"
							href="i_recipe.rcp.detail?p=1&sort=r_seen&r_no=${b.r_no }&from=ircp"><c:choose>
								<c:when test="${b.r_example == 'O' }">
									<img src="${b.r_photo }" />
								</c:when>
								<c:otherwise>
									<img src="resources/img/${b.r_photo }" />
								</c:otherwise>
							</c:choose></a>
						<p>${b.r_title }</p>
					</div>
				</c:forEach>

			</div>
			<div class="swiper-pagination"></div>
		</div>
		<div class="swiper-button-next"></div>
		<div class="swiper-button-prev"></div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.js"></script>

	<!-- Initialize Swiper -->
	<script>
		var swiper = new Swiper(".mySwiper", {
			effect : "coverflow",
			grabCursor : true,
			centeredSlides : true,
			slidesPerView : "auto",
			coverflowEffect : {
				rotate : 50,
				stretch : 0,
				depth : 100,
				modifier : 1,
				slideShadows : true,
			},
			pagination : {
				el : ".swiper-pagination",
			},
			loop : true,
			navigation : {
				nextEl : ".swiper-button-next",
				prevEl : ".swiper-button-prev",
			},
		});
	</script>
</body>
</html>