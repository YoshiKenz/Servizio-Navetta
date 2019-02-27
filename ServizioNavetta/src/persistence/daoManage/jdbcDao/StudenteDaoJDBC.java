package persistence.daoManage.jdbcDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.CrudTag;
import persistence.persistentModel.Password;
import persistence.persistentModel.Studente;
import persistence.persistentModel.StudenteProxy;
import persistence.utility.Utility;

public class StudenteDaoJDBC implements Crud {
	
	protected DataSource ds;
	
	public StudenteDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		Studente studente = (Studente) obj;
		Connection connection = ds.getConnection();
		String query = "insert into "
				+ "\"Studente\"(\"Matricola\",\"Nome\",\"Cognome\",\"Email\",\"Password\",\"Flag_attuali\") "
				+ "values(?,?,?,?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			
			Array nome = Utility.convertStringArray(studente.getNome(),connection);
			Array cognome = Utility.convertStringArray(studente.getCognome(),connection);
			Array email = Utility.convertStringArray(studente.getEmail(),connection);
			Array password = Utility.convertStringArray(studente.getPassword().password,connection);
			
			
			statement.setInt(1, studente.getMatricola());
			statement.setArray(2, nome);
			statement.setArray(3, cognome);
			statement.setArray(4, email);
			statement.setArray(5, password);
			statement.setInt(6, studente.getFlag());
			statement.executeUpdate();
			
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

	@Override
	public Studente findByPrimaryKey(String pKey) {
		int key = Integer.parseInt(pKey);
		StudenteProxy retrived = null;
		/**/
		Connection connection = ds.getConnection();
		PreparedStatement statement;
		String query = "select * from \"Studente\" where \"Matricola\" = ?";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, key);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				int flag = result.getInt("Flag_attuali");
				String nome = Utility.deleteArrayElements(result.getString("Nome"));
				String cognome = Utility.deleteArrayElements(result.getString("Cognome"));
				String email = Utility.deleteArrayElements(result.getString("Email"));
				retrived = new StudenteProxy(key, flag, nome, cognome, email, null, ds);
			}
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		/**/
		return retrived;
	}

	@Override
	public ArrayList<Studente> findAll() {
		Connection connection = ds.getConnection();
		String query = "SELECT * FROM \"Studente\"";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			ArrayList<Studente> returning = new ArrayList<Studente>();
			while(result.next()) {
				int _matricola  = result.getInt("Matricola");
				int flag = result.getInt("Flag_attuali");
				String nome = Utility.deleteArrayElements(result.getString("Nome"));
				String cognome = Utility.deleteArrayElements(result.getString("Cognome"));
				String email = Utility.deleteArrayElements(result.getString("Email"));
				Password password =null;
				returning.add(new StudenteProxy(_matricola, flag, nome, cognome, email, password,ds));
			}
			return returning;
		}catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public void update(CrudTag obj) {
		Studente studente = (Studente) obj;
		Connection connection = ds.getConnection();
		String query = "UPDATE \"Studente\""
					 + "SET (\"Nome\", "
					 + "\"Cognome\", "
					 + "\"Email\", "
					 + "\"Password\", "
					 + "\"Flag_attuali\")=(?,?,?,?,?) "
					 + "WHERE \"Matricola\" = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			Array nome = Utility.convertStringArray(studente.getNome(), connection)
			,cognome= Utility.convertStringArray(studente.getCognome(), connection)
			,email= Utility.convertStringArray(studente.getEmail(), connection)
			,password= Utility.convertStringArray(studente.getPassword().password, connection);
			
			statement.setArray(1, nome);
			statement.setArray(2, cognome);
			statement.setArray(3, email);
			statement.setArray(4, password);
			statement.setInt(5, studente.getFlag());
			statement.setInt(6, studente.getMatricola());
			int result = statement.executeUpdate();
			if(result ==0)
				System.out.println("No match Studente pkey");
			
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

	@Override
	public void delete(CrudTag obj) {
		/*	 Generalmente non vorrei eliminare uno studente dalla base di dati. Se dovesse essere necessario,
		 *   si dovrebbero eliminare tutte le prenotazioni che fanno riferimento allo studente e tutte
		 *   le domande di riabilitazione ma questo non è assolutamente previsto*/
	}

}
