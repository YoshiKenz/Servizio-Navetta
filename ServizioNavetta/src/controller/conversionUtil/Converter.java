package controller.conversionUtil;

import java.util.Calendar;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.persistentModel.Prenotazione;
//singleton??
public class Converter {
	public Converter() {
		
	}
	
	
	public Prenotazione getPrenotazione(String code) {
		//espressioni regolari?
		String[] tokens = code.split("-");
		String codePren = tokens[tokens.length-1];
		codePren = codePren.substring(0, codePren.length()-3);
		System.out.println(codePren);
		DAOFactory daoFactory = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC prenotationDao = (PrenotazioneDaoJDBC) daoFactory.getPrenotazioneDAO();
		Prenotazione prenotation = (Prenotazione) prenotationDao.findByPrimaryKey(codePren);
		return prenotation;
	}
	
	public String getCode(Prenotazione prenotazione) {
		Calendar calendar = prenotazione.getDateTime();
		String codePrenString =  String.valueOf(prenotazione.getID());
		int n = (int)  (Math.random()*100000);
		String codeRandom = String.valueOf(n);
		String code = calendar.get(Calendar.HOUR_OF_DAY) + "-" +calendar.get(Calendar.MINUTE) +"-" + codeRandom + "-" + codePrenString;
		return code; 
	}
}
