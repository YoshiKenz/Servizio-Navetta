package model;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.DatabaseManager;
import persistence.daoManage.crud.Crud;
import persistence.persistentModel.Autista;
import persistence.persistentModel.Linea;
import persistence.persistentModel.Navetta;

public class RegistroAttivitaNavette {
	
	
	private static RegistroAttivitaNavette instance;
	
	public static RegistroAttivitaNavette getInstance() {
		if(instance==null)
			instance = new RegistroAttivitaNavette(new Date());
		return instance;
	}
	
	private Date data;
	
	
	/**
	 *  The register for one Date is basically an HashMap, where the key 
	 *  is the ID of the Driver which drives the bus, and the value is
	 * 	an instance of "LineaRegistroNavette" which contains the following data :
	 * 	
	 * 	- "NavettA" "navettA" (Bus which is Driver is driving)
	 * 	- "inT" "giriCompletati" (Tours completed since start of shift)
	 *  - "AutistA" "autistA"	(Driver of the Bus)
	 *  - "TrattoLinea" "posizionE" (Current position of the Bus)
	 *  - "LineA" "lineA" (Bus line in which Bus is working)
	 * 
	 * */
	private HashMap<Integer,LineaRegistroNavette> linee;
	
	private RegistroAttivitaNavette(Date data) {
		this.data = data;
		linee = new HashMap<Integer,LineaRegistroNavette>();
	}

	public boolean addLinea(int AutistaId) {
		DAOFactory factory = DatabaseManager.getInstance().getDaoFactory();
		Crud autistaDao = factory.getAutistaDAO();
		Autista autista = (Autista) autistaDao.findByPrimaryKey(AutistaId+"");
		if(autista==null)
			return false;
		linee.put(Integer.valueOf(AutistaId), new LineaRegistroNavette());
		return true;
	}
	
	public LineaRegistroNavette getLineaRegistro(int AutistaId) {
		return linee.get(Integer.valueOf(AutistaId));
	}

	public Date getData() {
		return data;
	}

	public HashSet<Linea> getLineeAttive(){
		HashSet<Linea> ret = new HashSet<Linea>();
		for(LineaRegistroNavette l : linee.values()) {
			if(l.getLinea()!=null && ret.contains(l.getLinea())==false)
				ret.add(l.getLinea());
		}
		return ret;
	}
	
	public HashSet<Navetta> getNavetteAttive(){
		HashSet<Navetta> ret = new HashSet<Navetta>();
		for(LineaRegistroNavette l : linee.values()) {
			if(l.getNavetta()!=null && ret.contains(l.getNavetta())==false)
				ret.add(l.getNavetta());
		}
		return ret;
	}
	
	public HashSet<Navetta> getNavetteAttive(Linea ln){
		HashSet<Navetta> ret = new HashSet<Navetta>();
		for(LineaRegistroNavette l : linee.values()) {
			if(l.getNavetta()!=null && ret.contains(l.getNavetta())==false && l.getLinea().getNome().equals(ln.getNome()))
				ret.add(l.getNavetta());
		}
		return ret;
	}
	
	public int getIdAutista(int navetta) {
		for(LineaRegistroNavette l : linee.values())
			if(l.getNavetta()!=null && l.getNavetta().getID()==navetta)
				return l.getAutista().getID();
		return -1;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(LineaRegistroNavette l : linee.values())
			ret = ret.concat(l.toString()).concat(System.lineSeparator());
		return ret;
	}
	
}
