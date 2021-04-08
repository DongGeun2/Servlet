package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.registerdao;
import kr.or.bit.dto.Mvcregister;
import kr.or.bit.service.LoginOkServiceAction;
import kr.or.bit.service.RegisterOkServiceAction;

/*
 Command 방식
 @WebServlet("/WEB.do")
 web.do?cmd=login
 web.do?cmd=loginok
 
 
 URL 방식
 주소가 고정되면 안된다
 @WebServlet("*.do")
 *.do >> login.do
 *.do >> loginok.do
 
 
 */
@WebServlet("*.do")
public class RegisterFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RegisterFrontController() {
        super();
       
    }

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 요청 주소
		// 목록보기 : list.do
		// 글쓰기 : write.do
		// 글쓰기 처리 : writeok.do
		// 오면 doProcess 실행
		// 서비스 판단 방법 : command
		//                url
		//  판단   1. command 방식 :  servlet.do?cmd=login&id=kglim&pwd=1004   > cmd > if(cmd.equals("login"))
        //        2. url 방식 :      login.do?id=kglim&pwd=1004  :  "login.do" > url 주소로 요청을 판단
		
		
		// 한글처리
		request.setCharacterEncoding("UTF-8");
		
		// 데이터 받기
		//String command = request.getParameter("cmd");
		
		String requestURI = request.getRequestURI();
		String contextpath = request.getContextPath();
		String urlcommand = requestURI.substring(contextpath.length());
		
		// kr.or.bit.service : 서비스 클래스 
		Action action = null;
		ActionForward forward = null;
		
		if(urlcommand.equals("/Register.do")) {
			// UI 제공 (서비스 클래스가 필요 없다 )
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/Register/Register.jsp");
			
		}else if(urlcommand.equals("/Registerok.do")) {
			// UI 제공 + 로직 처리 
			action = new RegisterOkServiceAction();
			forward = action.execute(request, response);
			// POINT  
			// excute > request 객체의 주소와 response 주소를 전달 
			
		}else if(urlcommand.equals("/Login.do")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("/WEB-INF/Login/Login.jsp");
		}else if(urlcommand.equals("/Loginok.do")) {
			action = new LoginOkServiceAction();
			forward = action.execute(request, response);
		}
		
		if(forward != null) {
			if(forward.isRedirect()) { //treu 페이지를 재요청
				response.sendRedirect(forward.getPath()); // 거의 사용 안함 
			}else { // false
				
				// 1. UI 전달된 경우
				// 2. UI + 로직 
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
		}
		/*
		// 요청 판단해서 서비스 제어하기
		String viewpage="";
		
		if(urlcommand.equals("/Register.do")) {
			// 화면 전달 
			viewpage= "/WEB-INF/Register/Register.jsp";
		}else if(urlcommand.equals("/Registerok.do")) {
			// 로직 처리
			// 추가적인 데이터
			int id = Integer.parseInt(request.getParameter("id"));
			String pwd = request.getParameter("pwd");
			String email = request.getParameter("email");
			
			// controller -> [service 요청] -> dao 요청 ->
			Mvcregister dto = new Mvcregister();
			dto.setId(id);
			dto.setPwd(pwd);
			dto.setEmail(email);
			
			
			registerdao dao = new registerdao();
			int result = dao.writeOk(dto);
			
			String resultdata = "";
			if(result > 0) {
				resultdata = "welcome to bit " + dto.getId() + "님";
			}else {
				resultdata = "Insert Fail retry";
			}
			
			// 결과 저장하기
			request.setAttribute("data", resultdata);
			
			// 뷰 지정하기
			viewpage = "/WEB-INF/Register/Register_Welcome.jsp";
			
			
		}
		
		RequestDispatcher dis = request.getRequestDispatcher(viewpage);
		
		dis.forward(request, response);
		*/
		
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	
	}

}
