package kr.mem.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.mem.model.MemberDAO;
import kr.mem.pojo.Controller;
import kr.mem.pojo.MemberDeleteController;
import kr.mem.pojo.MemberInsertController;
import kr.mem.pojo.MemberInsertFormController;
import kr.mem.pojo.MemberListController;


public class MemberFrontController extends HttpServlet {
	
	// MemberFrontController(서블릿) 하나만 하고 나머지 컨트롤러들은 FOJO(일반 클래스 파일)로 생성
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		// 1.어떤요청인지 파악하는 작업 -> *.do로 오면 확실히 모르기 때문에
		String reqUrl = request.getRequestURI();
		//System.out.println(reqUrl);
		
		// 컨텍스트를 찾는 명령(/mvc1)
		String ctxPath = request.getContextPath();
		//System.out.println(ctxPath);
		
		// 클라이언트가 요청한 명령
		String command = reqUrl.substring(ctxPath.length());
		//System.out.println(command);
		
		Controller controller = null;
		String nextView = null;
		
		HandlerMapping mappings = new HandlerMapping();
		controller = mappings.getController(command);
		nextView = controller.requestHandle(request, response);
		
		
		
		// 핸들러매핑 : 클라이언트의 요청에 의해 FOJO들을 연결시켜주는 작업
		// list.do --> MemberListController
		// insert.do --> MemberInsertController
		// insertForm.do --> MemberInsertFormController
		// delete.do --> MemberDeleteController
		
		// 각 요청에 따라 처리하기(분기작업)
//		if(command.equals("/list.do")) {
//			
//			// controller 업캐스팅
//			controller = new MemberListController();
//			nextView = controller.requestHandle(request, response);
//			
//		}else if(command.equals("/insert.do")) {
//			
//			controller = new MemberInsertController();
//			nextView = controller.requestHandle(request, response);
//			
//		}else if(command.equals("/insertForm.do")) {
//			//response.sendRedirect("member/member.html");  // url이 바뀜
//			
//			controller = new MemberInsertFormController();
//			nextView = controller.requestHandle(request, response);
//			// url이 바뀌지않게 하기 위해 포워딩(insertForm.do라는 url을 가지고 있고 .do에서 모두 처리하며 html로 넘어가지 않기 때문에 작업의 편리성이 증가)
//			
//		}else if(command.equals("/delete.do")) {
//			
//			controller = new MemberDeleteController();
//			nextView = controller.requestHandle(request, response);
//			
//		}
		
		//---------------------------------------------------------
		
		// View 페이지로 연동하는 부분
		if(nextView != null) {
			if(nextView.indexOf("redirect:") != -1) {
				String[] sp = nextView.split(":");	// sp[0]:sp[1]
				response.sendRedirect(sp[1]);		// :0
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + nextView);
				rd.forward(request, response);
			}
		}
		
	}

}
