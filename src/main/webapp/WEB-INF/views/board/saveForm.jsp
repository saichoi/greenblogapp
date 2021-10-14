<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container p-4 w-70 rounded shadow">
	<h5 style="font-family: 'IBM Plex Sans KR', sans-serif; margin-bottom: 30px;">글쓰기</h5>
	<form action="/api/board" method="post">
		<div class="form-group">
			<input type="text" name="title" class="form-control" placeholder="Enter title">
		</div>
		<div class="form-group">
			<textarea id="summernote" class="form-control" name="content"></textarea>
		</div>
		<button type="submit" class="btn btn-primary col-md-4" style="margin-top: 30px;">글쓰기</button>
	</form>
</div>

<script>
        $('#summernote').summernote({
            height:350
        });
</script>
  
<%@ include file="../layout/footer.jsp"%>