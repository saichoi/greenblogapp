package com.cos.blogapp.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserControllerTest {
	
	@GetMapping("/test/join")
	public @ResponseBody String testJoin() {
		return "test/join";
	}
	
	@GetMapping("/test/login")
	public @ResponseBody String testLoin() {
		return "<script>alert('hello');</script>";
	}
	
	//주소?name=홍길동 -> 쿼리스트링 방식 주소 -> 스프링에서는 이방싱 안씀 
	//			/test/data/1
	@GetMapping("/test/data/{num}")
	public @ResponseBody String testData(@PathVariable int num) {  //@PathVariable이 {num}파싱해서 int num안에 넣어준다.
		
		if(num == 1) { //num이 1이면 정상
			//파일리턴
			StringBuilder sb = new StringBuilder ();//문자열 더할 때사용하는 클래스 객체
			sb.append("<script>");
			sb.append("location.href='/';");
			sb.append("</script>");
			
			return sb.toString(); // sb를 문자열로 바꾼다.
		}else { // num!=1 오류 
			//데이터리턴
			return "오류가 났습니다.";
		}
		
		
	}
}
