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
import persistence.persistentModel.Fermata;
import persistence.utility.Utility;

public class FermataDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public FermataDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		Fermata fermata = (Fermata) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Fermata\" (\"Nome\",\"Latitudine\",\"Longitudine\") values (?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array nome = Utility.convertStringArray(fermata.getNome(), con);
			smt.setArray(1, nome);
			smt.setDouble(2, fermata.getLatitudine());
			smt.setDouble(3, fermata.getLongitudine());
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
		Connection con = ds.getConnection();
		String query = "select * from \"Fermata\" where \"Nome\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array nome = Utility.convertStringArray(pKey, con);
			smt.setArray(1, nome);
			ResultSet result = smt.executeQuery();
			if(result.next()) {
				String nome1 = pKey;
				double lat = result.getDouble("Latitudine");
				double lng = result.getDouble("Longitudine");
				return new Fermata(nome1, lat, lng);
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
	public List<Fermata> findAll() {
		ArrayList<Fermata> result = new ArrayList<Fermata>();
		Connection con = ds.getConnection();
		String query = "select * from \"Fermata\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet resultset = smt.executeQuery();
			while(resultset.next()) {
				String nome = Utility.deleteArrayElements(resultset.getString("Nome"));
				double lat = resultset.getDouble("Latitudine");
				double lng = resultset.getDouble("Longitudine");
				result.add(new Fermata(nome, lat, lng));
			}
			return result;
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
		Fermata fermata = (Fermata) obj;
		Connection con = ds.getConnection();
		String query = "update \"Fermata\""
				+ "set (\"Latitudine\",\"Longitudine\") = (?,?)"
				+ "where \"Nome\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setDouble(1, fermata.getLatitudine());
			smt.setDouble(2, fermata.getLongitudine());
			Array nome = Utility.convertStringArray(fermata.getNome(), con);
			smt.setArray(3, nome);
			
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
		// Non previsto

	}

}
