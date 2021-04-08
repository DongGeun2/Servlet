package kr.or.bit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response);
	
	// 당신이 만약 action 인터페이스를 구현한다면 
	// execute 함수를 반드시 구현
	// execute(){ return new ActionForward(); } 강제
	
}
