package com.cos.blogapp.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller //컴퍼넌트 스캔(스프링) loc
public class BoardController {
	
	//DI
	private final BoardRepository boardRepository;
	
	@GetMapping("/board")
	public String home(Model model, int page) { //Model:request.setAttribute가 하는 기능을 한다.
		
		//페이징 하고나면 메인페이지에서 오류남  
		//방법1 page 타입을 Integer로 바꿔서...
//		if(page == null) {
//			System.out.println("page값이 null입니다.");
//			page = 0;
//		}
		
		//페이징
		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Direction.DESC, "id"));
		
							//boardsEntity: 퍼시스턴트 오브젝트: 영속화된 오브젝트 -> 오브젝트 사용시 셀렉트(레이지 로딩) 
		Page<Board> boardsEntity =
				boardRepository.findAll(pageRequest); //Sort.by(Sort.Direction.DESC, "id") //BoardRepository에서 Board 적어둬서 컴퓨터가 매핑 타입을 이미 알고 있다.
		model.addAttribute("boardsEntity", boardsEntity);
		//System.out.println(boardsEntity.get(0).getUser().getUsername()); //boardsEntity.get(0).getUser() => 이 단계에서 select(지연로딩)
		return "board/list";
	}
	
}
