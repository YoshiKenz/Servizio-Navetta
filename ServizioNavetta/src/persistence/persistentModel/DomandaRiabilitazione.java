package persistence.persistentModel;

import java.time.LocalDateTime;

import persistence.daoManage.crud.CrudTag;

public class DomandaRiabilitazione implements CrudTag{

	private int ID;
	private LocalDateTime dateTime;
	private Studente studente;
	private Amministratore amministratore;
	
	public DomandaRiabilitazione(int ID,LocalDateTime dateTime,Studente stud,Amministratore admin) {
		this.ID=ID;
		this.dateTime = dateTime;
		this.studente = stud;
		this.amministratore = admin;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Studente getStudente() {
		return studente;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

	public Amministratore getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}
	
	
}
