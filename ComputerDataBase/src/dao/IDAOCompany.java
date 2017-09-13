package dao;

import java.util.List;

import dao.exceptions.DAOException;
import model.Company;

public interface IDAOCompany {
	public void create(Company company) throws DAOException;

	public void update(Company company) throws DAOException;

	public void delete(Company company) throws DAOException;

	public List<Company> getFromId(String id) throws DAOException;

	public List<Company> getFromName(String name) throws DAOException;

	public List<Company> getAll() throws DAOException;
}
