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
        HikariConfig config;
        try {
            config = new HikariConfig(CONFIG_FILE);
        } catch (RuntimeException e) {
            throw new DAOConfigurationException("Unable to load the file \"" + CONFIG_FILE + "\".");
        }
        try {
            this.datasource = new HikariDataSource(config);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new DAOConfigurationException("Invalid properties.");
        }
    }

    private static final ThreadLocal<Connection> THREAD_CONNECTION =
            new ThreadLocal<Connection>() {
        @Override public Connection initialValue() {
            return null;
        }
    };

    private int compt = 0;

    /**
     * Gets a connection from the pool or creates a new one.
     * @return a connection taken from the pool
     * @throws DAOConfigurationException thrown if Hikari is unable to connect to the database
     */
    public Connection getConnection() throws DAOConfigurationException {
        compt++;
        try {
            if (THREAD_CONNECTION.get() == null) {
                THREAD_CONNECTION.set(this.datasource.getConnection());
            }
            System.out.println(Thread.currentThread().getStackTrace()[5].getMethodName() + "." + Thread.currentThread().getStackTrace()[4].getMethodName() + "." + Thread.currentThread().getStackTrace()[3].getMethodName());
            System.out.println(compt + " : we got the connection : " + THREAD_CONNECTION.get().isClosed());
            return THREAD_CONNECTION.get();
        } catch (SQLException e) {
            throw new DAOConfigurationException("Unable to connect to database : " + e.getMessage());
        }
    }

    /**
     * Closes the current connection.
     * @throws DAOConfigurationException thrown when an error is encountered when closing the connection
     */
    public void closeConnection() throws DAOConfigurationException {
        try {
            System.out.println("tried to close");
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().close();
                THREAD_CONNECTION.remove();
            }
        } catch (SQLException e) {
            throw new DAOConfigurationException("Error while closing the connection : " + e.getMessage());
        }
    }
}