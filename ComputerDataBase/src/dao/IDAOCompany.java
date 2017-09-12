package dao;

import dao.exceptions.DAOException;
import model.Company;

public interface IDAOCompany {
	 void create( Company company ) throws DAOException;
	 Company getFromName( String name ) throws DAOException;
	 Company getFromId( Integer id ) throws DAOException;
	 void update(Company company) throws DAOException;
	 void delete(Company company) throws DAOException;
}
