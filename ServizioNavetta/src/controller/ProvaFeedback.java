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
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;

public class ProvaFeedback extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4440958004855659979L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String matricola = (String) req.getSession().getAttribute("username");
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC pdao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		StudenteDaoJDBC sdao = (StudenteDaoJDBC) df.getStudenteDAO();
		Studente s = sdao.findByPrimaryKey(matricola);
		ArrayList<Prenotazione> p = pdao.findByReference(s);
		req.getSession().setAttribute("prenotazione", p);
		RequestDispatcher rq = req.getRequestDispatcher("WEB-INF/dynamicPages/feedback.jsp");
		rq.forward(req, resp);
	}
}
