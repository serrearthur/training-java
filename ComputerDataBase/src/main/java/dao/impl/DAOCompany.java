package dao.impl;

import static dao.impl.DAOUtility.initPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionManager;
import dao.IDAOCompany;
import dao.exceptions.DAOException;
import model.Company;

/**
 * Class maping the request made to the database and the {@link Company}.
 * @author aserre
 */
public class DAOCompany implements IDAOCompany {
    private final String REQUEST_CREATE = "INSERT INTO company (id, name) VALUES (NULL, ?)";
    private final String REQUEST_UPDATE = "UPDATE company SET name=? WHERE id=?";
    private final String REQUEST_DELETE = "DELETE FROM company WHERE id=?";
    private final String REQUEST_SELECT_ID = "SELECT * FROM company WHERE id = ?";
    private final String REQUEST_SELECT_NAME = "SELECT * FROM company WHERE name = ?";
    private final String REQUEST_SELECT_ALL = "SELECT * FROM company";

    /**
     * Initialization-on-demand singleton holder for {@link DAOCompany}.
     */
    private static class SingletonHolder {
        private static final DAOCompany INSTANCE = new DAOCompany();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link DAOCompany}
     */
    public static DAOCompany getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Constructor for the DAOCompany.
     */
    private DAOCompany() {
    }

    /**
     * Function mapping a {@link ResultSet} to a {@link Company}.
     * @param resultSet result of an SQL request
     * @return a {@link Company} mapped from the result of the request
     * @throws SQLException thrown by {@link ResultSet#getInt(String)} and
     * {@link ResultSet#getString(String)}
     */
    public static Company map(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("id"));
        if (resultSet.wasNull()) {
            company.setId(null);
        }
        company.setName(resultSet.getString("name"));
        return company;
    }

    /**
     * Make an SQL request to select a list of companies from the database.
     * @param request SQL request to execute
     * @param params parameters needed for the request
     * @return the list of Companies corresponding to the request
     * @throws DAOException thrown if the internal {@link Connection},
     * {@link PreparedStatement} or {@link ResultSet} throw an error
     */
    private List<Company> executeQuery(String request, Object... params) throws DAOException {
        List<Company> companies = new ArrayList<Company>();

        try (Connection connection = ConnectionManager.getInstance().getConnection();
                PreparedStatement preparedStatement = initPreparedStatement(connection, request, false, params);
                ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                companies.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return companies;
    }

    /**
     * Make an SQL request to update a company in the database.
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
                throw new DAOException("Unable to update this company, no row added to the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void create(Company company) throws DAOException {
        executeUpdate(REQUEST_CREATE, true, company.getName());
    }

    @Override
    public void update(Company company) throws DAOException {
        executeUpdate(REQUEST_UPDATE, false, company.getName(), company.getId());
    }

    @Override
    public void delete(Company company) throws DAOException {
        executeUpdate(REQUEST_DELETE, false, company.getId());
    }

    @Override
    public List<Company> getFromId(Integer id) throws DAOException {
        return executeQuery(REQUEST_SELECT_ID, id.toString());
    }

    @Override
    public List<Company> getFromName(String name) throws DAOException {
        return executeQuery(REQUEST_SELECT_NAME, name);
    }

    @Override
    public List<Company> getAll() throws DAOException {
        return executeQuery(REQUEST_SELECT_ALL);
    }
}