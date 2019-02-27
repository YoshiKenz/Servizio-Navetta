package model;

import persistence.persistentModel.Autista;
import persistence.persistentModel.Linea;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.TrattoLinea;

public class LineaRegistroNavette {
	
	private Navetta navetta;
	private int giriCompletati;
	private Autista autista;
	private TrattoLinea posizione;
	private Linea linea;
	
	public LineaRegistroNavette() {}
	
	public int getGiriCompletati() {
		return giriCompletati;
	}
	public void setGiriCompletati(int giriCompletati) {
		this.giriCompletati = giriCompletati;
	}
	public Autista getAutista() {
		return autista;
	}
	public void setAutista(Autista autista) {
		this.autista = autista;
	}
	public TrattoLinea getPosizione() {
		return posizione;
	}
	public void setPosizione(TrattoLinea posizione) {
		this.posizione = posizione;
	}

	public Navetta getNavetta() {
		return navetta;
	}

	public void setNavetta(Navetta navetta) {
		this.navetta = navetta;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	
	public boolean handleCompletedRound() {
		//TODO handle the event of a round completed from the bus
		return true;
	}
	
	@Override
	public String toString() {
		return "<"/*Navetta: "+navetta.getID()*/+", Autista: "+autista.getID()+
				", Tratto: "+posizione.getPartenza().getNome()+" -> "+posizione.getArrivo().getNome()+
				", Linea: "+linea.getNome()+", Giri Completati: "+giriCompletati+">";
	}

}
