package com.cos.blogapp.util;

public class Script {

		//실패했을 때 메서드 
		public static String back(String msg) {
			StringBuilder sb = new StringBuilder ();//문자열 더할 때사용하는 클래스 객체
			sb.append("<script>");
			sb.append("alert(' "+msg+" ');"); //에러맵에 담긴 메시지(msg)를 alert창으로 띄운다.
			sb.append("history.back();"); //원래 페이지로 돌아간다.
			sb.append("</script>");
			
			return sb.toString();
		}
		
		//성공했을 때 메서드 - 이동만 하는 것
		public static String href(String path) {
			StringBuilder sb = new StringBuilder ();
			sb.append("<script>");
			sb.append("location.href='"+path+"';"); 
			sb.append("</script>");
			
			return sb.toString();
		}
		
		//성공했을 때 메서드 - alert 창을 띄우고 이동  
		public static String href(String path, String msg) {  //함수 이름 같게 한다(오버로딩:개발자에게 기억하기 쉽게). 매개변수 바꾼다.
			StringBuilder sb = new StringBuilder(); 
			sb.append("<script>");
			sb.append("alert(' "+msg+" ');"); 
			sb.append("location.href='"+path+"';"); 
			sb.append("</script>");
			
			return sb.toString();
		}
	
}
