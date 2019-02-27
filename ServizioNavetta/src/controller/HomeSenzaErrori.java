package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeSenzaErrori extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9158374689381900638L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("registration-error", null);
		String type = (String) req.getSession().getAttribute("tipo-login");
		switch (type) {
		case "driver": {
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/driver.jsp");
			rd.forward(req, resp);
			return;
		}
		case "student": {
			req.getSession().setAttribute("request-error", null);
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/homeStudente.jsp");
			rd.forward(req, resp);
			return;
		}
		default: {

		}
		}
	}
}
