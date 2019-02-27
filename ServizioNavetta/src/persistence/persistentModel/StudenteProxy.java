package persistence.persistentModel;

import java.util.ArrayList;

import persistence.daoManage.DataSource;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;

public class StudenteProxy extends Studente{
	
	private DataSource ds;
	
	public StudenteProxy(int _matricola,int flag,String nome,String cognome,String email,Password password,DataSource ds) {
		super(_matricola, flag, nome, cognome, email, password);
		this.ds = ds;
	}
	
	public StudenteProxy(Studente s,DataSource ds) {
		super(s.getMatricola(),s.getFlag(),s.getNome(),s.getCognome(),s.getEmail(),s.getPassword());
		this.ds =ds;
	}
	
	@Override
	public ArrayList<Prenotazione> getPrenotazioni() {
		
		this.setPrenotazioni(/*SET*/ (new PrenotazioneDaoJDBC(ds)).findByReference(this) /*SET*/);
		return super.getPrenotazioni();
	}

}
