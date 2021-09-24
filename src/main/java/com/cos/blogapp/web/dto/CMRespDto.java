package com.cos.blogapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
//	private String data;  //fail or ok -> 다른 데이터 들어올 때 안 좋음
	private int code; // 1:성공, -1:실패
	private  String msg;
	//private User body; // body 유저 데이터 받기
	//private Board body; // body 게시글 데이터 받기
	//제네릭으로 동적으로 만든다.
	private T body;
	
}
