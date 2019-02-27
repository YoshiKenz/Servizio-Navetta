package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.SecurityDAO;
import persistence.persistentModel.Autista;
import persistence.persistentModel.Password;
import persistence.persistentModel.Studente;

/**
 * Servlet implementation class doLogin
 */
@WebServlet(description = "dopo Login home")
public class DoLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("login-error");
		//		String prova = (String) request.getAttribute("id");
		String username = (String) request.getParameter("id");
		String pass = (String) request.getParameter("pass");
		try {
			Integer.parseInt(username);
		}
		catch (NumberFormatException e) {
			request.getSession().setAttribute("login-error", "L'username deve essere un valore numerico.");
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
			rd.forward(request, response);
			return;
		}
		DAOFactory daoFactory = DatabaseManager.getInstance().getDaoFactory();
		SecurityDAO credenziali = daoFactory.getPersonaSecureDAO();
		credenziali.authorizeDao("1", "p");
		Password checkPass = (Password) credenziali.retriveSensitiveData(username);
		if(checkPass==null) {
			request.getSession().setAttribute("login-error", "L'utente non esiste.");
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
			rd.forward(request, response);
			return;
		}
		else if(checkPass.password.equals(pass)==false) {
			request.getSession().setAttribute("login-error", "Password errata.");
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
			rd.forward(request, response);
			return;
		}
		String type =  request.getParameter("login-type");
		switch(type) {
		case "driver" : {
			Crud autistaDao=daoFactory.getAutistaDAO();
			Autista a= (Autista) autistaDao.findByPrimaryKey(username);
			if(a==null) {
				request.getSession().setAttribute("login-error", "Non sei un Autista.");
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
				rd.forward(request, response);
				return;
			}
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("tipo-login", type);

			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/driver.jsp");
			rd.forward(request, response);
			return;
		}
		case "student" : {
			Crud studenteDao=daoFactory.getStudenteDAO();
			Studente a= (Studente) studenteDao.findByPrimaryKey(username);
			if(a==null) {
				request.getSession().setAttribute("login-error", "Non sei uno Studente.");
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
				rd.forward(request, response);
				return;
			}
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("tipo-login", type);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/homeStudente.jsp");
			rd.forward(request, response);
			return;
		}
		default :{

		}
		}
	}

}
