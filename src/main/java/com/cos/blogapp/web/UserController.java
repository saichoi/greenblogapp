package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.service.UserService;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.CMRespDto;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;
import com.cos.blogapp.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	// DI
	private final UserService userService;
	private final HttpSession session;

	// 회원정보 수정 
	@PutMapping("/user/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @Valid @RequestBody UserUpdateDto dto, BindingResult bindingResult) {

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
		
		//권한 체크
		if (principal.getId() != id) {
			throw new MyAsyncNotFoundException("회원정보를 수정할 권한이 없습니다.");
		}

	// 핵심로직 
		userService.회원수정(principal, dto);
		session.setAttribute("principal", principal); // 세션값 변경
		
		return new CMRespDto<String>(1,"성공",null);
		
	}

	// 회원정보 보기 
	@GetMapping("/user/{id}")
	public String userInfo(@PathVariable int id) {
		return "user/updateForm";
	}

	// 로그아웃 
	@GetMapping("/logout")
	public String logout() {
		session.invalidate(); 
		return "redirect:/";
	}

	// 로그인 페이지 이동 
	@GetMapping("/loginForm")
	public String login() {
		return "/user/loginForm";
	}

	// 회원가입 페이지 이동 
	@GetMapping("/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}

	// 회원가입 
	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) {

		// 실패했을 때
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

		userService.회원가입(dto);
		return Script.href("/loginForm");
	}

	// 로그인 
	@PostMapping("/login")
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {

		// 실패했을 때
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

		User userEntity = userService.로그인(dto);

		if (userEntity == null) { 
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		} else {
			session.setAttribute("principal", userEntity);
			return Script.href("/", "로그인 성공");
		}

	}

}
