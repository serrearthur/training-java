package dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dao.exceptions.DAOConfigurationException;
import dao.exceptions.DAOException;

/**
 * Class containing the methods to configure a connection to the database.
 * @author aserre
 */
public class ConnectionManager {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConnectionManager.class);
    private static final String CONFIG_FILE = "/db.properties";
    private static final ThreadLocal<Connection> THREAD_CONNECTION =
            new ThreadLocal<Connection>() {
        @Override public Connection initialValue() {
            return null;
        }
    };

    private HikariDataSource datasource;

    /**
     * Initialization-on-demand singleton holder  for {@link ConnectionManager}.
     */
    private static class SingletonHolder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link ConnectionManager}
     */
    public static ConnectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Contructor for a new ConnectionManager.
     * @throws DAOConfigurationException thrown by {@link ConnectionManager#loadConfigFile()}
     */
    private ConnectionManager() throws DAOConfigurationException {
        loadConfigFile();
    }

    /**
     * Load the dao.config file and configure the JDBC driver accordingly.
     * @throws DAOConfigurationException thrown if the dao.config file can't be found
     */
    private void loadConfigFile() throws DAOConfigurationException {
        HikariConfig config;
        try {
            config = new HikariConfig(CONFIG_FILE);
        } catch (RuntimeException e) {
            String message = "Unable to load the file \"" + CONFIG_FILE + "\".";
            logger.error(message);
            throw new DAOConfigurationException(message);
        }
        try {
            this.datasource = new HikariDataSource(config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            String message = "Invalid properties";
            logger.error(message);
            throw new DAOConfigurationException(message);
        }
    }

    /**
     * Gets a connection from the pool or creates a new one.
     * @return a connection taken from the pool
     * @throws DAOException thrown if Hikari is unable to connect to the database
     */
    public Connection getConnection() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() == null) {
                THREAD_CONNECTION.set(this.datasource.getConnection());
            }
            return THREAD_CONNECTION.get();
        } catch (SQLException e) {
            throw new DAOException("Unable to connect to database : " + e.getMessage());
        }
    }

    /**
     * Closes the current connection.
     * @throws DAOException thrown when an error is encountered when closing the connection
     */
    public void closeConnection() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().close();
                THREAD_CONNECTION.remove();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while closing the connection : " + e.getMessage());
        }
    }

    /**
     * Commits the current transaction.
     * @throws DAOException thrown when an error is encountered when commiting
     */
    public void commit() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().commit();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while commiting the transaction : " + e.getMessage());
        }
    }

    /**
     * Commits the current transaction.
     * @throws DAOException thrown when an error is encountered during the rollback
     */
    public void rollback() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().rollback();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while rolling back the transaction : " + e.getMessage());
        }
    }

    /**
     * Change the value for autocommit.
     * @param flag new value for autocommit
     * @throws DAOException thrown when an error is encountered during the operation
     */
    public void setAutoCommit(boolean flag) throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().setAutoCommit(flag);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating the autocommit : " + e.getMessage());
        }
    }

    /**
     * Gets value for autocommit.
     * @return the value of the autocommit flag
     * @throws DAOException thrown when an error is encountered during the operation
     */
    public boolean getAutoCommit() throws DAOException {
        try {
            return THREAD_CONNECTION.get().getAutoCommit();
        } catch (SQLException | NullPointerException e) {
            throw new DAOException("Error while getting the autocommit value : " + e.getMessage());
        }
    }
}