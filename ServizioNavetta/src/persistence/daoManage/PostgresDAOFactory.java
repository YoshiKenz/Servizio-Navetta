package persistence.daoManage;

import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.SecurityDAO;
import persistence.daoManage.jdbcDao.AmministratoreDaoJDBC;
import persistence.daoManage.jdbcDao.AutistaDaoJDBC;
import persistence.daoManage.jdbcDao.DomandaRiabilitazioneDaoJDBC;
import persistence.daoManage.jdbcDao.FeedbackDaoJDBC;
import persistence.daoManage.jdbcDao.FermataDaoJDBC;
import persistence.daoManage.jdbcDao.NavettaDaoJDBC;
import persistence.daoManage.jdbcDao.PersonaSecureDAO;
import persistence.daoManage.jdbcDao.PrenotazioneDaoJDBC;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.daoManage.jdbcDao.TrattoLineaDaoJDBC;

public class PostgresDAOFactory extends DAOFactory {

	private static DataSource dataSource;

	// --------------------------------------------

	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource = new DataSource("jdbc:postgresql://localhost:5432/ServizioNavetta", "postgres", "postgres");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------------

	@Override
	public Crud getStudenteDAO() {
		return new StudenteDaoJDBC(dataSource);
	}

	@Override
	public Crud getPrenotazioneDAO() {
		return new PrenotazioneDaoJDBC(dataSource);
	}

	@Override
	public Crud getAmministratoreDAO() {
		return new AmministratoreDaoJDBC(dataSource);
	}

	@Override
	public Crud getAutistaDAO() {
		return new AutistaDaoJDBC(dataSource);
	}

	@Override
	public Crud getDomandaRiabilitazioneDAO() {
		return new DomandaRiabilitazioneDaoJDBC(dataSource);
	}

	@Override
	public Crud getFeedBackDAO() {
		return new FeedbackDaoJDBC(dataSource);
	}

	@Override
	public Crud getFermataDAO() {
		return new FermataDaoJDBC(dataSource);
	}

	@Override
	public Crud getNavettaDAO() {
		return new NavettaDaoJDBC(dataSource);
	}

	@Override
	public Crud getTrattoLineaDAO() {
		return new TrattoLineaDaoJDBC(dataSource);
	}

	@Override
	public SecurityDAO getPersonaSecureDAO() {
		return new PersonaSecureDAO(dataSource);
	}

	public static DataSource getDS() {
		return dataSource;
	}
	
}
