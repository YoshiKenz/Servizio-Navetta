package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.PostgresDAOFactory;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.persistentModel.Password;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;

public class IscriviStudente extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6007577153637459820L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String matricola = req.getParameter("matricola");
		// resp.getWriter().println(matricola);
		int matricolaReg = 0;
		matricolaReg = Integer.parseInt(matricola);
		String nome = req.getParameter("nome");
		String cognome = req.getParameter("cognome");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String passwordConf = req.getParameter("password2");
		Password passReg = new Password();
		passReg.password = password;
		// resp.getWriter().println(nome + " " + cognome + " " + email + " " + password
		// + " " + matricolaReg);
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		StudenteDaoJDBC sdao = (StudenteDaoJDBC) df.getStudenteDAO();
		Studente s = sdao.findByPrimaryKey(matricola);
		if (s != null) {
			req.getSession().setAttribute("registration-error", "Studente con matricola gi� esistente");
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/iscriviStudenti.jsp");
			rd.forward(req, resp);
			// resp.getWriter().println("esiste non si puo registrare");
			// resp.getWriter().println("<option value=\"sbagliat" + "\">" + "</option>");
		} else if (ceEmail(email)) {
			req.getSession().setAttribute("registration-error", "Email gi� esistente");
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/iscriviStudenti.jsp");
			rd.forward(req, resp);
		} else if (!password.equals(passwordConf)) {
			req.getSession().setAttribute("registration-error", "Password non uguali");
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/iscriviStudenti.jsp");
			rd.forward(req, resp);
		} else {
			s = new Studente(matricolaReg, 0, nome, cognome, email, passReg);
			sdao.save(s);
			// ArrayList<Prenotazione> pr = prenStudente(s);
			// req.getSession().setAttribute("studente", s);
			// req.getSession().setAttribute("prenotazioni", pr);
			req.getSession().setAttribute("registration-error", null);
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/home.jsp");
			rd.forward(req, resp);
		}
	}

	@SuppressWarnings("unused")
	private ArrayList<Prenotazione> prenStudente(Studente s) {
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC pdao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		ArrayList<Prenotazione> p = pdao.findByReference(s);
		return p;
	}

	private boolean ceEmail(String email) {
		DAOFactory df = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		StudenteDaoJDBC sdao = (StudenteDaoJDBC) df.getStudenteDAO();
		ArrayList<Studente> s = sdao.findAll();
		for (Studente studente : s) {
			if (studente.getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}
}
