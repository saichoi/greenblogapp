package com.cos.blogapp.domain.board;

class 사과 extends Object{}
class 딸기 extends Object{}

public class 바구니 { //제네릭

		public void 담기(Object b) { //사과, Object
			사과 ccc = (사과)b; //다운캐스팅 
		}
		
		public static void main(String[] args) {
			//다형성 
			//Object a = new  사과(); //사과, Object
			사과 a = new  사과(); //사과, Object
			딸기 b = new  딸기(); //딸기, Object
			
			바구니 s = new 바구니();
			s.담기(a); //사과
			s.담기(b); //딸기 
		}
		
	
}
