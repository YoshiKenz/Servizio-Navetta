package controller.conversionUtil;

import model.LineaRegistroNavette;
import model.RegistroAttivitaNavette;
import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.persistentModel.Prenotazione;

public class Validator {
	public Validator() {
		
	}
	
	public boolean validate(Prenotazione prenotation, String autista) {
		
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		PrenotazioneDaoJDBC pdao = (PrenotazioneDaoJDBC) df.getPrenotazioneDAO();
		
		if(prenotation==null) {
			return false;
			
			
		}
		RegistroAttivitaNavette registro = RegistroAttivitaNavette.getInstance();
		int autistaID = Integer.parseInt(autista);
		LineaRegistroNavette linea = registro.getLineaRegistro(autistaID);
		if(prenotation.isObliteratoEntrata()==false) {		
		// controlli mancanti su orario per esempio..
			if(prenotation.getGiro() == linea.getGiriCompletati() + 1 && prenotation.getAutista().getID() == autistaID
					&& prenotation.getNavetta().getID() == linea.getNavetta().getID()  &&
					prenotation.getTratto().getPartenza().getNome().equals(linea.getPosizione().
					getPartenza().getNome())) {
				prenotation.setObliteratoEntrata(true);
				pdao.update(prenotation);
				return true;
			}
			else
			{
				return false;
			}
		}else //si vuole obliterare l uscita
		{ 
			if (prenotation.getGiro() == linea.getGiriCompletati() + 1 && prenotation.getAutista().getID() == autistaID
					&& prenotation.getNavetta().getID() == linea.getNavetta().getID()  &&
					prenotation.getTratto().getArrivo().getNome().equals("universita")){
						  prenotation.setObliteratoUscita(true);
						  pdao.update(prenotation);
						  return true;
					  }
			else {
				return false;
			}
			
		}
		
	}
}
