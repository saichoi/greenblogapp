<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container p-4 w-25 bg-light rounded shadow" >
<h5 style="font-family: 'IBM Plex Sans KR', sans-serif; margin-bottom:30px;">로그인</h5>
	<!-- container 가운데 정렬 전체화면의 80% -->
	<form action="/loginForm" method="post">
		<!-- form 많은 데이터를 한방에 보내기 위해 -->
		<div class="form-group">
			<input type="text" name="username" class="form-control" placeholder="Enter username">
		</div>
		<div class="form-group">
			<input type="password" class="form-control" name="password"
				placeholder="Enter password" id="pwd">
		</div>
		<button type="submit" class="btn btn-primary col-md-4" style=" margin-top:30px;">로그인</button>
	</form>
</div>
<%@ include file="../layout/footer.jsp"%>