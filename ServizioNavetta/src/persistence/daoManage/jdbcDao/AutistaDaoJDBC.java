package persistence.daoManage.jdbcDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.CrudTag;
import persistence.persistentModel.Autista;
import persistence.utility.Utility;

public class AutistaDaoJDBC implements Crud{
	
	private DataSource ds;
	
	public AutistaDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		Autista autista = (Autista) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Autista\"(\"ID\",\"Nome\",\"Cognome\",\"Email\",\"Password\") values (?,?,?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, autista.getID());
			
			Array nome = Utility.convertStringArray(autista.getNome(), con);
			Array cognome = Utility.convertStringArray(autista.getCognome(), con);
			Array email = Utility.convertStringArray(autista.getEmail(), con);
			Array password = Utility.convertStringArray(autista.getPassword().password, con);
			
			smt.setArray(2, nome);
			smt.setArray(3, cognome);
			smt.setArray(4, email);
			smt.setArray(5, password);
			
			smt.executeUpdate();
			
		} catch (SQLException e) {
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
		int key = 0;
		try {
			key = Integer.parseInt(pKey);
		}catch(NumberFormatException e) {return null;}
		
		Connection con = ds.getConnection();
		
		String query = "select * from \"Autista\" where \"ID\" = ?";
		
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, key);
			
			ResultSet res = smt.executeQuery();
			if(res.next()) {
				String nome = Utility.deleteArrayElements(res.getString("Nome"));
				String cognome = Utility.deleteArrayElements(res.getString("Cognome"));
				String email = Utility.deleteArrayElements(res.getString("Email"));
				return new Autista(key, nome, cognome, email, null);
			}
			return null;
		} catch (SQLException e) {
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
		ArrayList<Autista> autisti = new ArrayList<Autista>();
		Connection con = ds.getConnection();
		String query = "select * from \"Autista\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet res = smt.executeQuery();
			while(res.next()) {
				int ID = res.getInt("ID");
				String nome = Utility.deleteArrayElements(res.getString("Nome"));
				String cognome = Utility.deleteArrayElements(res.getString("Cognome"));
				String email = Utility.deleteArrayElements(res.getString("Email"));
				autisti.add(new Autista(ID, nome, cognome, email, null));
			}
			return autisti;
		} catch (SQLException e) {
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
		Autista autista = (Autista) obj;
		Connection con = ds.getConnection();
		String query = "update \"Autista\""
						+ "set (\"Nome\",\"Cognome\",\"Email\",\"Password\") = (?,?,?,?)"
						+ "where \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			
			smt.setInt(5, autista.getID());
			
			Array nome = Utility.convertStringArray(autista.getNome(), con);
			Array cognome = Utility.convertStringArray(autista.getCognome(), con);
			Array email = Utility.convertStringArray(autista.getEmail(), con);
			Array password = Utility.convertStringArray(autista.getPassword().password, con);
			
			smt.setArray(1, nome);
			smt.setArray(2, cognome);
			smt.setArray(3, email);
			smt.setArray(4, password);
			
			smt.executeUpdate();
			
		} catch (SQLException e) {
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
		//Non previsto -> dependency Prenotazione
	}

}
