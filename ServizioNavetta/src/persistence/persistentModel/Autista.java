package persistence.persistentModel;

import persistence.daoManage.crud.CrudTag;

public class Autista extends Persona implements CrudTag{
	
	private int ID;
	
	public Autista(int ID,String nome,String cognome,String email,Password password) {
		super(nome, cognome, email, password);
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
