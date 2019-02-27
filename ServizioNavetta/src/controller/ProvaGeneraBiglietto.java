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

public class ProvaGeneraBiglietto extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674366772336696342L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ccc");
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC pdao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		StudenteDaoJDBC sdao = (StudenteDaoJDBC) df.getStudenteDAO();
		Studente s = sdao.findByPrimaryKey("45");
		ArrayList<Prenotazione> pr = pdao.findByReference(s);
		req.getSession().setAttribute("prenotazione", pr);
		req.getSession().setAttribute("studente", s);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/generaPrenotazioni.jsp");
		rd.forward(req, resp);
	}
}
