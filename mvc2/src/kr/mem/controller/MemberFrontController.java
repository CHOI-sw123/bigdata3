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
	
	// MemberFrontController(����) �ϳ��� �ϰ� ������ ��Ʈ�ѷ����� FOJO(�Ϲ� Ŭ���� ����)�� ����
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("euc-kr");
		
		// 1.���û���� �ľ��ϴ� �۾� -> *.do�� ���� Ȯ���� �𸣱� ������
		String reqUrl = request.getRequestURI();
		//System.out.println(reqUrl);
		
		// ���ؽ�Ʈ�� ã�� ���(/mvc1)
		String ctxPath = request.getContextPath();
		//System.out.println(ctxPath);
		
		// Ŭ���̾�Ʈ�� ��û�� ���
		String command = reqUrl.substring(ctxPath.length());
		//System.out.println(command);
		
		Controller controller = null;
		String nextView = null;
		
		HandlerMapping mappings = new HandlerMapping();
		controller = mappings.getController(command);
		nextView = controller.requestHandle(request, response);
		
		
		
		// �ڵ鷯���� : Ŭ���̾�Ʈ�� ��û�� ���� FOJO���� ��������ִ� �۾�
		// list.do --> MemberListController
		// insert.do --> MemberInsertController
		// insertForm.do --> MemberInsertFormController
		// delete.do --> MemberDeleteController
		
		// �� ��û�� ���� ó���ϱ�(�б��۾�)
//		if(command.equals("/list.do")) {
//			
//			// controller ��ĳ����
//			controller = new MemberListController();
//			nextView = controller.requestHandle(request, response);
//			
//		}else if(command.equals("/insert.do")) {
//			
//			controller = new MemberInsertController();
//			nextView = controller.requestHandle(request, response);
//			
//		}else if(command.equals("/insertForm.do")) {
//			//response.sendRedirect("member/member.html");  // url�� �ٲ�
//			
//			controller = new MemberInsertFormController();
//			nextView = controller.requestHandle(request, response);
//			// url�� �ٲ����ʰ� �ϱ� ���� ������(insertForm.do��� url�� ������ �ְ� .do���� ��� ó���ϸ� html�� �Ѿ�� �ʱ� ������ �۾��� ������ ����)
//			
//		}else if(command.equals("/delete.do")) {
//			
//			controller = new MemberDeleteController();
//			nextView = controller.requestHandle(request, response);
//			
//		}
		
		//---------------------------------------------------------
		
		// View �������� �����ϴ� �κ�
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
