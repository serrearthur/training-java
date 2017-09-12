package dao;

import dao.exceptions.DAOException;
import model.Computer;

public interface IDAOComputer {
	 void create( Computer computer ) throws DAOException;
	 Computer getFromName( String name ) throws DAOException;
	 Computer getFromId( Integer id ) throws DAOException;
	 void update(Computer computer) throws DAOException;
	 void delete(Computer computer) throws DAOException;
}
