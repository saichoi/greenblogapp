<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Blog</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- cdn -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<!-- font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR:wght@300&family=Jua&display=swap"
	rel="stylesheet">

</head>
<body>

	<script>
		// id만 적을 수 있다.
		// 자바스크립트로 변형 불가능
		let globalUserId = "${sessionScope.principal.id}"; // 자바스크립트로는 완벽하게 유효성 체크할 수 없다.
	</script>

	<!-- 네브바 시작 -->
	<nav class="navbar navbar-expand-md navbar-dark bg-info"
		style="margin-bottom: 100px;">
		<div
			class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">메뉴1</a></li>
				<li class="nav-item"><a class="nav-link" href="#">메뉴2</a></li>
				<li class="nav-item"><a class="nav-link" href="#">메뉴3</a></li>
				<li class="nav-item"><a class="nav-link" href="#">메뉴4</a></li>
				<li class="nav-item"><a class="nav-link" href="#">메뉴5</a></li>
			</ul>
		</div>
		<div class="mx-auto order-0">
			<a class="navbar-brand mx-auto" href="/"
				style="font-family: 'Jua', sans-serif;">그린블로그</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target=".dual-collapse2">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
			<ul class="navbar-nav ml-auto">

				<c:choose>
					<c:when test="${empty sessionScope.principal}">
						<li class="nav-item"><a class="nav-link" href="/loginForm">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/joinForm">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a class="nav-link"
							href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link"
							href="/api/user/${sessionScope.principal.id}">회원정보</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
					</c:otherwise>
				</c:choose>


			</ul>
		</div>
	</nav>
	<!-- 네브바 끝 -->