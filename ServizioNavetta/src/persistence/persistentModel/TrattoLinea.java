package persistence.persistentModel;

import persistence.daoManage.crud.CrudTag;

public class TrattoLinea implements CrudTag{
	
	private Fermata partenza;
	private Fermata arrivo;
	private Double tempoMedioPercorrenza_MIN;
	private Double distanza_KM;
	
	public TrattoLinea(Fermata partenza,Fermata arrivo,double tempo,double distanza) {
		this.arrivo = arrivo;
		this.partenza = partenza;
		setTempoMIN(tempo);
		setDistanzaKM(distanza);
	}

	public Fermata getPartenza() {
		return partenza;
	}

	public void setPartenza(Fermata partenza) {
		this.partenza = partenza;
	}

	public Fermata getArrivo() {
		return arrivo;
	}

	public void setArrivo(Fermata arrivo) {
		this.arrivo = arrivo;
	}
	
	public Double getTempoMIN() {
		return tempoMedioPercorrenza_MIN;
	}
	
	public void setTempoMIN(double tempo) {
		this.tempoMedioPercorrenza_MIN = new Double(tempo);
	}
	
	public Double getDistanzaKM() {
		return distanza_KM;
	}
	
	public void setDistanzaKM(double distanza) {
		this.distanza_KM = new Double(distanza);
	}

}
