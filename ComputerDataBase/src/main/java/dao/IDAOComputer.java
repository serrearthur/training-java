package dao;

import java.util.List;

import dao.exceptions.DAOException;
import model.Computer;

public interface IDAOComputer {
    void create(Computer computer) throws DAOException;

    void update(Computer computer) throws DAOException;

    void delete(Computer computer) throws DAOException;

    List<Computer> getFromName(String name) throws DAOException;

    List<Computer> getFromId(Integer id) throws DAOException;

    List<Computer> getFromCompanyId(Integer id) throws DAOException;

    List<Computer> getAll() throws DAOException;
}
