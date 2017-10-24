package cdb.dao.impl;

import static cdb.dao.impl.DAOUtility.dateTimeToTimestamp;
import static cdb.dao.impl.DAOUtility.initPreparedStatement;
import static cdb.dao.impl.DAOUtility.timestampToDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cdb.dao.DAOComputer;
import cdb.dao.exceptions.DAOException;
import cdb.model.Company;
import cdb.model.Computer;

/**
 * Class maping the request made to the database and the {@link Computer}.
 * @author aserre
 */
@Repository
public class DAOComputerImpl implements DAOComputer {
    private static final String REQUEST_CREATE = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
    private static final String REQUEST_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String REQUEST_DELETE = "DELETE FROM computer WHERE LOCATE(CONCAT(',',id,','), ?) >0";
    private static final String REQUEST_DELETE_COMPANYID = "DELETE FROM computer WHERE LOCATE(CONCAT(',',company_id,','), ?) >0";
    private static final String REQUEST_SELECT_ID = "SELECT * FROM computer WHERE id = ?";
    private static final String REQUEST_SELECT_JOIN = "SELECT SQL_CALC_FOUND_ROWS * FROM computer cpt LEFT JOIN company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE ? OR cpn.name LIKE ?";
    private static final String REQUEST_SELECT_GET_COUNT = "SELECT FOUND_ROWS()";

    private SessionFactory sessionFactory;

    /**
     * Constructor.
     * @param sessionFactory {@link SessionFactory} used for the DAO
     */
    @Autowired
    private DAOComputerImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Computer computer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(computer);
    }

    @Override
    public void update(Computer computer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(computer);
    }

    @Override
    public void delete(String idList) {
        Session session = this.sessionFactory.getCurrentSession();
        Computer personToDelete = getFromId(Integer.parseInt(idList));
        session.delete(personToDelete);
    }

    @Override
    public void deleteCompanyId(String idList) throws DAOException {
        executeUpdate(REQUEST_DELETE_COMPANYID, false, "," + idList + ",");
    }

    @Override
    public Computer getFromId(Integer id) throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        Computer retrievedPerson = (Computer) session.get(Computer.class, id);
        return retrievedPerson;
    }
    
    public List<Computer> getFromCompanyId(Integer companyId) {
        
    }

    //SELECT SQL_CALC_FOUND_ROWS * FROM computer cpt LEFT JOIN company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE ? OR cpn.name LIKE ?
    //                   ORDER BY ? ? LIMIT ?,?;
    @Override
    public List<Computer> getFromName(Integer start, Integer limit, AtomicInteger count, String name, String col, String order) {
        
        return executeQuery(setOrderLimit(REQUEST_SELECT_JOIN, col, order, start, limit), count, "%" + name + "%", "%" + name + "%");
    }

    /**
     * Returns the row count of the previous request. Must be executed right after the request.
     * @return the row count of the previous request
     * @throws SQLException thrown when a connection problem happens.
     */
    private int getCount() throws SQLException {
        int count = 0;
        try (PreparedStatement preparedStatement = initPreparedStatement(manager.getConnection(), REQUEST_SELECT_GET_COUNT, false);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
        return count;
    }

    /**
     * Appends the ORDER BY and LIMIT commands to the SQL request.
     * @param request request to modify
     * @param col column to sort by
     * @param order "ASC" or "DESC
     * @param start starting index
     * @param limit limit for the page size
     * @return a new SQL request string
     */
    private String setOrderLimit(String request, String col, String order, Integer start, Integer limit) {
        return request + " ORDER BY " + col + " " + order + " LIMIT " + start + "," + limit;
    }

}