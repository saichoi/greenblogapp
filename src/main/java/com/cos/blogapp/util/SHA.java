package com.cos.blogapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {													//MyAlgorithm 타입만 넣을 수 있게 만든다. 
	public static String encrypt(String rawPassword, MyAlgorithm algorithm) { //함수 내부 전체에 예외처리
		//1. SHA256 함수를 가진 클래스 객체 가져오기 
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm.getType()); //getInstance = 실생시 new(static)한 것을 재사용(싱글톤)
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		
		//2. 비밀번호 1234 -> SHA2256에 던지기
		md.update(rawPassword.getBytes()); //바이트로 바꿔서  

		//3. 암호화된 글자를 16진수로 변환(헥사코드)
		StringBuilder sb = new StringBuilder();
		for(Byte b : md.digest()) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
		
	}
}
