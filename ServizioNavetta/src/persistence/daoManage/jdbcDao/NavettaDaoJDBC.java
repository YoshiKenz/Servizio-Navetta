package persistence.daoManage.jdbcDao;

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
import persistence.persistentModel.Navetta;

public class NavettaDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public NavettaDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		Navetta navetta = (Navetta) obj;
		Connection con = ds.getConnection();
		String query = "INSERT INTO \"Navetta\"(\"ID\",\"Descrizione\",\"Posti_totali\") values(?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, navetta.getID());
			smt.setString(2, navetta.getDescrizione());
			smt.setInt(3, navetta.getPostiTotali());
			int result = smt.executeUpdate();
			if(result==0) {System.out.println("No insert in Navetta (i guess)");}
			
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
		int id=0;
		try {
			id=Integer.parseInt(pKey);
		}catch(NumberFormatException e) {return null;}
		
		Connection con = ds.getConnection();
		String query = "SELECT * FROM \"Navetta\" WHERE \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, id);
			ResultSet res = smt.executeQuery();
			if(res.next()) {
				String descrizione = res.getString("Descrizione");
				int posti = res.getInt("Posti_totali");
				return new Navetta(id, descrizione, posti);
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
		ArrayList<Navetta> res = new ArrayList<Navetta>();
		Connection con = ds.getConnection();
		String query = "select * from \"Navetta\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet resSet = smt.executeQuery();
			while(resSet.next()) {
				int ID = resSet.getInt("ID");
				int posti = resSet.getInt("Posti_totali");
				String descrizione = resSet.getString("Descrizione");
				res.add(new Navetta(ID, descrizione, posti));
			}
			
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		
		return res;
	}

	@Override
	public void update(CrudTag obj) {
		Navetta navetta = (Navetta) obj;
		
		Connection con = ds.getConnection();
		String query = "UPDATE \"Navetta\""
				+ "SET (\"Descrizione\",\"Posti_totali\") = (?,?)"
				+ "WHERE \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setString(1, navetta.getDescrizione());
			smt.setInt(2, navetta.getPostiTotali());
			smt.setInt(3,navetta.getID());
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
		//Non previsto

	}

}
