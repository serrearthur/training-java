package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.exceptions.DAOConfigurationException;

public class ConnectionManager {
    private static final String CONFIG_FILE = "dao.properties";
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USER = "username";
    private static final String PROPERTY_PASS = "password";

    private String url;
    private String username;
    private String password;
    private String driver;

    private Connection connection;

    public ConnectionManager() throws DAOConfigurationException {
        loadConfigFile();
    }

    public Connection getConnection() {
        return connection;
    }

    public void loadConfigFile() throws DAOConfigurationException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertyFile = classLoader.getResourceAsStream(CONFIG_FILE);

        if (propertyFile == null) {
            throw new DAOConfigurationException("The properties file \"" + CONFIG_FILE + "\" does not exist.");
        }
        try {
            properties.load(propertyFile);
            this.url = properties.getProperty(PROPERTY_URL);
            this.driver = properties.getProperty(PROPERTY_DRIVER);
            this.username = properties.getProperty(PROPERTY_USER);
            this.password = properties.getProperty(PROPERTY_PASS);
        } catch (IOException e) {
            throw new DAOConfigurationException("Unable to load file \"" + CONFIG_FILE + "\" : ", e);
        }
    }

    public void startConnection() throws DAOConfigurationException {
        try {
            Class.forName(this.driver);
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException e) {
            throw new DAOConfigurationException("Can't find driver in classpath : ", e);
        } catch (SQLException e) {
            throw new DAOConfigurationException("Unable to connect to database : " + e.getMessage());
        }
    }
}