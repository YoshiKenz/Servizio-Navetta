package persistence.daoManage.crud;

import java.util.List;

public interface Crud {

	public void save(CrudTag obj); //Create
	
	public CrudTag findByPrimaryKey(String pKey); //Retrive
	
	public List<? extends CrudTag> findAll();
	
	public void update(CrudTag obj); //Update
	
	public void delete(CrudTag obj); //Delete
}
