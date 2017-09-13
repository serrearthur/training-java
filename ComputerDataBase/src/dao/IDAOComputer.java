package dao;

import java.util.List;

import dao.exceptions.DAOException;
import model.Computer;

public interface IDAOComputer {
	public void create(Computer computer) throws DAOException;

	public void update(Computer computer) throws DAOException;

	public void delete(Computer computer) throws DAOException;

	public List<Computer> getFromName(String name) throws DAOException;

	public List<Computer> getFromId(String id) throws DAOException;

	public List<Computer> getFromCompanyId(String id) throws DAOException;

	public List<Computer> getAll() throws DAOException;
}
