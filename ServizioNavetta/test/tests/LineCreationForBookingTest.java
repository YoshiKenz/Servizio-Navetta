package tests;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DataSource;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.PostgresDAOFactory;
import persistence.daoManage.jdbcDao.TrattoToLineaAdder;
import persistence.persistentModel.Fermata;
import persistence.persistentModel.Linea;
import persistence.persistentModel.TrattoLinea;

public class LineCreationForBookingTest {
	
	public static void main(String[] args) {
		DAOFactory df = DatabaseManager.getInstance().getDaoFactory();
		DataSource ds = PostgresDAOFactory.getDS();
		Fermata f1 = (Fermata) df.getFermataDAO().findByPrimaryKey("Borromeo");
		Fermata f2 = (Fermata) df.getFermataDAO().findByPrimaryKey("Castiglione_Cs._Stazione_FS");
		Linea l = new Linea("lineaF");
		TrattoLinea t1 = new TrattoLinea(f1, f2, 5, 5);
		TrattoLinea t2 = new TrattoLinea(f2, f1, 5, 5);
		
		df.getTrattoLineaDAO().save(t1);
		df.getTrattoLineaDAO().save(t2);
		
		TrattoToLineaAdder.getInstance().add(t1, l, ds);
		TrattoToLineaAdder.getInstance().add(t2, l, ds);
	}
}
