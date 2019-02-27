package persistence.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.daoManage.DataSource;
import persistence.daoManage.PersistenceException;
import persistence.daoManage.PostgresDAOFactory;

public class IdProvider {
	
	private static IdProvider instance=null;
	
	private IdProvider() {}
	
	public static IdProvider getInstance() {
		if(instance==null)
			instance = new IdProvider();
		return instance;
	}
	
	public int getNextId(String table) {
		/**
		 * Parameter "table" must be the exact name of a table in the DB.
		 * The table "table" must have an integer as primary id
		 * Otherwise the method returns -1;
		 */
		table = table.toLowerCase();
		
		DataSource ds = PostgresDAOFactory.getDS();
		Connection con = ds.getConnection();
		
		String tableName;
		
		String refresh = "REFRESH MATERIALIZED VIEW \"TablesIdProvider\"";
		
		String query = "select \"nextid\"" + 
					"from \"TablesIdProvider\"" + 
					"where	\"TableName\" = ?";
		
		switch(table) {
		case "persona":{
			tableName="Persona";
			
			break;
		}
		case "prenotazione":{
			tableName="Prenotazione";
			break;
		}
		case "domanda_riabilitazione":
		case "domandariabilitazione":
		case "domanda riabilitazione":{
			tableName="Domanda_Riabilitazione";
			break;
		}
		case "feedback":{
			tableName="Feedback";
			break;
		}
		case "navetta":{
			tableName="Navetta";
			break;
		}
		default :{
			return -1;
		}
		}
		insertIfNotExistsIndexableTable(tableName, ds);
		try {
			PreparedStatement refreshView = con.prepareStatement(refresh);
			refreshView.execute();
			
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, tableName);
			ResultSet res = statement.executeQuery();
			if(res.next()) {
				int ret = res.getInt(1);
				return ret;
			}
			return -1;
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
	
	private boolean insertIfNotExistsIndexableTable(String table,DataSource ds) {
		Connection con = ds.getConnection();
		String isIn ="select * from \"IndexableTables\" where \"TableName\"= ?";
		try {
			PreparedStatement stm = con.prepareStatement(isIn);
			stm.setString(1, table);
			ResultSet res = stm.executeQuery();
			if(res.next()) {
				return false;
			}else {
				String insert = "insert into \"IndexableTables\" values(?)";
				PreparedStatement insert1 = con.prepareStatement(insert);
				insert1.setString(1, table);
				insert1.executeUpdate();
				return true;
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
	}

}
