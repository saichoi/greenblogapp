package com.cos.blogapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.handler.ex.MyAsyncNotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;
import com.cos.blogapp.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	// 이게 하나의 서비스인가? (principal 값 변경, update 하고 세션 값 변경(x))
	// 리퀘스트 관련 코드는 따로 빼준다.
	// principal 값 변경하는 연산하는데 오래 걸리는가?
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class) // 트랜젝션에서 하나라도 실패하면 전부 롤백 됨
	public void 회원수정(User principal, UserUpdateDto dto) {
		
		User userEntity = userRepository.findById(principal.getId())
				.orElseThrow(()-> new MyAsyncNotFoundException("회원정보를 찾을 수 없습니다."));
		userEntity.setEmail(dto.getEmail());
		// 원래는 리턴 받아서 성공/실패를 전달해주는 게 좋다.
	} // 더티 체킹(save x) 
	
	@Transactional(rollbackFor = MyNotFoundException.class) // 트랜젝션에서 하나라도 실패하면 전부 롤백 됨
	public void 회원가입(JoinReqDto dto) {
		// 정상일 때
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		dto.setPassword(encPassword); 
		userRepository.save(dto.toEntity()); 
	}
	
	public User 로그인(LoginReqDto dto) {
		return userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
	}
	
}
