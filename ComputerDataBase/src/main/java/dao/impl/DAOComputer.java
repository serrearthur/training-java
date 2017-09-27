package dao.impl;

import static dao.impl.DAOUtility.initPreparedStatement;
import static dao.impl.DAOUtility.dateTimeToTimestamp;
import static dao.impl.DAOUtility.timestampToDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionManager;
import dao.IDAOComputer;
import dao.exceptions.DAOException;
import model.Computer;

/**
 * Class maping the request made to the database and the {@link Computer}.
 * @author aserre
 */
public class DAOComputer implements IDAOComputer {
    private static final String REQUEST_CREATE = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
    private static final String REQUEST_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String REQUEST_DELETE = "DELETE FROM computer WHERE id=?";
    private static final String REQUEST_SELECT_ID = "SELECT * FROM computer WHERE id = ?";
    private static final String REQUEST_SELECT_NAME = "SELECT * FROM computer WHERE name LIKE ?";
    private static final String REQUEST_SELECT_COMPANY = "SELECT * FROM computer WHERE company_id = ?";
    private static final String REQUEST_SELECT_ALL = "SELECT * FROM computer";

    /**
     * Initialization-on-demand singleton holder for {@link DAOComputer}.
     */
    private static class SingletonHolder {
        private static final DAOComputer INSTANCE = new DAOComputer();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link DAOComputer}
     */
    public static DAOComputer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Constructor for the DAOComputer.
     */
    private DAOComputer() {
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
     * Make an SQL request to select a list of companies from the database.
     * @param request SQL request to execute
     * @param params parameters needed for the request
     * @return the list of Computers corresponding to the request
     * @throws DAOException thrown if the internal {@link Connection},
     * {@link PreparedStatement} or {@link ResultSet} throw an error
     */
    private List<Computer> executeQuery(String request, Object... params) throws DAOException {
        List<Computer> computers = new ArrayList<Computer>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement preparedStatement = initPreparedStatement(connection, request, false, params);
        ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                computers.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return computers;
    }

    /**
     * Make an SQL request to select a list of companies from the database.
     * @param request SQL request to execute
     * @param returnGeneratedKeys <code>true</code> if the request needs generated keys,
     * <code>false</code> otherwise
     * @param params parameters needed for the request
     * @throws DAOException thrown if the internal {@link Connection},
     * {@link PreparedStatement} or {@link ResultSet} throw an error
     */
    private void executeUpdate(String request, boolean returnGeneratedKeys, Object... params) throws DAOException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
        PreparedStatement preparedStatement = initPreparedStatement(connection, request, returnGeneratedKeys, params);) {
            int status = preparedStatement.executeUpdate();
            if (status == 0) {
                throw new DAOException("Unable to update this computer, no row added to the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
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
    public void delete(Computer computer) throws DAOException {
        executeUpdate(REQUEST_DELETE, false, computer.getId());
    }

    @Override
    public List<Computer> getFromName(String name) throws DAOException {
        return executeQuery(REQUEST_SELECT_NAME, "%" + name + "%");
    }

    @Override
    public List<Computer> getFromId(Integer id) throws DAOException {
        return executeQuery(REQUEST_SELECT_ID, id.toString());
    }

    @Override
    public List<Computer> getFromCompanyId(Integer id) throws DAOException {
        return executeQuery(REQUEST_SELECT_COMPANY, id.toString());
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        return executeQuery(REQUEST_SELECT_ALL);
    }
}