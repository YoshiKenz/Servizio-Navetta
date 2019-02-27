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
import persistence.persistentModel.Linea;
import persistence.persistentModel.TrattoLinea;
import persistence.utility.Utility;

public class TrattoLineaDaoJDBC implements Crud {
	
	private DataSource ds;
	
	public TrattoLineaDaoJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(CrudTag obj) {
		TrattoLinea tratto = (TrattoLinea) obj;
		Connection con = ds.getConnection();
		String query = "insert into \"Tratto_di_linea\" "
				+ "(\"Fermata_Arrivo\",\"Fermata_Partenza\",\"Tempo_medio_percorrenza_MIN\",\"Distanza_KM\")"
				+ "values (?,?,?,?)";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array nomeArrivo = Utility.convertStringArray(tratto.getArrivo().getNome(), con);
			Array nomePartenza = Utility.convertStringArray(tratto.getPartenza().getNome(), con);
			smt.setArray(1, nomeArrivo);
			smt.setArray(2, nomePartenza);
			smt.setDouble(3, tratto.getTempoMIN().doubleValue());
			smt.setDouble(4, tratto.getDistanzaKM().doubleValue());
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

	public CrudTag findByPrimaryKey(String pKey) {return null;}
	
	public TrattoLinea findByPrimaryKeyComposed(String fKey1_partenza,String fkey2_arrivo) {
		Connection con = ds.getConnection();
		String query = "select * from \"Tratto_di_linea\" "
						+ "where \"Fermata_Partenza\" =  ? AND \"Fermata_Arrivo\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array nomePartenza = Utility.convertStringArray(fKey1_partenza, con);
			Array nomeArrivo = Utility.convertStringArray(fkey2_arrivo, con);
			smt.setArray(1, nomePartenza);
			smt.setArray(2, nomeArrivo);
			ResultSet resultset = smt.executeQuery();
			if(resultset.next()) {
				Fermata partenza = (Fermata)
						(new FermataDaoJDBC(ds)).findByPrimaryKey(
								Utility.deleteArrayElements(resultset.getString("Fermata_Partenza")));
				Fermata arrivo = (Fermata)
						(new FermataDaoJDBC(ds)).findByPrimaryKey(
								Utility.deleteArrayElements(resultset.getString("Fermata_Arrivo")));
				double tempo = resultset.getDouble("Tempo_medio_percorrenza_MIN");
				double distanza = resultset.getDouble("Distanza_KM");
				return new TrattoLinea(partenza, arrivo, tempo, distanza);
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
	public List<TrattoLinea> findAll() {
		ArrayList<TrattoLinea> ret = new ArrayList<TrattoLinea>();
		Connection con = ds.getConnection();
		String query = "select * from \"Tratto_di_linea\"";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			ResultSet resultset = smt.executeQuery();
			while(resultset.next()) {
				Fermata partenza = (Fermata)
						(new FermataDaoJDBC(ds)).findByPrimaryKey(
								Utility.deleteArrayElements(resultset.getString("Fermata_Partenza")));
				Fermata arrivo = (Fermata)
						(new FermataDaoJDBC(ds)).findByPrimaryKey(
								Utility.deleteArrayElements(resultset.getString("Fermata_Arrivo")));
				double tempo = resultset.getDouble("Tempo_medio_percorrenza_MIN");
				double distanza = resultset.getDouble("Distanza_KM");
				ret.add(new TrattoLinea(partenza, arrivo, tempo, distanza));
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
		TrattoLinea tratto = (TrattoLinea) obj;
		Connection con = ds.getConnection();
		String query = "update \"Tratto_di_linea\""
				+ "set (\"Tempo_medio_percorrenza_MIN\",\"Distanza_KM\") = (?,?)"
				+ "where \"Fermata_Partenza\" = ? AND \"Fermata_Arrivo\" = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array nomePartenza = Utility.convertStringArray(tratto.getPartenza().getNome(), con);
			Array nomeArrivo = Utility.convertStringArray(tratto.getArrivo().getNome(), con);
			
			smt.setDouble(1, tratto.getTempoMIN().doubleValue());
			smt.setDouble(2, tratto.getDistanzaKM().doubleValue());
			smt.setArray(3, nomePartenza);
			smt.setArray(4, nomeArrivo);
			
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
		TrattoLinea tratto = (TrattoLinea) obj;
		Connection con = ds.getConnection();
		String queryDependency = "delete from \"Linea_X_Tratto\""
								+ "where \"fermata_partenza\" = ? AND \"fermata_arrivo\" = ?";
		try {
			PreparedStatement smt1 = con.prepareStatement(queryDependency);
			Array partenza = Utility.convertStringArray(tratto.getPartenza().getNome(), con);
			Array arrivo = Utility.convertStringArray(tratto.getArrivo().getNome(), con);
			smt1.setArray(1, partenza);
			smt1.setArray(2, arrivo);
			
			smt1.executeUpdate();
			
			String query = "delete from \"Tratto_di_linea\""
							+ "where \"Fermata_Arrivo\" = ? AND \"Fermata_Partenza\" = ?";
			PreparedStatement smt2 = con.prepareStatement(query);
			smt2.setArray(1, arrivo);
			smt2.setArray(2, partenza);
			
			smt2.executeUpdate();
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
	
	public ArrayList<Linea> getLinee(TrattoLinea t){
		String query = "select * from \"Linea_X_Tratto\" where \"fermata_partenza\" = ? AND \"fermata_arrivo\" = ?";
		Connection con = ds.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			Array partenza = Utility.convertStringArray(t.getPartenza().getNome(), con);
			Array arrivo = Utility.convertStringArray(t.getArrivo().getNome(), con);
			stm.setArray(1, partenza);
			stm.setArray(2, arrivo);
			ResultSet res = stm.executeQuery();
			ArrayList<Linea> linee = new ArrayList<Linea>();
			while(res.next()) {
				String nome = Utility.deleteArrayElements(res.getString(3));
				Linea linea = new Linea(nome);
				linee.add(linea);
			}
			return linee;
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

}
