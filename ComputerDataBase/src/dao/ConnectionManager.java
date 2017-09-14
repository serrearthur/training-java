package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.exceptions.DAOConfigurationException;

public class ConnectionManager {
	private static final String CONFIG_FILE = "dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER = "username";
	private static final String PROPERTY_PASS = "password";

	private String url;
	private String username;
	private String password;

	private Connection connection;

	public ConnectionManager() throws DAOConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String username;
		String password;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertyFile = classLoader.getResourceAsStream(CONFIG_FILE);

		if (propertyFile == null) {
			throw new DAOConfigurationException("Le fichier properties \"" + CONFIG_FILE + "\" est introuvable.");
		}

		try {
			properties.load(propertyFile);
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			username = properties.getProperty(PROPERTY_USER);
			password = properties.getProperty(PROPERTY_PASS);
		} catch (IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + CONFIG_FILE, e);
		}

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
		}

		try {
			this.connection = DriverManager.getConnection(url, username, password);
			this.url = url;
			this.username = username;
			this.password = password;
		} catch (SQLException e) {
			throw new DAOConfigurationException("Problème de connexion a la BDD : " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void restartConnection() throws DAOConfigurationException {
		try {
			this.connection = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (SQLException e) {
			throw new DAOConfigurationException("Problème de connexion a la BDD : " + e.getMessage());
		}
	}
}
