package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println(request.getContextPath());
		String username = (String) request.getSession().getAttribute("username");
		System.out.println(request.getServletContext().getContextPath());
		if(username!=null) {
			String tipo_login = (String) request.getSession().getAttribute("tipo-login");
			switch(tipo_login) {
			case "driver":{
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/driver.jsp");
				rd.forward(request, response);
				return;
			}
			case "student":{
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/homeStudente.jsp");
				rd.forward(request, response);
				return;
			}
			default : {
				
			}
			}
		}else {	
			if(Boolean.parseBoolean((String) request.getAttribute("loginRedirect"))) {
				
				request.getSession().setAttribute("login-error", "Utente o password errati");
//				System.out.println("ATTR "+request.getSession().getAttribute("login-error"));
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("bla");
		response.sendRedirect("/ServizioNavetta/home");
	}

}
