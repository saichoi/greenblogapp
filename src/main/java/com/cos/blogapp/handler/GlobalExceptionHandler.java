package com.cos.blogapp.handler;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.util.Script;

//Throw 받아주는 클래스
//@ControllerAdvice 가 하는일 - 1.익셉션을 핸들링, 2.@Controller의 역활까지 한다.
@ControllerAdvice  
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public @ResponseBody String error1(NoSuchElementException e) { //@ResponseBody = 자바스크립트 리턴 
		System.out.println("Error:"+e.getMessage());
		return Script.href("/","게시글 id를 찾을 수 없습니다."); //file return
	}
	
}
