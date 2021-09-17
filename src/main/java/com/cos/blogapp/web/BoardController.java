package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyNotFountException;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final 붙은 필드에 대한 생성자를 만들어준다.
@Controller //컴퍼넌트 스캔(스프링) loC
public class BoardController {
	
	//DI : Ioc에 있는거 가져옴 
	private final BoardRepository boardRepository;
	private final HttpSession session;
		//final 붙이면 초기화 해줘야한다.
	
	//queryString, pathVariable => DB where에 걸리는 친구들!!
	//1.컨트롤러 선정 2.Http Method 선정 3.받을 데이터가 있는지!! (body, queryString, pathVariable)
	//4.디비에 접근해야하면 Model 접근 orElse Model에 접근할 필요 없다.
	@GetMapping("/board/{id}") //PathVariable 주소 방식
	public String detail(@PathVariable int id, Model model) {
		//select * from board where id = :id
		
		//1.orElse : 값을 찾으면 Board가 리턴, 못찾으면 (괄호안 내용 리턴)
		//		Board boardEntity =  boardRepository.findById(id)
		//				.orElse(new Board(100, "글없어요", "글없어요", null));
		
		//2.orElseThrow
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(()-> //람다식 사용할 때 : 함수 한개인지 확인->매개변수 있는지 확인->리턴필요한지 확인:<T>를 리턴 
					new MyNotFountException(id+"를 못 찾았어요.") 
					);   
			//람다식에서 {}는 여러줄 코드를 사용하기 위해 사용 -> 한 줄이라면 중괄호 생략 -> 한줄 무조건 리턴 -> 리턴 생략 -> 세미콜론 지움   
		model.addAttribute("boardEntity",boardEntity);
		return "board/detail";
	}
	
	//RestFull API 주소 설계 방식 
	@PostMapping("/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {
		
		User principal = (User)session.getAttribute("principal");  
		
		//인증 체크
		if(principal==null) {
			return Script.href("/loginForm","잘못된 접근입니다.");
		}
		
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		System.out.println(dto.getTitle());
		System.out.println(dto.getContent());
		
//		User user = new User();
//		user.setId(3);
//		boardRepository.save(dto.toEntity(user));
		
		boardRepository.save(dto.toEntity(principal));
		return Script.href("/","글쓰기 성공"); //데이터를 모델에서 들고 와야 한다.
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "/board/saveForm";
	}
	
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
