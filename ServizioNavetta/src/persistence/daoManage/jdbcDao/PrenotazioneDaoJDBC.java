package persistence.daoManage.jdbcDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.CrudTag;
import persistence.persistentModel.Autista;
import persistence.persistentModel.Navetta;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;
import persistence.persistentModel.TrattoLinea;
import persistence.utility.Utility;

public class PrenotazioneDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public PrenotazioneDaoJDBC(DataSource ds) {
		this.ds =ds;
	}

	@Override
	public void save(CrudTag obj) {
		Prenotazione prenotazione = (Prenotazione) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Prenotazione\" "
				+ "(\"ID\",\"Giro\",\"Navetta_ID\",\"Obliterato_entrata\","
				+ "\"Obliterato_uscita\",\"Tratto::partenza\",\"Tratto::arrivo\","
				+ "\"Date_time\",\"Autista_ID\",\"Studente_ID\")"
				+ "values (?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			
			Array partenza = Utility.convertStringArray(prenotazione.getTratto().getPartenza().getNome(), con);
			Array arrivo = Utility.convertStringArray(prenotazione.getTratto().getArrivo().getNome(), con);
			
			smt.setInt(1, prenotazione.getID());
			smt.setInt(2, prenotazione.getGiro());
			smt.setInt(3, prenotazione.getNavetta().getID());
			smt.setBoolean(4, prenotazione.isObliteratoEntrata());
			smt.setBoolean(5, prenotazione.isObliteratoUscita());
			smt.setArray(6, partenza);
			smt.setArray(7, arrivo);
			smt.setObject(8, Utility.convertToTimestamp(prenotazione.getDateTime()));
			smt.setInt(9, prenotazione.getAutista().getID());
			smt.setInt(10, prenotazione.getStudente().getMatricola());
			
			smt.executeUpdate();
			
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public CrudTag findByPrimaryKey(String pKey) {
		int prenotazioneID=0;
		try {
			prenotazioneID = Integer.parseInt(pKey);
		}catch(NumberFormatException e) {return null;}
		Connection con = ds.getConnection();
		String query = "select * from \"Prenotazione\" where \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, prenotazioneID);
			ResultSet result = smt.executeQuery();
			if(result.next()) {
				int giro = result.getInt("Giro");
				Navetta navetta = (Navetta) new NavettaDaoJDBC(ds).findByPrimaryKey(result.getInt("Navetta_ID")+"");
				String fKey1_partenza = Utility.deleteArrayElements(result.getString("Tratto::partenza"));
				String fKey2_arrivo = Utility.deleteArrayElements(result.getString("Tratto::arrivo"));
				TrattoLinea tratto = new TrattoLineaDaoJDBC(ds).findByPrimaryKeyComposed(fKey1_partenza, fKey2_arrivo);
				Calendar dateTime = Utility.convertToCalendar((java.sql.Timestamp)result.getObject("Date_time"));
				Studente studente = new StudenteDaoJDBC(ds).findByPrimaryKey(result.getInt("Studente_ID")+"");
				
				Autista autista = (Autista) new AutistaDaoJDBC(ds).findByPrimaryKey(result.getInt("Autista_ID")+"");
				Prenotazione pr = new Prenotazione(prenotazioneID, giro, navetta, tratto, dateTime, autista, studente);
				pr.setObliteratoEntrata(result.getBoolean("Obliterato_entrata"));
				pr.setObliteratoUscita(result.getBoolean("Obliterato_uscita"));
				return pr;
			}
			return null;
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public List<? extends CrudTag> findAll() {

		ArrayList<Prenotazione> ret = new ArrayList<Prenotazione>();
		Connection con = ds.getConnection();
		String query =" select * from \"Prenotazione\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet result = smt.executeQuery();
			while(result.next()) {
				int ID = result.getInt("ID");
				int giro = result.getInt("Giro");
				Navetta navetta = (Navetta) new NavettaDaoJDBC(ds).findByPrimaryKey(result.getInt("Navetta_ID")+"");
				String fKey1_partenza = Utility.deleteArrayElements(result.getString("Tratto::partenza"));
				String fKey2_arrivo = Utility.deleteArrayElements(result.getString("Tratto::arrivo"));
				TrattoLinea tratto = new TrattoLineaDaoJDBC(ds).findByPrimaryKeyComposed(fKey1_partenza, fKey2_arrivo);
				Calendar dateTime = Utility.convertToCalendar((java.sql.Timestamp)result.getObject("Date_time"));
				Studente studente = new StudenteDaoJDBC(ds).findByPrimaryKey(result.getInt("Studente_ID")+"");
				
				Autista autista = (Autista) new AutistaDaoJDBC(ds).findByPrimaryKey(result.getInt("Autista_ID")+"");
				Prenotazione pr = new Prenotazione(ID, giro, navetta, tratto, dateTime, autista, studente);
				pr.setObliteratoEntrata(result.getBoolean("Obliterato_entrata"));
				pr.setObliteratoUscita(result.getBoolean("Obliterato_uscita"));
				ret.add(pr);
			}
			return ret;
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public void update(CrudTag obj) {
		Prenotazione prenotazione = (Prenotazione) obj;
		Connection con = ds.getConnection();
		String query = "update \"Prenotazione\" set"
				+ "(\"Giro\",\"Navetta_ID\",\"Obliterato_entrata\","
				+ "\"Obliterato_uscita\",\"Tratto::partenza\",\"Tratto::arrivo\","
				+ "\"Date_time\",\"Autista_ID\",\"Studente_ID\") = (?,?,?,?,?,?,?,?,?)"
				+ "where \"ID\"=?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			

			Array partenza = Utility.convertStringArray(prenotazione.getTratto().getPartenza().getNome(), con);
			Array arrivo = Utility.convertStringArray(prenotazione.getTratto().getArrivo().getNome(), con);
			
			smt.setInt(10, prenotazione.getID());
			smt.setInt(1, prenotazione.getGiro());
			smt.setInt(2, prenotazione.getNavetta().getID());
			smt.setBoolean(3, prenotazione.isObliteratoEntrata());
			smt.setBoolean(4, prenotazione.isObliteratoUscita());
			smt.setArray(5, partenza);
			smt.setArray(6, arrivo);
			smt.setObject(7, Utility.convertToTimestamp(prenotazione.getDateTime()));
			smt.setInt(8, prenotazione.getAutista().getID());
			smt.setInt(9, prenotazione.getStudente().getMatricola());
			
			smt.executeUpdate();
			
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}

	}

	@Override
	public void delete(CrudTag obj) {
		// Non previsto

	}
	
	public ArrayList<Prenotazione> findByReference(Studente studente) {
		int pkey = studente.getMatricola();
		
		Connection connection = ds.getConnection();
		
		String query = "SELECT * from \"Prenotazione\" where \"Studente_ID\" = ?";
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, pkey);
			ResultSet result = statement.executeQuery();
			ArrayList<Prenotazione> returning = new ArrayList<Prenotazione>();
			while(result.next()) {
				int ID = result.getInt("ID");
				int giro = result.getInt("Giro");
				Calendar dateTime = Utility.convertToCalendar((java.sql.Timestamp)result.getObject("Date_time"));
				Navetta navetta = (Navetta) new NavettaDaoJDBC(ds).findByPrimaryKey(result.getInt("Navetta_ID")+"");
				String fKey1_partenza = Utility.deleteArrayElements(result.getString("Tratto::partenza"));
				String fKey2_arrivo = Utility.deleteArrayElements(result.getString("Tratto::arrivo"));
				TrattoLinea tratto = new TrattoLineaDaoJDBC(ds).findByPrimaryKeyComposed(fKey1_partenza, fKey2_arrivo);
				Autista autista = (Autista) new AutistaDaoJDBC(ds).findByPrimaryKey(result.getInt("Autista_ID")+"");
				Prenotazione pr = new Prenotazione(ID, giro, navetta, tratto, dateTime, autista,studente);
				pr.setObliteratoEntrata(result.getBoolean("Obliterato_entrata"));
				pr.setObliteratoUscita(result.getBoolean("Obliterato_uscita"));
				returning.add(pr);
			}
			return returning;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		
	}

}
