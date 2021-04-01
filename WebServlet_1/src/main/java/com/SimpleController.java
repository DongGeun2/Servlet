package com;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 	web.xml 설정
 	<url-pattern>/simple</url-pattern>
 	localhost:8090/Webservlet/simple 요청이 들어오면
 	
 	public class SimpleController 자바 파일 컴파일 실행
 
 	[ 서블릿(java 로 만든 웹 서비스 파일) ]
 	서블릿 조건 : java 파일이 extends HttpServle 상속 >> 웹 요청(request) 객체, 응답(response) 객체 사용
 	- servlet은 url에서 바로 요청 안된다 >> 요청 >> mapping >> 요청 주소 생성 
 	-1. web.xml
 	-2. @webSevlet
 	
 	- extends HttpServlet 반드시 상속 ( 해야만 웹에서 사용 가능 )
 	- simpleController 서블릿 파일 웹 전용
 	- servlet은 이벤트 기반의 동작 ( 특정사건 (이벤트) 가 발생하면 [자동 호출되는 함수 존재])
 	- 자동 호출 함수
 		protected void doGet()
 		>> localhost:8090/Webservlet/simple      요청방식 GET 방식 > 자동 doGet() 호출
 		>> <form method="GET" or <a href="/simple?num=100 ... 
 		
 		protected void doPost()                  요청방식 POST방식일 경우 자동으로 doPost() 호출
 		>> <form method="POST" 
 		
 		-doGet(), doPOST() >> 데이터 받기 (request, response) 사용할 수 있어야 한다
 		>> doPOST(HttpServletRequest request, HttpServletResponse response)
 		>> 함수 안에서 request, response 객체를 사용할 수 있다
 */
public class SimpleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SimpleController() {
        super();
        System.out.println("SimpleController생성자 함수 호출 ");
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트 요청 : GET 방식");
		
		// JSP 페이지 작업 내용 그대로 
		
		// 1. 한글 처리
		request.setCharacterEncoding("UTF8");
		// 2. 데이터 받기 (요청 의도 파악)
		String type = request.getParameter("type");
		
		// 3. 로직 수행 ( 요청에 따른 업무 수행 ) >> service
		Object resultobj = null;
		if(type == null || type.equals("greeting")) {
			resultobj = "hello world";
		}else if(type.equals("date")){
			resultobj = new Date();
		}else {
			resultobj = "invalid String obj";
		}
		
		// 4. 요청 완료에 따른 결과 저장 
		// MVC 패턴 방식  >  (화면: JSP ) 서블릿에서 만든 객체정보를 ( JSP 전달 )
		// 결과를 저장 : session 변수 , 특정 페이지( include , forward ) request변수
		request.setAttribute("result", resultobj);
		
		// 5. 저장한 결과를 JSP 페이지에 전달해서(UI) 구성
		// 결과를 forward 방식을 통해서 JSP에게 넘겨준다
		// 요청한 주소는 변화가 없고 buffer forward 페이지 정보를 담아서 서비스
		
		RequestDispatcher dis = request.getRequestDispatcher("/simplevies.jsp"); // 뷰 페이지를 정의한다
		
		// 정의한 페이지를 forward 
		dis.forward(request, response);
		// servlet 가지고 있는 request 객체의 주소 response 객체의 주소를 넘겨서 사용하게 하겟다.
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트 요청 : POST 방식");
	}

}
