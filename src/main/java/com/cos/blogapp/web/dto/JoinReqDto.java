package com.cos.blogapp.web.dto;

import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
	
	public User toEntity() {
		User user = new User();
		//user.setId(1); 원래는 입력받지 않는 데이터를 입력하면 update문이 실행 
		user.setUesrname(username);
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}
}
