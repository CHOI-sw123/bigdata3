package kr.mem.pojo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.mem.model.MemberDAO;
import kr.mem.model.MemberVO;

public class MemberListController implements Controller{

	// 부모클래스인 Controller의 requestHandle 메소드를 오버로딩
	@Override
	public String requestHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberVO> list = dao.memberAllList();
		// 객체 바인딩
		request.setAttribute("list", list);
		// View페이지 경로 넘기기
		return "member/memberList.jsp";	// 포워딩은 경로가 나타나지 않음(WEB-INF가 URL에 드러나면 안됨)
	}
	
}
