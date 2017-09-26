package dao;

import java.sql.Connection;
import java.sql.SQLException;

import dao.exceptions.DAOConfigurationException;
import dao.impl.DAOCompany;
import dao.impl.DAOComputer;

/**
 * Singleton holding the reference to the {@link DAOCompany} and {@link DAOComputer}.
 * @author aserre
 */
public final class DAOFactory {
    private ConnectionManager manager;
    private IDAOComputer daoComputer;
    private IDAOCompany daoCompany;

    /**
     * Singleton holder for {@link DAOFactory}.
     */
    private static class SingletonHolder {
        /** Instance unique non préinitialisée. */
        private static final DAOFactory INSTANCE = new DAOFactory();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link DAOFactory}
     */
    public static DAOFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Constructor for {@link DAOFactory}.
     * @throws DAOConfigurationException thrown by the {@link ConnectionManager}
     */
    private DAOFactory() throws DAOConfigurationException {
        try {
            this.manager = new ConnectionManager();
            this.daoComputer = new DAOComputer(this);
            this.daoCompany = new DAOCompany(this);
        } catch (DAOConfigurationException e) {
            throw e;
        }
    }

    /**
     * Returns the current connection or creates a new one if the connection is closed.
     * @return an open {@link Connection}
     * @throws SQLException thrown by the {@link Connection}
     */
    public Connection getConnection() throws SQLException {
        if (manager.getConnection() == null || manager.getConnection().isClosed()) {
            manager.startConnection();
        }
        return manager.getConnection();
    }

    public IDAOComputer getComputerDao() {
        return daoComputer;
    }

    public IDAOCompany getCompanyDao() {
        return daoCompany;
    }
}
