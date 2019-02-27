package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeDopoObliterazione extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2425609517766172775L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("registration-error", null);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/driver.jsp");
		rd.forward(req, resp);
	}
}
