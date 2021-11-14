<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<p>전체 게시글 : ${boardsEntity.totalElements}</p>

	<!-- 검색창 시작 -->
	<form class="row g-3 d-fex justify-content-end" >
		<div class="col-auto">
			<input type="text" class="form-control" name="searchText"
			  value="${param.searchText}" >
		</div>
		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">검색</button>
		</div>
	</form>

	<!-- var는 pageScope에 저장 -->
	<c:forEach var="board" items="${boardsEntity.content}">
		<!-- 카드 글 시작 -->
		<div class="card d-flex flex-row align-items-center overflow-hidden" style="height:150px;">
			<div class="card-image w-25 ">
				<img class="card-img-top h-auto" src="${board.content}" alt="Card image cap" onerror="this.src='/image/default-image.png'">
			</div>
			<div class="card-body">
				<!-- el표현식은 변수명을 적으면 자동으로 get함수를 호출해준다. -->
				<h4 class="card-title">${board.title}</h4>
				<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
			</div>
		</div>
		<br>
		<!-- 카드 글 끝 -->
	</c:forEach>

	<!-- disabled -->
	<ul class="pagination d-flex justify-content-center">
		<c:choose>
			<c:when test="${boardsEntity.first}">
				<li class="page-item disabled"><a class="page-link"
					href="/board?page=${boardsEntity.number - 1}&searchText=${param.searchText}">Prev</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="/board?page=${boardsEntity.number - 1}&searchText=${param.searchText}">Prev</a></li>
			</c:otherwise>
		</c:choose>


		<c:forEach begin="${startPage}" end="${endPage}" var="idx">
			<c:choose>
				<c:when test="${idx eq nowPage}">
					<li class="page-item disabled"><a
						class="page-link page-active" href="/board?page=${idx-1}&searchText=${param.searchText}">${idx}</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link"
						href="/board?page=${idx-1}&searchText=${param.searchText}">${idx}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>

		<c:choose>
			<c:when test="${boardsEntity.last}">
				<li class="page-item disabled"><a class="page-link"
					href="/board?page=${param.page + 1}&searchText=${param.searchText}">Next</a></li>
			</c:when>

			<c:otherwise>
				<li class="page-item"><a class="page-link"
					href="/board?page=${param.page + 1}&searchText=${param.searchText}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>

</div>

<%@ include file="../layout/footer.jsp"%>