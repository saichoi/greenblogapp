package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	//DI
	private final UserRepository userRepository;
	private final HttpSession session;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); //세션 무효화(sessionId에 있는 값을 비우는 것)
		return "redirect:/";
	}

	@GetMapping("/loginForm")
	public String login() {
		return "/user/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}

	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) {
																																			// 모델 : 의존성주입하게 만들어준다.
		
		//1.유효성 검사 실패 - 자바스크립트 응답(경고창띄우고 joinForm페이지 돌아가기(뒤로가기) )
		//2.정상 - 로그인 페이지 
		
		//System.out.println("에러사이즈" + bindingResult.getFieldErrors().size());
		
		//실패했을 때
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드 : " +  error.getField());
				System.out.println("메시지 : " +  error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		//정상일 때
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		dto.setPassword(encPassword); //길이가 길어지므로 데이터 베이스 수정해야함 -> User.java에서 길이 수정후 create
		userRepository.save(dto.toEntity()); //dto가 가지고 있는 패스워드를 hash로 만들어준다.
		return Script.href("/loginForm");  
	}
	
	@PostMapping("/login")
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {
		
		//1.유효성 검사 실패 - 자바스크립트 응답(경고창띄우고 loginForm페이지 돌아가기(뒤로가기) )
		//2.정상 - 메인 페이지 
		
		//실패했을 때
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		User userEntity =  
 				userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
		
		if(userEntity == null) { // username, password 잘못기입 
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		}else {
			//세션이 날라가는 조건 : 1.session.invalidate(), 2. 브러우저를 닫으면 
			session.setAttribute("principal", userEntity); 
			return Script.href("/","로그인 성공");
		}
		
	}
	

}
