package ca.utoronto.ece.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException{
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException{
		String logoutURL = (String)request.getSession().getAttribute("logoutURL");
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		
		response.sendRedirect(logoutURL);
	}
}
