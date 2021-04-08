package kr.or.bit.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;

public class MemoServletService implements Action {

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String content = request.getParameter("content");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		ActionForward actionForward = new ActionForward();
		
		
		try {
			memodao dao = new memodao();
			out = response.getWriter();
			//memo m = new memo();
			int row = dao.insertMemo(new memo(id,email,content));
			
			if(row > 0) {
				actionForward.setPath("/MemoList.do");
			}else {
				actionForward.setPath("/memo.html");
			}	
		}catch (Exception e) {
		
			System.out.println(e.getMessage());
		}
		
		
		return actionForward;
	}

}
