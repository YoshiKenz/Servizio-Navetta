package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.RegistroAttivitaNavette;
import model.geoUtil.FermataComparator;
import model.geoUtil.GeoUtil;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.crud.Crud;
import persistence.persistentModel.Fermata;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.Studente;
import persistence.persistentModel.TrattoLinea;
import persistence.daoManage.jdbcDao.FermataDaoJDBC;

public class CreaPrenotazione extends HttpServlet{

	/**
	 * 
	 */

	private static final int NUMERO_FERMATE_VICINE = 5;
	private static final int NUMERO_MASSIMO_FLAG = 5;


	private static final long serialVersionUID = 7019570969697763456L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("In servlet : CreaPrenotazione");

		String state = (String) req.getParameter("state");
		if(state==null) {
			/*Verifica ban*/
			Crud studenteDao = DatabaseManager.getInstance().getDaoFactory().getStudenteDAO();
			String matricola = (String) req.getSession().getAttribute("username");
			int nFlag= ((Studente)studenteDao.findByPrimaryKey(matricola)).getFlag();
			if(nFlag>NUMERO_MASSIMO_FLAG) {
				RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/inviaDomandaRiabilitazione.jsp");
				rd.forward(req, resp);
				return;
			}
			/**/
			RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/NuovaPrenotazioneMap.jsp");
			rd.forward(req, resp);
			return;
		}

		switch(state) {

		case "partenza":
		case "arrivo":{
			double actualLat = Double.parseDouble(req.getParameter("actual-lat"));
			double actualLng = Double.parseDouble(req.getParameter("actual-lng"));
			List<Fermata> fermatevicine = new ArrayList<Fermata>();
			DAOFactory df = DatabaseManager.getInstance().getDaoFactory();

			FermataDaoJDBC fermataDao = (FermataDaoJDBC) df.getFermataDAO();
			ArrayList<Fermata> tuttefermate = (ArrayList<Fermata>) fermataDao.findAll();
			tuttefermate.sort(new FermataComparator(actualLat, actualLng));
			
			fermatevicine = tuttefermate.subList(0, NUMERO_FERMATE_VICINE);

			
			ArrayList<JSONObject> fermateJson = new ArrayList<JSONObject>();
			for(Fermata f : fermatevicine) {
				fermateJson.add(new JSONObject(f));
			}
			JSONArray fermate = new JSONArray(fermateJson);
			resp.getOutputStream().println(fermate.toString());
			break;
		}

		case "computeLine" :{

			String partenzaStr = (String) req.getParameter("start-point");
			String arrivoStr = (String) req.getParameter("stop-point");
			JSONObject partenzaJson = null,arrivoJson=null;
			String partenzaID="",arrivoID="";
			try {
				partenzaJson = new JSONObject(partenzaStr);
				arrivoJson = new JSONObject(arrivoStr);
				partenzaID = partenzaJson.getString("nome");
				arrivoID = arrivoJson.getString("nome");


			}catch(JSONException ex) {/*Handle exception*/}
			DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
			Crud fermataDao = df.getFermataDAO();
			Fermata partenza = (Fermata) fermataDao.findByPrimaryKey(partenzaID), 
					arrivo = (Fermata) fermataDao.findByPrimaryKey(arrivoID);
			try {
				if(partenza==null)
					partenza = new Fermata(partenzaJson.getString("nome"), 
							Double.parseDouble(partenzaJson.getString("latitudine")), 
							Double.parseDouble(partenzaJson.getString("longitudine")));
				if(arrivo==null)
					arrivo = new Fermata(arrivoJson.getString("nome"), 
							Double.parseDouble(arrivoJson.getString("latitudine")), 
							Double.parseDouble(arrivoJson.getString("longitudine")));

			}catch(JSONException ex) {/*Handle exception*/}
			ArrayList<ArrayList<TrattoLinea> > routes = GeoUtil.computeRoutes(partenza, arrivo);
			ArrayList<JSONArray> arrayRoutes = new ArrayList<JSONArray>();
			for(ArrayList<TrattoLinea> at : routes) {
				ArrayList<JSONObject> route = new ArrayList<JSONObject>();
				for(TrattoLinea tr : at) {
					route.add(new JSONObject(tr));
				}
				arrayRoutes.add(new JSONArray(route));
			}
			JSONArray routesArray = new JSONArray(arrayRoutes);
			
			resp.getOutputStream().println(routesArray.toString());
			
			return;
		}
		case "computeBus" : {
			
			
		}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String jsonSelectedLineArray = req.getParameter("tratto-array");
		
		try {
			JSONArray arrayTratti = new JSONArray(jsonSelectedLineArray);
			ArrayList<TrattoLinea> route = new ArrayList<TrattoLinea>();
			for(int i=0;i<arrayTratti.length();i++) {
				JSONObject partenzaJson = arrayTratti.getJSONObject(i).getJSONObject("partenza"),
				arrivoJson = arrayTratti.getJSONObject(i).getJSONObject("arrivo");
				Fermata partenza = new Fermata(partenzaJson.getString("nome"), partenzaJson.getDouble("latitudine"), partenzaJson.getDouble("longitudine")),
				arrivo = new Fermata(arrivoJson.getString("nome"), arrivoJson.getDouble("latitudine"), arrivoJson.getDouble("longitudine"));
				double tempo = arrayTratti.getJSONObject(i).getDouble("tempoMIN"),
				distanza = arrayTratti.getJSONObject(i).getDouble("distanzaKM");
				route.add(new TrattoLinea(partenza, arrivo, tempo, distanza));
			}
			
			RegistroAttivitaNavette registro = RegistroAttivitaNavette.getInstance();
			ArrayList<ArrayList<Navetta>> navetteXtratti = GeoUtil.computeBus(route, registro);
			req.getSession().setAttribute("listeNavette", navetteXtratti);
			req.getSession().setAttribute("tratti", route);
		} catch (JSONException e) {/*Handle exception*/}
		
		
		RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/dynamicPages/pickBus.jsp");
		rd.forward(req, resp);
	}
}
