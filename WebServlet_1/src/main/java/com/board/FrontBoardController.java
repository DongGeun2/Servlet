package com.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// web.xml 설정
public class FrontBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontBoardController() {
        super();
    }
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException  {
    	// GET, POST 두가지 요청에 대해서 동작 함수
    	// method >> 요청 >> GET,POST 확인
    	System.out.println("클라이언트 요청 : " + method);
    	
    	// 1. 한글처리
    	// 2. 요청받기 (request)
    	// 3. 요청판단 (판단의 기준 : parameter 기준 >> command 방식 )
    	// 서버는 cmd 변수가 가지는 값을 가지고 판단 : login >> 로그인 처리 
    	
    	// String command = request.getParameter("cmd")
    	// if(command.equals("login"){ 로그인 서비스 처리 }
    	// else if(command.equals("list"){ 게시판 목록 }
    	
    	// command 방식 반대 >> URL주소 방식
    	// 마지막 주소값을 추출 
    	
    	// 4. 결과 저장 ( request.setattr ... , session.setattr ... , application.setattr ... )
    	
    	
    	// 5. view 저장
    	// view page : *.jsp
    	// webContent > board > boardlist.jsp
    	// webContent > error > error404.jsp
    	
    	// 위 방식은 보안상 문제 >> 실개발 >> webContent >> WEB-INF > views 폴더 생성 > board or member or admin 폴더 생성후 관리 
    	
    	// ex) 보안폴더 : WEB-INF > views > board > boardlist.jsp 
    	
    	// 6. view 에게 request 
    	
    	request.setCharacterEncoding("UTF8");
    	
    	String cmd = request.getParameter("cmd");
    	
    	// 요청판단 
    	String viewpage = null;
    	
    	// cmd > null > error.jsp
    	// cmd > boardlist > list.jsp
    	// cmd > boardwrite > write.jsp
    	
    	if(cmd==null) {
    		viewpage = "/error/error.jsp";
    	}else if(cmd.equals("boardlist")) {
    		// 실제 업무 처리 
    		/*
    		 DB 연결 > select > 객체 담고 > 저장 
    		 boardDao dad = new boradDad();
    		 List<board> boardlist = dao.selectBoardList();    
    		 requset.setAttribute("list",boardlist);
    		 forward > view > 전달 ( request.getAttribute()) 가지고 와서 화면 출력 
    		 출력시에는 EL & JSTL 사용		     		 
    		  */
    		viewpage = "/board/boardlist.jsp";
    	}else if(cmd.equals("boardwrite")) {
    		viewpage = "/board/boardwrite.jsp";
    	}else if(cmd.equals("login")) { // 보안폴더
    		viewpage = "/WEB-INF/views/login/login.jsp";
    	}else {
    		viewpage = "/error/error.jsp";
    	}
    	
    	// 결과 저장하기 
    	// List<Emp> list = new ArrayList<>();
    	// list.add(new Emp(200,김유신);
    	// request.setAttribute("emplist",list);
    	
    	
    	// view 지정
    	RequestDispatcher dis = request.getRequestDispatcher(viewpage);    	
    	
    	// 데이터 전달 (forward)
    	dis.forward(request, response);
    	
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response,"GET");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response,"POST");
	}

}
