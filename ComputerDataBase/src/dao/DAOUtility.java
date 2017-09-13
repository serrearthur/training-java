package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtility {

	/**
	 * Constructeur caché par défaut (car c'est une classe finale utilitaire,
	 * contenant uniquement des méthode appelées de manière statique)
	 */
	private DAOUtility() {
	}

	/**
	 * Fermeture silencieuse du resultset
	 */
	public static void silentShutdown(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture silencieuse du statement
	 */
	public static void silentShutdown(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermeture silencieuse de la connection
	 */
	public static void silentShutdown(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture de la connection : " + e.getMessage());
			}
		}
	}

	/**
	 * Fermetures silencieuses du statement et de la connection
	 */
	public static void silentShutdown(Statement statement, Connection connection) {
		silentShutdown(statement);
		silentShutdown(connection);
	}

	/**
	 * Fermetures silencieuses du resultset, du statement et de la connection
	 */
	public static void silentShutdown(ResultSet resultSet, Statement statement, Connection connection) {
		silentShutdown(resultSet);
		silentShutdown(statement);
		silentShutdown(connection);
	}

	/**
	 * Initialise la requête préparée basée sur la connection passée en argument,
	 * avec la requête SQL et les objets donnés.
	 */
	public static PreparedStatement initPreparedStatement(Connection connection, String request,
			boolean returnGeneratedKeys, Object... objects) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(request,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		for (int i = 0; i < objects.length; i++) {
			preparedStatement.setObject(i + 1, objects[i]);
		}
		return preparedStatement;
	}
}