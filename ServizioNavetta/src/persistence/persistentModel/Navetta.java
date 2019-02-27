package persistence.persistentModel;

import persistence.daoManage.crud.CrudTag;

public class Navetta implements CrudTag{

	private int ID;
	private String descrizione;
	private int postiTotali;
	
	public Navetta(int ID,String descrizione,int posti) {
		this.ID = ID;
		this.descrizione = descrizione;
		this.postiTotali = posti;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public int getPostiTotali() {
		return postiTotali;
	}

	public void setPostiTotali(int postiTotali) {
		this.postiTotali = postiTotali;
	}
	
	
}
