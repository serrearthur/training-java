package dao;

import java.sql.Connection;
import java.sql.SQLException;

import dao.exceptions.DAOConfigurationException;
import dao.impl.DAOCompany;
import dao.impl.DAOComputer;

public final class DAOFactory {
	private ConnectionManager manager;
	private IDAOComputer daoComputer;
	private IDAOCompany daoCompany;

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
		try {
			this.manager = new ConnectionManager();
			this.daoComputer = new DAOComputer(this);
			this.daoCompany = new DAOCompany(this);
		} catch (DAOConfigurationException e) {
			throw e;
		}
	}

	/**
	 * Méthode chargée de fournir une connection à la base de données
	 * 
	 * @throws SQLException
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
