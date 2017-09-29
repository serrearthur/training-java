package dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
     * @param idList list of id of the {@link Computer} to be deleted
     * @throws DAOException thrown when a connection problem happens.
     */
    void delete(String idList) throws DAOException;

    /**
     * Method to delete all computers with a specific companyId.
     * @param idList list of companyId from the companyies to be deleted
     * @throws DAOException thrown when a connection problem happens.
     */
    void deleteCompanyId(String idList) throws DAOException;

    /**
     * Get a list of computers that have a specified id.
     * @param id ID of the computers
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getFromId(Integer id) throws DAOException;

    /**
     * Get a list of computers that have a specified name.
     * @param start starting index
     * @param limit limit for the page size
     * @param count variable used to store the row count
     * @param name name of the computers
     * @param col column to order by
     * @return a list of the corresponding computers
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Computer> getFromName(Integer start, Integer limit, AtomicInteger count, String name, String col) throws DAOException;
}
