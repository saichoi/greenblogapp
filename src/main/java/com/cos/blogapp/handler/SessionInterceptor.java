package com.cos.blogapp.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.cos.blogapp.domain.user.User;

public class SessionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("prehandle실행됨");
		
		HttpSession session = request.getSession();
		User principal = (User) session.getAttribute("principal");
 		if(principal == null) {
 			response.sendRedirect("/loginForm"); // filter에서는 throw를 던질 수 없기 때문에 여기서도 일단 throw 안슴
 		}
 		
		return true;
	}
	
}
