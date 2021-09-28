package com.cos.blogapp.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.CMRespDto;

//Throw 받아주는 클래스
//@ControllerAdvice 가 하는일 - 1.익셉션을 핸들링, 2.@Controller의 역활까지 한다.
@ControllerAdvice  
public class GlobalExceptionHandler {
	
	// 일반요청 
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) { //@ResponseBody = 자바스크립트 리턴 
		System.out.println("Error:"+e.getMessage());
		return Script.href("/",e.getMessage()); //file return
	}
	
	// fetch 요청 ( 데이터를 응답 받아야할 때)
	@ExceptionHandler(value = MyAsyncNotFoundException.class)
	public @ResponseBody CMRespDto<String> error2(MyAsyncNotFoundException e) { //@ResponseBody = 자바스크립트 리턴 
		System.out.println("Error:"+e.getMessage());
		return new CMRespDto<String>(-1, e.getMessage(), null);
	}
	
}
