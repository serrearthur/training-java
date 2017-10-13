package cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;

import cdb.dao.exceptions.DAOConfigurationException;
import cdb.dao.exceptions.DAOException;

/**
 * Class containing the methods to configure a connection to the database.
 * @author aserre
 */
public class ConnectionManager {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConnectionManager.class);
    //private static final String CONFIG_FILE = "/db.properties";
    private static final ThreadLocal<Connection> THREAD_CONNECTION =
            new ThreadLocal<Connection>() {
        @Override public Connection initialValue() {
            return null;
        }
    };

    private DataSource dataSource;
    //private HikariDataSource dataSource;

    /**
     * Contructor for a new ConnectionManager.
     * @throws DAOConfigurationException thrown by {@link ConnectionManager#loadConfigFile()}
     */
    public ConnectionManager() throws DAOConfigurationException {
        //loadConfigFile(CONFIG_FILE);
    }

    /**
     * Load the config file and configure the JDBC driver accordingly.
     * @param configFile path to the config file
     * @throws DAOConfigurationException thrown if the cdb.dao.config file can't be found
     */
    public void loadConfigFile(String configFile) throws DAOConfigurationException {
        HikariConfig config;
        try {
            config = new HikariConfig(configFile);
        } catch (RuntimeException e) {
            String message = "Unable to load the file \"" + configFile + "\".";
            logger.error(message);
            throw new DAOConfigurationException(message);
        }
        try {
            config.equals(null);
            //this.dataSource = new HikariDataSource(config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            String message = "Invalid properties";
            logger.error(message);
            throw new DAOConfigurationException(message);
        }
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Gets a connection from the pool or creates a new one.
     * @return a connection taken from the pool
     * @throws DAOException thrown if Hikari is unable to connect to the database
     */
    public Connection getConnection() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() == null) {
                THREAD_CONNECTION.set(this.dataSource.getConnection());
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