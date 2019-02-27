package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import controller.conversionUtil.Converter;
import controller.conversionUtil.Validator;
import persistence.persistentModel.Prenotazione;

/**
 * Servlet implementation class ObliteraBiglietto
 */

public class ObliteraBigliettoQR extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObliteraBigliettoQR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/dynamicPages/obliteraBiglietto.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonReceived = "";
		String nameAutista;
		Converter convertitor = new Converter();
		Validator validator = new Validator();
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = reader.readLine();
		while (line != null) {
			jsonReceived = jsonReceived + line + "\n";
			line = reader.readLine();
		}
		JSONObject jsonResult;
		nameAutista= (String) request.getSession().getAttribute("username");
		Prenotazione prenotation = convertitor.getPrenotazione(jsonReceived);
		try {
			jsonResult = new JSONObject();
			if(validator.validate(prenotation,nameAutista)) {
				jsonResult.put("verified",true);}	
			else {
				jsonResult.put("verified", false);
			}
			response.getWriter().println(jsonResult);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

}
