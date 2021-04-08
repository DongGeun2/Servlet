package kr.or.bit.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionForward;
import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;

public class MemoListServer implements Action {

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) {
		memodao dao = new memodao();
		
		List<memo> memolist;
		ActionForward action = null;
		
		try {
			memolist = dao.getMemoList();
			request.setAttribute("memolist", memolist);
			
			// view 페이지 설정
			ActionForward acf = new ActionForward();
			acf.setPath("/memolist.jsp");
			action = acf;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

		}
		
		return action;
	}

}
