package persistence.persistentModel;

import java.util.Calendar;

import persistence.daoManage.crud.CrudTag;

public class Prenotazione implements CrudTag{

	private boolean obliteratoUscita=false,obliteratoEntrata=false;
	private int ID;
	private int giro;
	private Navetta navetta;
	private TrattoLinea tratto;
	private Calendar dateTime;
	private Autista autista;
	private Studente studente;
	
	public Prenotazione(int ID,int giro,Navetta navetta,TrattoLinea tratto,Calendar dateTime,Autista autista,Studente studente) {
		this.ID = ID;
		this.giro = Math.abs(giro);
		this.navetta = navetta;
		this.tratto = tratto;
		this.dateTime = dateTime;
		this.autista = autista;
		this.studente = studente;
	}

	public boolean isObliteratoUscita() {
		return obliteratoUscita;
	}

	public void setObliteratoUscita(boolean obliteratoUscita) {
		this.obliteratoUscita = obliteratoUscita;
	}

	public boolean isObliteratoEntrata() {
		return obliteratoEntrata;
	}

	public void setObliteratoEntrata(boolean obliteratoEntrata) {
		this.obliteratoEntrata = obliteratoEntrata;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getGiro() {
		return giro;
	}

	public void setGiro(int giro) {
		this.giro = giro;
	}

	public Navetta getNavetta() {
		return navetta;
	}

	public void setNavetta(Navetta navetta) {
		this.navetta = navetta;
	}

	public TrattoLinea getTratto() {
		return tratto;
	}

	public void setTratto(TrattoLinea tratto) {
		this.tratto = tratto;
	}

	public Calendar getDateTime() {
		return dateTime;
	}

	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}

	public Autista getAutista() {
		return autista;
	}

	public void setAutista(Autista autista) {
		this.autista = autista;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	
	
}
