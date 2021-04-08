package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import kr.or.bit.service.MemoListServer;
import kr.or.bit.service.MemoServletService;

@WebServlet("*.do")
public class MemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public MemoController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String requesturl = request.getRequestURI();
		String requestpath = request.getContextPath();
		String URL = requesturl.substring(requestpath.length());
		
		System.out.println(URL);
		
		ActionForward actionForward = null;
		Action action = null;
		
		if(URL.equals("/MemoList.do")) {
			action = new MemoListServer();
			actionForward = action.excute(request, response);
				
		}else if(URL.equals("/MemoServlet.do")) {
			action = new MemoServletService();
			actionForward = action.excute(request, response);
		}
		
		if(actionForward != null) {
			RequestDispatcher dis = request.getRequestDispatcher(actionForward.getPath());
			dis.forward(request, response);
		}
		
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}

}
