package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RegistroAttivitaNavette;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.AutistaDaoJDBC;
import persistence.daoManage.jdbcDao.NavettaDaoJDBC;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.daoManage.jdbcDao.TrattoLineaDaoJDBC;
import persistence.persistentModel.Autista;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;
import persistence.persistentModel.TrattoLinea;
import persistence.utility.IdProvider;

/**
 * Servlet implementation class FinalizzaPrenotazione
 */

public class FinalizzaPrenotazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizzaPrenotazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int dim = Integer.parseInt(request.getParameter("dimension"));
		ArrayList<Integer> navs = new ArrayList<Integer>();
		for(int i=0;i<dim;i++) {
			String strId = request.getParameter("tratto-"+i);
			int id = Integer.parseInt(strId);
			navs.add(Integer.valueOf(id));
			/*DEBUG*/
			System.out.println(navs.get(i));
			/*DEBUG*/
		}
		
		String username = (String) request.getSession().getAttribute("username");
		
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		
		PrenotazioneDaoJDBC pDao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		NavettaDaoJDBC nDao = (NavettaDaoJDBC) df.getNavettaDAO();
		AutistaDaoJDBC aDao = (AutistaDaoJDBC) df.getAutistaDAO();
		StudenteDaoJDBC sDao = (StudenteDaoJDBC) df.getStudenteDAO();
		TrattoLineaDaoJDBC tDao = (TrattoLineaDaoJDBC) df.getTrattoLineaDAO();
		RegistroAttivitaNavette registro = RegistroAttivitaNavette.getInstance();
		
		int iterator = 0;
		for(Integer ing : navs) {
			int ID = IdProvider.getInstance().getNextId("prenotazione");
			/*TODO determinare giro*/
			int giro = 0;
			/**/
			String strPartenza = request.getParameter("start-"+iterator);
			String strArrivo = request.getParameter("stop-"+iterator);
			TrattoLinea tratto = tDao.findByPrimaryKeyComposed(strPartenza, strArrivo);
			Navetta navetta = (Navetta) nDao.findByPrimaryKey(ing.intValue()+"");
			Calendar dateTime = new Calendar.Builder().setInstant(new Date()).build();
			Autista autista = (Autista) aDao.findByPrimaryKey(registro.getIdAutista(navetta.getID())+"");
			Studente studente = sDao.findByPrimaryKey(username);
			Prenotazione p = new Prenotazione(ID, giro, navetta, tratto, dateTime, autista, studente);
			pDao.save(p);
			iterator++;
		}
		
		/*CLEAN SESSION*/
		String type = (String) request.getSession().getAttribute("tipo-login");
		Enumeration<String> attributes  = request.getSession().getAttributeNames();
		try {
			while(true) {
				String el = attributes.nextElement();
				request.getSession().removeAttribute(el);
			}
		}catch(NoSuchElementException e) {}
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("tipo-login", type);
		/*CLEAN SESSION*/
		
		RequestDispatcher rd = request.getRequestDispatcher("/home");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
