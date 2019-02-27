package tests;

import java.util.ArrayList;
import java.util.Calendar;

import persistence.daoManage.DAOFactory;
import persistence.daoManage.PostgresDAOFactory;
import persistence.daoManage.jdbcDao.StudenteDaoJDBC;
import persistence.persistentModel.Password;
import persistence.persistentModel.Prenotazione;
import persistence.persistentModel.Studente;
import persistence.persistentModel.StudenteProxy;
import persistence.utility.Utility;

public class MainTest {

	public static void main(String[] args) {
		DAOFactory df = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		StudenteDaoJDBC dao = (StudenteDaoJDBC) df.getStudenteDAO();
		Studente stud = dao.findByPrimaryKey("7891011");
		if(stud!=null)
			System.out.println("STUDENTE : "+stud.getMatricola()+" "+stud.getNome()+" "+stud.getCognome()+" "+stud.getEmail());
		else
			System.out.println("Studente non trovato !");
		
		Password pass2 = new Password();
		pass2.password = "pass2";
		Studente s2 = new Studente(7891012, 0, "Antonio", "Antonii", "mail@mail.mail", pass2);
		dao.save(s2);
		
//		ArrayList<Studente> studenti = dao.findAll();
		
//		for(Studente sI:studenti) {
//			System.out.println("STUDENTE : "+sI.getMatricola()+" "+sI.getNome()+" "+sI.getCognome()+" "+sI.getEmail()+" "+sI.getFlag());
//		}
		
		Studente s3 = new Studente(7891011, 0, "Rocco", "Co", "franco@co.mail", pass2);
		dao.update(s3);
		Runtime.getRuntime().gc();
		System.out.println("BEFORE 1 : "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		Runtime.getRuntime().gc();
		StudenteProxy _123456 =(StudenteProxy)  dao.findByPrimaryKey("123456");
		Runtime.getRuntime().gc();
//		StudenteProxy proxy123456 = new StudenteProxy(_123456, new DataSource("jdbc:postgresql://localhost:5432/ServizioNavetta","postgres","postgres"));
		System.out.println("BEFORE : "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		Runtime.getRuntime().gc();
		ArrayList<Prenotazione> prenotazioni = _123456.getPrenotazioni();
		for(Prenotazione prenotazione : prenotazioni)
			System.out.println("Prenotazione ID : "+prenotazione.getID());
		Runtime.getRuntime().gc();
		Calendar calendar = prenotazioni.get(0).getDateTime();
		Runtime.getRuntime().gc();
		System.out.println("AFTER : "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		Runtime.getRuntime().gc();
		String somedate = Utility.convertSimpleDateFormat(calendar);
		Runtime.getRuntime().gc();
		System.out.println(somedate);

	}

}
