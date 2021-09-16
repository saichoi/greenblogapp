package com.cos.blogapp.test;

//Java 1.8 : 람다식
//1.함수를 넘기는 게 목적(익명함수 = 람다함수) 
//2.인테페이스에 함수가 무조건 하나여야함.
//3.코드가 간결해지고, 타입을 몰라도 됨.

interface MySupplier{
	void get();
}

public class LamdaTest {
	
	static void start(MySupplier s) {
		s.get();
	}
	
	public static void main(String[] args) {
		//start( () -> { System.out.println("get함수 호출됨"); } );
		start( () ->  System.out.println("get함수 호출됨") ); // 한줄 코드 생략가능 
		//무조건 넘어가기 때문에 함수 이름이 중요하지 않다. , 추상 메서드 자동 구현. 함수가 하나일때만 가능  
	}
}
