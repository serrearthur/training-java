package dao.impl;

import static dao.impl.DAOUtility.silentShutdown;
import static dao.impl.DAOUtility.initPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
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

    private DAOFactory factory;

    /**
     * Constructor for the DAOCompany.
     * @param factory singleton holding the DAOFactory
     */
    public DAOCompany(DAOFactory factory) {
        this.factory = factory;
    }

    /**
     * Function mapping a {@link ResultSet} to a {@link Company}.
     * @param resultSet result of an SQL request
     * @return a {@link Company} mapped from the result of the request
     * @throws SQLException thrown by {@link ResultSet#getInt(String)} and {@link ResultSet#getString(String)}
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
     * @param param parameters needed for the request
     * @return the list of Companies corresponding to the request
     * @throws DAOException thrown if the internal {@link Connection}, {@link PreparedStatement} or {@link ResultSet}
     * throw an error
     */
    private List<Company> getCompanies(String request, Object... param) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Company> companies = new ArrayList<Company>();

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, request, false, param);
            resultSet = preparedStatement.executeQuery();

            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            while (resultSet.next()) {
                companies.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }

        return companies;
    }

    @Override
    public void create(Company company) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_CREATE, true, company.getName());
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException("Unable to create this company, no row added to the table.");
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                company.setId(resultSet.getInt(1));
            } else {
                throw new DAOException("Unable to create this company, no generated key given.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void update(Company company) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_UPDATE, false, company.getName(),
                    company.getId());
            int status = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (status == 0) {
                throw new DAOException("Unable to update this company, no row modified in the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void delete(Company company) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_DELETE, false, company.getId());
            int status = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (status == 0) {
                throw new DAOException("Unable to delete this company, no row removed from the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<Company> getFromId(Integer id) throws DAOException {
        return getCompanies(REQUEST_SELECT_ID, id.toString());
    }

    @Override
    public List<Company> getFromName(String name) throws DAOException {
        return getCompanies(REQUEST_SELECT_NAME, name);
    }

    @Override
    public List<Company> getAll() throws DAOException {
        return getCompanies(REQUEST_SELECT_ALL);
    }
}