package com.cos.blogapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.web.dto.BoardSaveReqDto;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	// 생성자 주입(DI)
	private final BoardRepository boardRepository;

	// 트랜젝션 어노테이션 : 트랜젝션 시작하는 것
	// rollbackFor : 함수 내부에 하나의 write라도 실패하면 전체를 rollback하는 것
	// 주의 :RuntimeException을 던저야 동작한다. 
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class) // 트랜젝션에서 하나라도 실패하면 전부 롤백 됨
	public void 게시글수정(int id, User principal, BoardSaveReqDto dto) {

		// 게시글 수정하는 서비스(트랜젝션)

		// 권한 체크
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyAsyncNotFoundException("해당 게시글을 찾을 수 없습니다."));
		if (principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해당 게시글을 수정할 권한이 없습니다.");
		}

		// 영속화된 데이터를 변경하면!!
		boardEntity.setTitle(dto.getTitle());
		boardEntity.setContent(dto.getContent());
	} // 트랜젝션 종료(더티체킹) 

	public Board 게시글수정페이지이동(int id, Model model) {
		// 게시글 정보 가지고 가야함.
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException(id + "번의 게시글을 찾을 수 없습니다."));
		
		return boardEntity;
	}

	@Transactional(rollbackFor = MyAsyncNotFoundException.class) // 트랜젝션에서 하나라도 실패하면 전부 롤백 됨
	public void 게시글삭제(int id, User principal) {

		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyAsyncNotFoundException("해당글을 찾을 수 없습니다."));
		if (principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해당글을 삭제할 권한이 없습니다.");
		}

		try {
			boardRepository.deleteById(id); // 게시글 id가 없으면 오류 발생 (Empty result data Exception) -> try ~ catch 처리
		} catch (Exception e) {
			throw new MyAsyncNotFoundException(id + "를 찾을 수 없어서 삭제할 수 없습니다.");
		}
	}

	public Board 게시글상세보기(int id, Model model) {
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> new MyNotFoundException(id + "를 못 찾았어요."));
		return boardEntity;
	}

	// @Transactional: 트랜젝션 '범위' 설정 
	@Transactional(rollbackFor = MyNotFoundException.class) // 트랜젝션에서 하나라도 실패하면 전부 롤백 됨 
	public void 게시글등록(User principal, BoardSaveReqDto dto) {
		boardRepository.save(dto.toEntity(principal));
	}

	public Page<Board> 게시글목록보기(int page) {

		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Direction.DESC, "id"));
		Page<Board> boardsEntity = boardRepository.findAll(pageRequest); 
		return boardsEntity;
	}

} // end of BoardService
