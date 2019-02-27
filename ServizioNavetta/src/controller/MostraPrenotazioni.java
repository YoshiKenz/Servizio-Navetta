package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.conversionUtil.Converter;
import model.RegistroAttivitaNavette;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;

public class MostraPrenotazioni extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6342700169231418782L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String matricola = (String) req.getSession().getAttribute("username");
		@SuppressWarnings("unused")
		RegistroAttivitaNavette r = (RegistroAttivitaNavette) req.getServletContext().getAttribute("registro");
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		StudenteDaoJDBC sdao = (StudenteDaoJDBC) df.getStudenteDAO();
		
		Studente s = sdao.findByPrimaryKey(matricola);
		PrenotazioneDaoJDBC p = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		
		ArrayList<Prenotazione> pr = (ArrayList<Prenotazione>) p.findByReference(s);
		ArrayList<String> c = new ArrayList<String>();
		Converter co = new Converter();
		for (Prenotazione pren : pr) {
			System.out.println(pren.getID());
			c.add(co.getCode(pren));
		}
		for (String string : c) {
			System.out.println(string + " ee");
		}
		for (Prenotazione pe : pr) {
			System.out.println(pe.getID() + "idd");
		}
		// ArrayList<Prenotazione> prFinal = new ArrayList<Prenotazione>();
		// System.out.println(prFinal.size() + " ssss");
		req.setAttribute("prenotazione", pr);
		req.setAttribute("codici", c);
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/generaPrenotazioni.jsp");
		rd.forward(req, resp);
	}
}
