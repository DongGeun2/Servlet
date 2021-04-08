package kr.or.bit.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;


@WebServlet("/me3")
public class MemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public MemoServlet() {
        super();
    }

	protected void doprocess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// localhost:8090/Webserlet_4_memo_mvc/	MemoServlet
		// 이렇게 요청이 들어오면 실행
		
		// servlet 하만 가지고 작업 가능 ( FrontServlet )
		// 현재 실습은 요청당 >> Servlet 하나를 생성 
		
		// INSERT
		// 1. 한글처리
		// 2. 데이터 받기
		// 3. 비지니스 (처리)
		// 4. 결과
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String content = request.getParameter("content");
		
		System.out.println(id + email + content);
		// 별도의 UI를 가지지 않고
		// 성공 했으면 목록보기로 이동 
		// 실패하면 다시 입력
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			memodao dao = new memodao();
			//memo m = new memo();
			int row = dao.insertMemo(new memo(id,email,content));
			
			if(row > 0) {
				out.print("<script>");
					out.print("alert('등록성공');");
					out.print("location.href='MemoList';");
				out.print("</script>");
				
			}else {
				//
			}
			
		}catch (Exception e) {
			out.print("<script>");
				out.print("alert('등록실패')");
				out.print("location.href='memo.html'");
			out.print("</script>");
			System.out.println(e.getMessage());
		}
		
	}
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doprocess(request, response);
	}

}
