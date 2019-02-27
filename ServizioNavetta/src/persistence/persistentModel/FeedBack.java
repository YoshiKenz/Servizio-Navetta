package persistence.persistentModel;

import persistence.daoManage.crud.CrudTag;

public class FeedBack implements CrudTag{
	
	private Prenotazione prenotazione;
	private String contenuto;
	
	public FeedBack(Prenotazione prenotazione,String contenuto) {
		this.prenotazione = prenotazione;
		this.contenuto = contenuto;
	}
	
	public FeedBack(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
		this.contenuto = "";
	}

	public Prenotazione getPrenotazione() {
		return prenotazione;
	}

	public void setPrenotazione(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	

}
