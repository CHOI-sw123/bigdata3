package kr.mem.pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.mem.model.MemberDAO;

public class MemberDeleteController implements Controller{

	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int num=Integer.parseInt(request.getParameter("num"));
		
		MemberDAO dao = new MemberDAO();
		int cnt = dao.memberDelete(num);
		String page = null;
		String cpath = request.getContextPath();
		
		if(cnt > 0) {
			page = "redirect:"+cpath+"/list.do";	// 프론트 컨트롤러에서 redirect방식이면 경로설정에서 표기!
		}else {
			throw new ServletException("error");
		}
		
		return page;
	}

}
