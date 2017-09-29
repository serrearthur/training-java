package dao;

import java.util.List;

import dao.exceptions.DAOException;
import dao.impl.DAOCompany;
import model.Company;

/**
 * Interface representing the methods used by a {@link DAOCompany}.
 * @author aserre
 */
public interface IDAOCompany {
    /**
     * Method to create a company in the database.
     * @param company {@link Company} to be created
     * @throws DAOException thrown when a connection problem happens.
     */
    void create(Company company) throws DAOException;

    /**
     * Method to update a company in the database.
     * @param company {@link Company} to be updated
     * @throws DAOException thrown when a connection problem happens.
     */
    void update(Company company) throws DAOException;

    /**
     * Method to delete a company in the database.
     * @param id id of the {@link Company} to be deleted
     * @throws DAOException thrown when a connection problem happens.
     */
    void delete(Integer id) throws DAOException;

    /**
     * Get a list of companies that have a specified id.
     * @param id ID of the companies
     * @return a list of the corresponding companies
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Company> getFromId(Integer id) throws DAOException;

    /**
     * Get a list of companies that have a specified name.
     * @param name name of the companies
     * @return a list of the corresponding companies
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Company> getFromName(String name) throws DAOException;

    /**
     * Get a list of all the companies.
     * @return a list of the corresponding companies
     * @throws DAOException thrown when a connection problem happens.
     */
    List<Company> getAll() throws DAOException;
}
