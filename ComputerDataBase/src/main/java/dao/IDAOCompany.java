package dao;

import java.util.List;

import dao.exceptions.DAOException;
import model.Company;

public interface IDAOCompany {
    void create(Company company) throws DAOException;

    void update(Company company) throws DAOException;

    void delete(Company company) throws DAOException;

    List<Company> getFromId(Integer id) throws DAOException;

    List<Company> getFromName(String name) throws DAOException;

    List<Company> getAll() throws DAOException;
}
