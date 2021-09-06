package com.cos.blogapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//@RequireArgsConstrutor : private final UsreRepository userRepository
@Controller
public class UserController {
	
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
	

}
