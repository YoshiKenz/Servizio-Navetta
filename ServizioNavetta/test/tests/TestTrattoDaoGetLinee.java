package tests;

import persistence.daoManage.DAOFactory;
//import persistence.daoManage.DataSource;
import persistence.daoManage.DatabaseManager;
//import persistence.daoManage.PostgresDAOFactory;
import persistence.daoManage.jdbcDao.TrattoLineaDaoJDBC;
//import persistence.daoManage.jdbcDao.TrattoToLineaAdder;
import persistence.persistentModel.Linea;
import persistence.persistentModel.TrattoLinea;

public class TestTrattoDaoGetLinee {

	public static void main(String[] args) {
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
//		DataSource ds = PostgresDAOFactory.getDS();
		TrattoLinea t = (TrattoLinea) df.getTrattoLineaDAO().findAll().get(0);
//		TrattoToLineaAdder.getInstance().add(t, new Linea("linea1"), ds);
		System.out.println(t.getPartenza().getNome()+" "+t.getArrivo().getNome());
		Linea l = ((TrattoLineaDaoJDBC)df.getTrattoLineaDAO()).getLinee(t).get(0);
		System.out.println(l.getNome());
	}
}
