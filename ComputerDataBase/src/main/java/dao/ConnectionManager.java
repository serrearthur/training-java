package dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dao.exceptions.DAOConfigurationException;

/**
 * Class containing the methods to configure a connection to the database.
 * @author aserre
 */
public class ConnectionManager {
    private static final String CONFIG_FILE = "/db.properties";

    private HikariConfig config;
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
     * Contructor for a new ConnectionManger.
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
        try {
            this.config = new HikariConfig(CONFIG_FILE);
        } catch (RuntimeException e) {
            throw new DAOConfigurationException("Unable to load the file \"" + CONFIG_FILE + "\".");
        }
        try {
            this.datasource = new HikariDataSource(this.config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DAOConfigurationException("Invalid properties.");
        }
    }

    /**
     * Gets a connection from the pool or creates a new one.
     * @return a connection taken from the pool
     * @throws DAOConfigurationException thrown if Hikari is unable to connect to the database
     */
    public Connection getConnection() throws DAOConfigurationException {
        try {
            return this.datasource.getConnection();
        } catch (SQLException e) {
            throw new DAOConfigurationException("Unable to connect to database : " + e.getMessage());
        }
    }
}