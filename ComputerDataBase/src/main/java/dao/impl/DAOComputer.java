package dao.impl;

import static dao.impl.DAOUtility.silentShutdown;
import static dao.impl.DAOUtility.initPreparedStatement;
import static dao.impl.DAOUtility.dateTimeToTimestamp;
import static dao.impl.DAOUtility.timestampToDateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.IDAOComputer;
import dao.exceptions.DAOException;
import model.Computer;

public class DAOComputer implements IDAOComputer {
    private static final String REQUEST_CREATE = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
    private static final String REQUEST_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private static final String REQUEST_DELETE = "DELETE FROM computer WHERE id=?";
    private static final String REQUEST_SELECT_ID = "SELECT * FROM computer WHERE id = ?";
    private static final String REQUEST_SELECT_NAME = "SELECT * FROM computer WHERE name LIKE ?";
    private static final String REQUEST_SELECT_COMPANY = "SELECT * FROM computer WHERE company_id = ?";
    private static final String REQUEST_SELECT_ALL = "SELECT * FROM computer";

    private final DAOFactory factory;

    public DAOComputer(DAOFactory factory) {
        this.factory = factory;
    }

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

    private List<Computer> getComputers(String request, Object... param) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Computer> computers = new ArrayList<Computer>();

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, request, false, param);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            while (resultSet.next()) {
                computers.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
        return computers;
    }

    @Override
    public void create(Computer computer) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_CREATE, true, computer.getName(),
                    dateTimeToTimestamp(computer.getIntroduced()), dateTimeToTimestamp(computer.getDiscontinued()),
                    computer.getCompanyId());
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException("Unable to create this computer, no row added to the table.");
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
                computer.setId(resultSet.getInt(1));
            } else {
                throw new DAOException("Unable to create this computer, no generated key given.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void update(Computer computer) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_UPDATE, false, computer.getName(),
                    dateTimeToTimestamp(computer.getIntroduced()), dateTimeToTimestamp(computer.getDiscontinued()),
                    computer.getCompanyId(), computer.getId());
            int status = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (status == 0) {
                throw new DAOException("Unable to update this computer, no row modified in the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public void delete(Computer computer) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            /* Récupération d'une connection depuis la Factory */
            connection = factory.getConnection();
            preparedStatement = initPreparedStatement(connection, REQUEST_DELETE, false, computer.getId());
            int status = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (status == 0) {
                throw new DAOException("Unable to delete this computer, no row removed from the table.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            silentShutdown(resultSet, preparedStatement, connection);
        }
    }

    @Override
    public List<Computer> getFromName(String name) throws DAOException {
        return getComputers(REQUEST_SELECT_NAME, "%" + name + "%");
    }

    @Override
    public List<Computer> getFromId(Integer id) throws DAOException {
        return getComputers(REQUEST_SELECT_ID, id.toString());
    }

    @Override
    public List<Computer> getFromCompanyId(Integer id) throws DAOException {
        return getComputers(REQUEST_SELECT_COMPANY, id.toString());
    }

    @Override
    public List<Computer> getAll() throws DAOException {
        return getComputers(REQUEST_SELECT_ALL);
    }
}
