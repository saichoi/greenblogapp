package com.cos.blogapp.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	//JpaRepository를 붙여주면 BoardRepository를 메모리에 띄워준다.
}
