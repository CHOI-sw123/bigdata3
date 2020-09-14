package kr.mem.pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	// FrontController가 할 일을 FOJO들이 대신해주는 메소드
	// 서블릿이하는 일을 FOJO들이 대신해야 하기 때문에 서블릿과 같은 메소드(request, response)가 필요함
	public String requestHandle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;
	
}
