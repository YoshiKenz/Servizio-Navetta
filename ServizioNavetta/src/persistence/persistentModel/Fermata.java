package persistence.persistentModel;

import persistence.daoManage.crud.CrudTag;

public class Fermata implements CrudTag, Comparable<Fermata>{

	private String nome;
	private Double latitudine;
	private Double longitudine;
	private static final double LAT_MAX_VALUE = 90;
	private static final double LNG_MAX_VALUE = 90;
	
	public Fermata(String nome,double lat,double lng) {
		this.nome=nome;
		setLatitudine(lat);
		setLongitudine(lng);
		this.longitudine = lng;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(double latitudine) {
		
		this.latitudine = latitudine%LAT_MAX_VALUE;
	}

	public Double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine%LNG_MAX_VALUE;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)
			return true;
		else {
			if(!(obj instanceof Fermata))
				return false;
			Fermata arg = (Fermata) obj;
			return arg.nome.equals(this.nome);
		}
	}

	@Override
	public int compareTo(Fermata o) {
		return this.getNome().compareTo(o.getNome());
	}
	
}
