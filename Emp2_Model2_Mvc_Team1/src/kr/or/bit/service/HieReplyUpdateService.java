package kr.or.bit.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;
import kr.or.bit.dto.Reply;
import net.sf.json.JSONArray;

public class HieReplyUpdateService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = new ActionForward();
		BoardDao dao = new BoardDao();
		Board board = new Board();

		String no = request.getParameter("no");
		String idx = request.getParameter("idx");
		String comment_kwc = request.getParameter("comment_kwc");


		int row = dao.replyUpdate(Integer.parseInt(idx), Integer.parseInt(no), comment_kwc);
		
		List<Reply> replyList = dao.replylist(idx);
		
		  JSONArray jsonArr = JSONArray.fromObject(replyList);
		  
		  
		  try { response.setContentType("application/x-json; charset=UTF-8");
		  response.getWriter().print(jsonArr); } catch (IOException e) {
		  e.printStackTrace(); }
		 
		return null;
	}
}
