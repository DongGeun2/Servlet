package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.registerdao;
import kr.or.bit.dto.Mvcregister;

public class RegisterOkServiceAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		
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
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/WEB-INF/Register/Register_Welcome.jsp");
		
		return forward;
	}

}
