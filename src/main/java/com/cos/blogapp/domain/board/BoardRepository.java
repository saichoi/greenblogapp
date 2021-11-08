package com.cos.blogapp.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	//JpaRepository를 붙여주면 BoardRepository를 메모리에 띄워준다.
	
	@Query(value = "select count(id) from board", nativeQuery = true)
	int mBoardCnt();
	
}
