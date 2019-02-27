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
import persistence.persistentModel.Amministratore;
import persistence.utility.Utility;

public class AmministratoreDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public AmministratoreDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		Amministratore admin = (Amministratore) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Amministratore\"(\"ID\",\"Nome\",\"Cognome\",\"Email\",\"Password\") values (?,?,?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, admin.getID());
			
			Array nome = Utility.convertStringArray(admin.getNome(), con);
			Array cognome = Utility.convertStringArray(admin.getCognome(), con);
			Array email = Utility.convertStringArray(admin.getEmail(), con);
			Array password = Utility.convertStringArray(admin.getPassword().password, con);
			
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
	public Amministratore findByPrimaryKey(String pKey) {
		int key = 0;
		try {
			key = Integer.parseInt(pKey);
		}catch(NumberFormatException e) {return null;}
		
		Connection con = ds.getConnection();
		
		String query = "select * from \"Amministratore\" where \"ID\" = ?";
		
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, key);
			
			ResultSet res = smt.executeQuery();
			if(res.next()) {
				String nome = Utility.deleteArrayElements(res.getString("Nome"));
				String cognome = Utility.deleteArrayElements(res.getString("Cognome"));
				String email = Utility.deleteArrayElements(res.getString("Email"));
				return new Amministratore(key, nome, cognome, email, null);
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
	public List<Amministratore> findAll() {
		ArrayList<Amministratore> admins = new ArrayList<Amministratore>();
		Connection con = ds.getConnection();
		String query = "select * from \"Amministratore\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet res = smt.executeQuery();
			while(res.next()) {
				int ID = res.getInt("ID");
				String nome = Utility.deleteArrayElements(res.getString("Nome"));
				String cognome = Utility.deleteArrayElements(res.getString("Cognome"));
				String email = Utility.deleteArrayElements(res.getString("Email"));
				admins.add(new Amministratore(ID, nome, cognome, email, null));
			}
			return admins;
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
		Amministratore autista = (Amministratore) obj;
		Connection con = ds.getConnection();
		String query = "update \"Ammnistratore\""
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
		Amministratore admin = (Amministratore) obj;
		
		//ELIMINO TUTTE LE RELATIVE DOMANDE POICHé LA TABELLA DOMANDA HA 
		//UN VINCOLO DI INTEGRITà REFERENZIALE RISPETTO ALLA TABELLA AMMINISTRATORE
		(new DomandaRiabilitazioneDaoJDBC(ds)).findByAdmin(admin.getID()+"");
		
		Connection con = ds.getConnection();
		String  query = "delete from \"Amministratore\" where \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, admin.getID());
			smt.executeQuery();
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

}
