package com.cos.blogapp.handler.ex;
/**
 * 
 * @author dahyechoi 2021.09.16
 * 1.id를 못찾았을 때 사용
 * 
 *
 */

public class MyNotFountException extends RuntimeException{
	
	public MyNotFountException(String msg) {
		super(msg);
	}
	
}
