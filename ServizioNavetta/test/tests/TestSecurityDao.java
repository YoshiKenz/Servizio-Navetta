package tests;

import persistence.daoManage.DataSource;
import persistence.daoManage.jdbcDao.PersonaSecureDAO;
import persistence.persistentModel.Password;

public class TestSecurityDao {

	public static void main(String[] args) {

		PersonaSecureDAO secure = new PersonaSecureDAO(
				new DataSource("jdbc:postgresql://localhost:5432/ServizioNavetta","postgres","postgres"));
		boolean response = secure.authorizeDao("1", "p");
		if(response)
			System.out.println("Authorized");
		
		Password pass = (Password) secure.retriveSensitiveData("123456");
		
		if(pass!=null && pass.password!=null)
			System.out.println("PASSWORD : "+pass.password);
		
		response = secure.deauthorizeDao("1", "p");
		
		if(response)
			System.out.println("deauthorized");
	}

}
