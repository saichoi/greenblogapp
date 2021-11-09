package com.cos.blogapp.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	//JpaRepository를 붙여주면 BoardRepository를 메모리에 띄워준다.
	
	@Query(value = "select count(id) from board", nativeQuery = true)
	int mBoardCnt();
	
	@Query(value = "SELECT * FROM board WHERE title LIKE %:searchText% or content LIKE %:searchText%", nativeQuery = true)
	Page<Board> findByTitleOrContent(String searchText, Pageable page);
	
}
