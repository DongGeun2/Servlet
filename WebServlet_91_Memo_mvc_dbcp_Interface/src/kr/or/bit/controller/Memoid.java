package kr.or.bit.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.memodao;


@WebServlet("/me")
public class Memoid extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Memoid() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// 비동기 요청
    	// client 전달 ( ID사용 유무 )
    	// 1. TEXT ( HTML, text , script, css, json ) 
    	// 2. XML
    	
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset='UTF-8'");
    	PrintWriter out = response.getWriter();
    	
    	memodao dao = new memodao();
    	String id = request.getParameter("id");
    	String ischeck = dao.isCheckById(id);
    	
    	// "false" or "true"
    	// KEY POINT
    	out.print(ischeck);
    	
    	
    	
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}
}
