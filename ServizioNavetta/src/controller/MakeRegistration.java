package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MakeRegistration
 */
@WebServlet("/MakeRegistration")
public class MakeRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = (String) request.getSession().getAttribute("username");
		String mode = (String) request.getSession().getAttribute("login-type");
		if(username!=null) {
			if(mode=="driver")
			{
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/driver.html");
				rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/homeStudente.html");
				rd.forward(request, response);
			}
		}else {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/registration.jsp");
		rd.forward(request, response);}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
