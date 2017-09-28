package dao;

import java.util.List;

import dao.exceptions.DAOException;
import dao.impl.DAOComputer;
import model.Computer;

/**
 * Interface representing the method used by a {@link DAOComputer}.
 * @author aserre
 */
public interface IDAOComputer {
    /**
     * Method to create a computer in the database.
     * @param computer {@link Computer} to be created
     * @throws DAOException thrown when a connection problem happens.
     */
    void create(Computer computer) throws DAOException;

    /**
     * Method to update a computer in the database.
     * @param computer {@link Computer} to be updated
     * @throws DAOException thrown when a connection problem happens.
     */
    void update(Computer computer) throws DAOException;

    /**
     * Method to delete a computer in the database.
     * @param computer {@link Computer} to be deleted
     * @throws DAOException thrown when a connection problem happens.
     */
    void delete(Computer computer) throws DAOException;

    /**
     * Method to delete a company and all the computers associated with it in the database.
     * @param c {@link Computer} from the company to be deleted
     * @throws DAOException thrown when a connection problem happens.
     */
    void deleteCompanyId(Computer c) throws DAOException;

    /**
     * Get a list of computers that have a specified id.
     * @param id ID of the computers
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getFromId(Integer id) throws DAOException;

    /**
     * Get a list of computers that have a specified name.
     * @param name name of the computers
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getFromName(String name) throws DAOException;

    /**
     * Get a list of computers that have a specified computerId.
     * @param id id of the company
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getFromCompanyId(Integer id) throws DAOException;

    /**
     * Get a list of all the computers.
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getAll() throws DAOException;
}
