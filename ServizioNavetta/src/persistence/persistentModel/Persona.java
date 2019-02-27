package persistence.persistentModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistence.daoManage.crud.CrudTag;

public class Persona implements CrudTag{
	private String nome;
	private String cognome;
	private String email;
	private Password password;
	private static final Pattern emailPattern = Pattern.compile(".*\\@.+\\..+");
	
	public Persona(String nome,String cognome,String email,Password password) {
		this.nome = nome;
		this.cognome = cognome;
		setEmail(email);
		this.password = new Password();
		setPassword(password);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		Matcher matcher = Persona.emailPattern.matcher(email);
		if(!matcher.matches())
			throw new RuntimeException("Email parameter doesn't seem to respect email pattern"+System.lineSeparator());
		this.email = email;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		if(password ==null || password.password==null)
			return;
		this.password.password = new String(password.password);
	}
}
