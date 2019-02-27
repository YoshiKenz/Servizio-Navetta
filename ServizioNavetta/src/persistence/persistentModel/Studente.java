package persistence.persistentModel;

import java.util.ArrayList;

public class Studente extends Persona {
	private int matricola;
	private int flag;
	private ArrayList<Prenotazione> prenotazioni;
	
	
	public Studente(int _matricola,int flag,String nome,String cognome,String email,Password password) {
		super(nome, cognome, email, password);
		this.matricola = _matricola;
		this.flag = flag;
		prenotazioni = new ArrayList<Prenotazione>();
	}

	public int getMatricola() {
		return matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		flag = Math.abs(flag);
		this.flag = flag;
	}

	public ArrayList<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public void setPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
		this.prenotazioni = prenotazioni;
	}
	
	/*
	public static void main(String[] args) {
		Password pass = new Password();
		pass.password = "pass";
		try {
			Studente s1 = new Studente(1, 1, "Alessio", "Portaro", "dovrebbeEsseregiusta@penso.si", pass );
		}
		catch(RuntimeException e) {
			String error = e.getMessage();
			System.out.println(error);
		}
		
		System.out.println("Tutto ok");
	}
	*/
}
