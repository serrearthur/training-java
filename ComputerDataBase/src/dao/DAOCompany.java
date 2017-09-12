package dao;

import static dao.DAOUtility.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.exceptions.DAOException;
import model.Company;

public class DAOCompany implements IDAOCompany {
	private DAOFactory factory;

	public DAOCompany(DAOFactory factory) {
		this.factory = factory;
	}

	private static Company map(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt("id"));
		company.setName(resultSet.getString("name"));
		return company;
	}

	@Override
	public void create(Company company) throws DAOException {
		String request = "INSERT INTO company (id, name) VALUES (NULL, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, true, company.getName());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException("Échec de la création de l'entreprise, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				company.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Échec de la création de l'entreprise en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public Company getFromName(String name) throws DAOException {
		String request = "SELECT id, name FROM company WHERE name = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, name);
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				company = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}

		return company;
	}

	@Override
	public Company getFromId(Integer id) throws DAOException {
		String request = "SELECT id, name FROM company WHERE id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, id);
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				company = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}

		return company;
	}

	@Override
	public void update(Company company) throws DAOException {
		String request = "UPDATE company SET name=? WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, company.getName(), company.getId());
			int status = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (status == 0) {
				throw new DAOException("Échec de la mise a jour de l'entreprise, aucune ligne modifiée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public void delete(Company company) throws DAOException {
		String request = "DELETE FROM company WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, company.getId());
			int status = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (status == 0) {
				throw new DAOException("Échec de la suppression de l'entreprise, aucune ligne modifiée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}
}
