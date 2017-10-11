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

import cdb.dao.ConnectionManager;
import cdb.dao.DAOComputer;
import cdb.dao.exceptions.DAOException;
import cdb.model.Computer;

/**
 * Class maping the request made to the database and the {@link Computer}.
 * @author aserre
 */
public class DAOComputerImpl implements DAOComputer {
    private static final String REQUEST_CREATE = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
    private static final String REQUEST_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String REQUEST_DELETE = "DELETE FROM computer WHERE LOCATE(CONCAT(',',id,','), ?) >0";
    private static final String REQUEST_DELETE_COMPANYID = "DELETE FROM computer WHERE LOCATE(CONCAT(',',company_id,','), ?) >0";
    private static final String REQUEST_SELECT_ID = "SELECT * FROM computer WHERE id = ?";
    private static final String REQUEST_SELECT_JOIN = "SELECT SQL_CALC_FOUND_ROWS * FROM computer cpt LEFT JOIN company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE ? OR cpn.name LIKE ?";
    private static final String REQUEST_SELECT_GET_COUNT = "SELECT FOUND_ROWS()";

    private ConnectionManager manager;

    /**
     * Initialization-on-demand singleton holder for {@link DAOComputerImpl}.
     */
    private static class SingletonHolder {
        private static final DAOComputerImpl INSTANCE = new DAOComputerImpl();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link DAOComputerImpl}
     */
    public static DAOComputer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Constructor for the DAOComputer.
     */
    private DAOComputerImpl() {
        this.manager = ConnectionManager.getInstance();
    }

    /**
     * Function mapping a {@link ResultSet} to a {@link Computer}.
     * @param resultSet result of an SQL request
     * @return a {@link Computer} mapped from the result of the request
     * @throws SQLException thrown by {@link ResultSet}
     */
    public static Computer map(ResultSet resultSet) throws SQLException {
        Computer computer = new Computer();
        computer.setId(resultSet.getInt("id"));
        if (resultSet.wasNull()) {
            computer.setId(null);
        }
        computer.setName(resultSet.getString("name"));
        computer.setIntroduced(timestampToDateTime(resultSet.getTimestamp("introduced")));
        computer.setDiscontinued(timestampToDateTime(resultSet.getTimestamp("discontinued")));
        computer.setCompanyId(resultSet.getInt("company_id"));
        if (resultSet.wasNull()) {
            computer.setCompanyId(null);
        }
        return computer;
    }

    /**
     * Make an SQL request to update a computer in the database.
     * @param request SQL request to execute
     * @param returnGeneratedKeys <code>true</code> if the request needs generated keys,
     * <code>false</code> otherwise
     * @param params parameters needed for the request
     * @throws DAOException thrown if the internal {@link Connection},
     * {@link PreparedStatement} or {@link ResultSet} throw an error
     */
    private void executeUpdate(String request, boolean returnGeneratedKeys, Object... params) throws DAOException {
        try (PreparedStatement preparedStatement = initPreparedStatement(manager.getConnection(), request, returnGeneratedKeys, params);) {
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Unable to update this computer, no row added to the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (manager.getAutoCommit()) {
                manager.closeConnection();
            }
        }
    }

    /**
     * Make an SQL request to select a list of computers from the database.
     * @param request SQL request to execute
     * @param count variable used to store the row count
     * @param params parameters needed for the request
     * @return the list of Computers corresponding to the request
     * @throws DAOException thrown if the internal {@link Connection},
     * {@link PreparedStatement} or {@link ResultSet} throw an error
     */
    private List<Computer> executeQuery(String request, AtomicInteger count, Object... params) throws DAOException {
        List<Computer> computers = new ArrayList<Computer>();
        try (PreparedStatement preparedStatement = initPreparedStatement(manager.getConnection(), request, false, params);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            count.set(this.getCount());
            while (resultSet.next()) {
                computers.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (manager.getAutoCommit()) {
                manager.closeConnection();
            }
        }
        return computers;
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

    @Override
    public void create(Computer computer) throws DAOException {
        executeUpdate(REQUEST_CREATE, true, computer.getName(), dateTimeToTimestamp(computer.getIntroduced()),
                        dateTimeToTimestamp(computer.getDiscontinued()), computer.getCompanyId());
    }

    @Override
    public void update(Computer computer) throws DAOException {
        executeUpdate(REQUEST_UPDATE, false, computer.getName(), dateTimeToTimestamp(computer.getIntroduced()),
                        dateTimeToTimestamp(computer.getDiscontinued()), computer.getCompanyId(), computer.getId());
    }

    @Override
    public void delete(String idList) throws DAOException {
        executeUpdate(REQUEST_DELETE, false, "," + idList + ",");
    }

    @Override
    public void deleteCompanyId(String idList) throws DAOException {
        executeUpdate(REQUEST_DELETE_COMPANYID, false, "," + idList + ",");
    }

    @Override
    public List<Computer> getFromId(Integer id) throws DAOException {
        return executeQuery(REQUEST_SELECT_ID, new AtomicInteger(), id.toString());
    }

    @Override
    public List<Computer> getFromName(Integer start, Integer limit, AtomicInteger count, String name, String col, String order) throws DAOException {
        return executeQuery(setOrderLimit(REQUEST_SELECT_JOIN, col, order, start, limit), count, "%" + name + "%", "%" + name + "%");
    }
}