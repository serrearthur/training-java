package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.exceptions.DAOException;
import model.Computer;

import static dao.DAOUtility.*;

public class DAOComputer implements IDAOComputer {
	private DAOFactory factory;

	public DAOComputer(DAOFactory factory) {
		this.factory = factory;
	}

	private static Computer map(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer();
		computer.setId(resultSet.getInt("id"));
		computer.setName(resultSet.getString("name"));
		computer.setIntroduced(resultSet.getDate("introduced"));
		computer.setDiscontinued(resultSet.getDate("discontinued"));
		computer.setCompanyId(resultSet.getInt("company_id"));
		return computer;
	}

	@Override
	public void create(Computer computer) throws DAOException {
		String request = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, true, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId());
			int statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (statut == 0) {
				throw new DAOException("Échec de la création de l'ordinateur, aucune ligne ajoutée dans la table.");
			}
			/* Récupération de l'id auto-généré par la requête d'insertion */
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
				computer.setId(resultSet.getInt(1));
			} else {
				throw new DAOException("Échec de la création de l'ordinateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public Computer getFromName(String name) throws DAOException {
		String request = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, name);
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				computer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}

		return computer;
	}

	@Override
	public Computer getFromId(Integer id) throws DAOException {
		String request = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, id);
			resultSet = preparedStatement.executeQuery();

			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			if (resultSet.next()) {
				computer = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}

		return computer;
	}

	@Override
	public void update(Computer computer) throws DAOException {
		String request = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, computer.getName(),
					computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId(), computer.getId());
			int status = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (status == 0) {
				throw new DAOException("Échec de la mise a jour de l'ordinateur, aucune ligne modifiée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}

	@Override
	public void delete(Computer computer) throws DAOException {
		String request = "DELETE FROM computer WHERE id=?";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, computer.getId());
			int status = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */
			if (status == 0) {
				throw new DAOException("Échec de la suppression de l'ordinateur, aucune ligne modifiée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}
	}
}