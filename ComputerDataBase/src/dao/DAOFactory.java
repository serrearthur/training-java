package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.exceptions.DAOConfigurationException;
import dao.implem.DAOCompany;
import dao.implem.DAOComputer;

public class DAOFactory {
	private static final String CONFIG_FILE = "dao/dao.properties";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USER = "username";
	private static final String PROPERTY_PASS = "password";

	private String url;
	private String username;
	private String password;
	private IDAOComputer daoComputer;
	private IDAOCompany daoCompany;

	// implémentation du singleton
	/** Holder */
	private static class SingletonHolder {
		/** Instance unique non préinitialisée */
		private final static DAOFactory instance = new DAOFactory();
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static DAOFactory getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Méthode chargée de récupérer les informations de connection à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
	private DAOFactory() throws DAOConfigurationException {
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

		this.url = url;
		this.username = username;
		this.password = password;
		this.daoComputer = new DAOComputer(this);
		this.daoCompany = new DAOCompany(this);
	}

	/**
	 * Méthode chargée de fournir une connection à la base de données
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public IDAOComputer getComputerDao() {
		return daoComputer;
	}

	public IDAOCompany getCompanyDao() {
		return daoCompany;
	}
}