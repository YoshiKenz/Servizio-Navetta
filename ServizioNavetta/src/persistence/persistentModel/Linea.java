package persistence.persistentModel;

import java.util.ArrayList;

public class Linea {
	
	private String nome;
	private ArrayList<TrattoLinea> tratti;
	
	public Linea(String nome) {
		this.nome= nome;
		tratti = new ArrayList<TrattoLinea>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<TrattoLinea> getTratti() {
		return tratti;
	}

	public void setTratti(ArrayList<TrattoLinea> tratti) {
		this.tratti = tratti;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==this)
			return true;
		else {
			if(!(obj instanceof Linea))
				return false;
			else {
				Linea clone = (Linea) obj;
				return this.getNome().equals(clone.getNome());
			}
		}
	}
	
}
