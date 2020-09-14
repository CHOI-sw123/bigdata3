package kr.mem.pojo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	// FrontController�� �� ���� FOJO���� ������ִ� �޼ҵ�
	// ���������ϴ� ���� FOJO���� ����ؾ� �ϱ� ������ �������� ���� �޼ҵ�(request, response)�� �ʿ���
	public String requestHandle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;
	
}