package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import model.LineaRegistroNavette;
import model.RegistroAttivitaNavette;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.persistentModel.Autista;
import persistence.persistentModel.Fermata;
import persistence.persistentModel.Linea;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.TrattoLinea;

/**
 * Servlet implementation class DriverRegisterMediator
 */

public class DriverRegisterMediator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverRegisterMediator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username =(String) request.getSession().getAttribute("username");
		/*DEBUG*/
		if(username==null) {
			username = 2+"";//Present in DB
		}
//		DAOFactory daoFact = DatabaseManager.getInstance().getDaoFactory();
		
		/*DEBUG*/
		RegistroAttivitaNavette registro = RegistroAttivitaNavette.getInstance();
		
		
		LineaRegistroNavette lineaRegistro = registro.getLineaRegistro(Integer.parseInt(username));
		if(lineaRegistro==null) {
			if(registro.addLinea(Integer.parseInt(username))==false) {
				/*Autista doesn't exist in DB*/
				// [...] DO SOMETHING
			}
			lineaRegistro = registro.getLineaRegistro(Integer.parseInt(username));
			DAOFactory daoFactory = DatabaseManager.getInstance().getDaoFactory();
			Autista autista =(Autista) daoFactory.getAutistaDAO().findByPrimaryKey(username);
			lineaRegistro.setAutista(autista);
			lineaRegistro.setGiriCompletati(0);
			/* Missing data */
			String navettaId = "1";
			String nomeLinea = "lineaF";//Present in DB
			//insert into "Linea" values (ARRAY[['l'],['i'],['n'],['e'],['a']]);
			int capolinea = 0;
			/**/
			Navetta navetta = (Navetta) daoFactory.getNavettaDAO().findByPrimaryKey(navettaId);
			lineaRegistro.setNavetta(navetta);
			Linea linea = new Linea(nomeLinea);
			ArrayList<TrattoLinea> tratti = linea.getTratti();
			if(tratti.isEmpty()==false) {
				TrattoLinea posizione = tratti.get(capolinea);
				lineaRegistro.setPosizione(posizione);
			}
			/*DEBUG*/
			else {
				Fermata f1 = new Fermata("quattromiglia", 39.3569466, 16.2263765), 
						f2 = new Fermata("University Club", 39.3591417, 16.2261368);
				TrattoLinea farlocco = new TrattoLinea(f1, f2, 1, 1);
				lineaRegistro.setPosizione(farlocco);
			}
			/*DEBUG*/
			lineaRegistro.setLinea(linea);
		}
		
		
		boolean repeat = Boolean.parseBoolean(request.getParameter("repeat"));
		
		if( !repeat) {
			int currentIndex = lineaRegistro.getLinea().getTratti().indexOf(lineaRegistro.getPosizione());
			ArrayList<TrattoLinea> tratti = lineaRegistro.getLinea().getTratti();
			if(tratti.isEmpty()) {
				
			}else if(tratti.size()-1==currentIndex) {
				lineaRegistro.setPosizione(tratti.get(0));
				lineaRegistro.handleCompletedRound();
			}else {
				lineaRegistro.setPosizione(tratti.get(currentIndex+1));
			}
		}
		
		
		
		JSONObject lineaJson = new JSONObject(lineaRegistro);
		response.getOutputStream().println(lineaJson.toString());
//		request.setAttribute("linea-registro", lineaRegistro);
		
		/*DEBUG*/
		System.out.println("Attr : "+request.getAttribute("repeat"));
		System.out.println("Session attr : "+request.getSession().getAttribute("repeat"));
		System.out.println("Param : "+request.getParameter("repeat"));
		/*DEBUG*/
		
		
	}

}
