package persistence.daoManage.crud;

public interface SecurityDAO{
	
	public boolean authorizeDao(String Admin,String pass);
	public boolean deauthorizeDao(String Admin,String pass);
	public boolean isAuthorized();
	public SensitiveDataTag retriveSensitiveData(String pKey);
	public boolean setSensitiveData(String pKey,SensitiveDataTag data);
}
