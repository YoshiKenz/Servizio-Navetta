package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DammiQR extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8105087658978716621L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Studente matricola = (Studente) req.getSession().getAttribute("studente");
//		// resp.getWriter().println(matricola);
//		int matricolaReg = 0;
//		// matricolaReg = Integer.parseInt(matricola);
//		System.out.println(matricola.getNome() + " sfd");
//		// req.setAttribute("codice", matricola);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/generaQR.jsp");
		rd.forward(req, resp);
	}
}
