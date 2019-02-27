package persistence.daoManage.jdbcDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.persistentModel.Linea;
import persistence.persistentModel.TrattoLinea;
import persistence.utility.Utility;

public class TrattoToLineaAdder {
	
	private static TrattoToLineaAdder instance;
	
	private TrattoToLineaAdder() {}
	
	public static TrattoToLineaAdder getInstance() {
		if(instance==null)
			instance = new TrattoToLineaAdder();
		return instance;
	}

	public void add(TrattoLinea t,Linea l,DataSource ds) {
		String query = "insert into \"Linea_X_Tratto\"(\"fermata_partenza\",\"fermata_arrivo\",\"linea_id\") values(?,?,?)";
		Connection con = ds.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			Array partenza = Utility.convertStringArray(t.getPartenza().getNome(), con)
			,arrivo = Utility.convertStringArray(t.getArrivo().getNome(), con)
			,linea = Utility.convertStringArray(l.getNome(), con);
			
			stm.setArray(1, partenza);
			stm.setArray(2, arrivo);
			stm.setArray(3, linea);
			
			stm.executeUpdate();
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
