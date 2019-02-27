package persistence.daoManage.jdbcDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.crud.SecurityDAO;
import persistence.daoManage.crud.SensitiveDataTag;
import persistence.persistentModel.Password;
import persistence.utility.Utility;

public class PersonaSecureDAO implements SecurityDAO {

	private DataSource ds;

	private boolean authorized = false;
	private String authorizer;

	public PersonaSecureDAO(DataSource ds) {
		this.ds = ds;
		authorized = false;
	}

	@Override
	public boolean authorizeDao(String Admin, String pass) {
		Connection connection = ds.getConnection();
		String query ="SELECT * FROM \"Amministratore\" WHERE \"ID\" = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(Admin));
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				String password = Utility.deleteArrayElements(result.getString("Password"));
				if(pass.equals(password)) {
					authorized = true;
					authorizer = new String(Admin);
					return true;
				}
				return false;
			}else {
				return false;
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

	}

	@Override
	public boolean deauthorizeDao(String Admin, String pass) {
		if(!authorized)
			return false;
		Connection connection = ds.getConnection();
		String query ="SELECT * FROM \"Amministratore\" WHERE \"ID\" = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, Integer.parseInt(Admin));
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				if(/*authorized && */Admin.equals(authorizer)) {//short-circuit to avoid nullpointer
					String password = Utility.deleteArrayElements(result.getString("Password"));
					if(pass.equals(password)) {
						authorized = false;
						authorizer = null;
						return true;
					}
					else
						return false;
				}
				else
					return false;
			}else {
				return false;
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
	}

	@Override
	public boolean isAuthorized() {
		return authorized;
	}

	@Override
	public SensitiveDataTag retriveSensitiveData(String pKey) {
		if(!authorized)
			return null;
		else {
			String query = "SELECT * FROM \"Persona\" WHERE \"ID\" = ?";
			Connection connection = ds.getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(query);
//				System.out.println(pKey);
				statement.setInt(1,Integer.parseInt(pKey));
				ResultSet result = statement.executeQuery();
				if(result.next()) {
					String password = Utility.deleteArrayElements(result.getString("Password"));
					Password personaPass = new Password();
					personaPass.password = password;
					return personaPass;
				}
				else {
					return null;
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
		}
	}

	@Override
	public boolean setSensitiveData(String pKey, SensitiveDataTag data) {
		if(!authorized)
			return false;
		else {
			Password personaPass = (Password) data;
			String query = "UPDATE \"Persona\" "
						 + "SET \"Password\" = ? "
						 + "WHERE \"ID\" = ?";
			Connection connection = ds.getConnection();
			try {
				PreparedStatement statement = connection.prepareStatement(query);
				statement.setInt(2,Integer.parseInt(pKey));
				statement.setString(1,personaPass.password);
				int result = statement.executeUpdate();
				if(result==0)//PKEY DOESN'T MATCH 
					return false;
				else 
					return true;
				
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
	}

}
