package com.cos.blogapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserRepository userRepository;
	private final HttpSession session;

	@GetMapping({"/","home"}) // 두가지 루트 모두 메인화면으로 가게 만든다.
	public String home() {
		return "home";
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
	public String join(JoinReqDto dto) {
		
		if(dto.getPassword() == null ||
				dto.getPassword() == null ||
				dto.getEmail() == null ||
				!dto.getUsername().equals("") ||	 
				!dto.getPassword().equals("") || 
				!dto.getEmail().equals("")
		){
			return "error/error"; //controller는 페이지 리턴할 수 없다. -> 에러페이지 만들어준다. 
		}
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm"; 
	}
	
	@PostMapping("/login")
	public String login(LoginReqDto dto) {

		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());
		
		User userEntity = userRepository.mLogin(dto.getUsername(), dto.getPassword());
		
		if(userEntity == null) {
			return "redirect:/loginForm";
		}else {
			session.setAttribute("principal", userEntity); 
			return "redirect:/home";
		}
		
	}
	

}
