package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DopoIscrizioneSenzaErrori extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5440092540205709127L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("registration-error", null);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
		rd.forward(req, resp);
	}
}
