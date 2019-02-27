package persistence.persistentModel;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.jdbcDao.TrattoLineaDaoJDBC;
import persistence.utility.Utility;

public class LineaProxy extends Linea{
	
	private DataSource ds;
	
	public LineaProxy(String nome,DataSource ds) {
		super(nome);
		this.ds = ds;
	}
	
	@Override
	public ArrayList<TrattoLinea> getTratti() {
		ArrayList<TrattoLinea> tratti = new ArrayList<TrattoLinea>();
		Connection con = ds.getConnection();
		String query = "select * from Linea_X_Tratto where linea_id = ?";
		try {
			PreparedStatement smt = con.prepareStatement(query);
			Array linea = Utility.convertStringArray(this.getNome(), con);
			
			smt.setArray(1, linea);
			
			ResultSet res = smt.executeQuery();
			while(res.next()) {
				
				String fKey1_partenza = Utility.deleteArrayElements(res.getString("fermata_partenza"));
				String fkey2_arrivo = Utility.deleteArrayElements(res.getString("fermata_arrivo"));
				
				TrattoLinea tratto = new TrattoLineaDaoJDBC(ds).findByPrimaryKeyComposed(fKey1_partenza, fkey2_arrivo);
				tratti.add(tratto);
			}
			return tratti;
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
