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
import persistence.persistentModel.FeedBack;
import persistence.persistentModel.Prenotazione;

public class FeedbackDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public FeedbackDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		FeedBack feedback = (FeedBack) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Feedback\" (\"ID\",\"Contenuto\") values (?,?)" ;
		
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, feedback.getPrenotazione().getID());
			smt.setString(2, feedback.getContenuto());
			
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
		int prenotazione = 0;
		try {
			prenotazione = Integer.parseInt(pKey);
		}catch(NumberFormatException e) {return null;}
		
		Connection con = ds.getConnection();
		
		String query = "select * from \"Feedback\" where \"ID\" = ?";
		
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, prenotazione);
			
			ResultSet res = smt.executeQuery();
			if(res.next()) {
				Prenotazione prenot = (Prenotazione) new PrenotazioneDaoJDBC(ds).findByPrimaryKey(prenotazione+"");
				FeedBack ret = new FeedBack(prenot);
				ret.setContenuto(res.getString("Contenuto"));
				return ret;
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
		ArrayList<FeedBack> ret = new ArrayList<FeedBack>();
		Connection con = ds.getConnection();
		String query = "select * from \"Prenotazione\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet res = smt.executeQuery();
			while(res.next()) {
				
				Prenotazione prenotazione = (Prenotazione) new PrenotazioneDaoJDBC(ds).findByPrimaryKey(res.getInt("Prenotazione_ID")+"");
				FeedBack e = new FeedBack(prenotazione);
				e.setContenuto(res.getString("Contenuto"));
				ret.add(e);
			}
			return ret;
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
		FeedBack feedback = (FeedBack) obj;
		Connection con = ds.getConnection();
		
		String query = "update \"Feedback\""
						+ "set (\"Contenuto\")"
						+ "where \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, feedback.getPrenotazione().getID());
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
		FeedBack feedback = (FeedBack) obj;
		Connection con  = ds.getConnection();
		String query = "delete from \"Feedback\""
						+ "where \"ID\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			smt.setInt(1, feedback.getPrenotazione().getID());
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
