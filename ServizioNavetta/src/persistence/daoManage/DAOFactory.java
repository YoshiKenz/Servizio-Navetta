package persistence.daoManage;

import persistence.daoManage.crud.Crud;
import persistence.daoManage.crud.SecurityDAO;

public abstract class DAOFactory {

	// --- List of supported DAO types ---

	
	/**
	 * Numeric constant '1' corresponds to explicit MySQL choice
	 */
	public static final int MYSQL = 1;
	
	/**
	 * Numeric constant '2' corresponds to explicit Postgres choice
	 */
	public static final int POSTGRESQL = 2;
	
	
	// --- Actual factory method ---
	
	/**
	 * Depending on the input parameter
	 * this method returns one out of several possible 
	 * implementations of this factory spec 
	 */
	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		
		case MYSQL:
			return null;//new HsqldbDAOFactory();
		case POSTGRESQL:
			return new PostgresDAOFactory();
		default:
			return null;
		}
	}
	
	
	
	// --- Factory specification: concrete factories implementing this spec must provide this methods! ---
	
	/**
	 * Method to obtain a DATA ACCESS OBJECT
	 * for the datatype 'Student'
	 */
	public abstract Crud getStudenteDAO();
	public abstract Crud getPrenotazioneDAO();
	public abstract Crud getAmministratoreDAO();
	public abstract Crud getAutistaDAO();
	public abstract Crud getDomandaRiabilitazioneDAO();
	public abstract Crud getFeedBackDAO();
	public abstract Crud getFermataDAO();
	public abstract Crud getNavettaDAO();
	public abstract Crud getTrattoLineaDAO();
	public abstract SecurityDAO getPersonaSecureDAO();


}
