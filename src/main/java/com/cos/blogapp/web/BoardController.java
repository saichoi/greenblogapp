package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.service.BoardService;
import com.cos.blogapp.service.CommentService;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;
import com.cos.blogapp.web.dto.CMRespDto;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Controller 
public class BoardController {

	// DI : Ioc에 있는거 가져옴
	private final BoardService boardService;
	private final CommentService commentService;
	private final HttpSession session;
	
	// 댓글달기
	@PostMapping("/board/{boardId}/comment")
	public String commentSave(@PathVariable int boardId, CommentSaveReqDto dto) {
		User principal = (User) session.getAttribute("principal");
		
		if (principal == null) {
			throw new MyNotFoundException("인증이 되지 않았습니다.");
		}
		
		commentService.댓글등록(boardId, dto, principal); // -> 디비와 관련된 트랜젝션을 서비스로 이동 
		return "redirect:/board/"+boardId;  
	}
	
	// 게시글 수정하기
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, 
			@RequestBody @Valid BoardSaveReqDto dto, BindingResult bindingResult){

		//유효성검사
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}
		
		//인증 체크
		User principal = (User) session.getAttribute("principal");
		if (principal == null) {
			throw new MyAsyncNotFoundException("인증이 되지 않았습니다.");
		}
		
		boardService.게시글수정(id, principal, dto);
		
		return new CMRespDto<String>(1, "업데이트 성공",null);
		
	}

	// 게시글 수정하기 페이지 이동
	@GetMapping("/board/{id}/updateForm")
	public String boardUpdateForm(@PathVariable int id, Model model) {
		
		model.addAttribute("boardEntity", boardService.게시글수정페이지이동(id, model));
		
		return "board/updateForm";
		
	}

	// 게시글 삭제하기 
	@DeleteMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {
		User principal = (User) session.getAttribute("principal");
		
		boardService.게시글삭제(id, principal);
		return new CMRespDto<String>(1, "성공", null); // @ResoponseBody : 데이터 리턴
	}

	// 게시글 상세보기 
	@GetMapping("/board/{id}") 
	public String detail(@PathVariable int id, Model model) {
		// Board객체에 존재하는 것(Board(o),User(o),List<Comment>(x : LAZY))
		model.addAttribute("boardEntity", boardService.게시글상세보기(id, model));
		return "board/detail"; // ViewResolver
	}

	// 게시글 등록하기
	@PostMapping("/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {

	//공통 로직 
		User principal = (User) session.getAttribute("principal");

		// 인증 체크
		if (principal == null) {
			return Script.href("/loginForm", "잘못된 접근입니다.");
		}

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

	// 핵심 로직 
		boardService.게시글등록(principal, dto);
		
		return Script.href("/", "글쓰기 성공"); // 데이터를 모델에서 들고 와야 한다.
	}

	// 게시글 등록하기 페이지 이동하기
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "/board/saveForm";
	}

	// 게시글 목록보기 
	@GetMapping("/board")
	public String home(Model model, int page) {
		model.addAttribute("boardsEntity", boardService.게시글목록보기(page));
		return "board/list";
	}

}
