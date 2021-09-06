package com.cos.blogapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.web.dto.JoinReqDto;

@Controller
public class UserController {

	private UserRepository userRepository;
	private HttpSession session; // dependecy injection

	public UserController(UserRepository userRepository, HttpSession session) { // IoC컨테이너에서 찾아서 주입한다.
		this.userRepository = userRepository;
		this.session = session;
	}

	@GetMapping("/home")
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

		userRepository.save(dto.toEntity());

		return "redirect:/loginForm"; 
	}

}
