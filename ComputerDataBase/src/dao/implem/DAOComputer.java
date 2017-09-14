package dao.implem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.IDAOComputer;
import dao.exceptions.DAOException;
import model.Computer;

import static dao.DAOUtility.*;

public class DAOComputer implements IDAOComputer {
	private final static String REQUEST_CREATE = "INSERT INTO computer (id, name, introduced, discontinued, company_id) VALUES (NULL, ?, ?, ?, ?)";
	private final static String REQUEST_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private final static String REQUEST_DELETE = "DELETE FROM computer WHERE id=?";
	private final static String REQUEST_SELECT_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private final static String REQUEST_SELECT_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ?";
	private final static String REQUEST_SELECT_COMPANY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ?";
	private final static String REQUEST_SELECT_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";

	private DAOFactory factory;

	public DAOComputer(DAOFactory factory) {
		this.factory = factory;
	}

	private static Computer map(ResultSet resultSet) throws SQLException {
		Timestamp introduced = resultSet.getTimestamp("introduced");
		Timestamp discontinued = resultSet.getTimestamp("discontinued");
		Computer computer = new Computer();
		computer.setId(resultSet.getInt("id"));
		computer.setName(resultSet.getString("name"));
		if (introduced!=null) {
			computer.setIntroduced(introduced.toLocalDateTime());
		}
		if (discontinued!=null) {
			computer.setDiscontinued(discontinued.toLocalDateTime());
		}
		computer.setCompanyId(resultSet.getInt("company_id"));
		return computer;
	}

	@Override
	public void create(Computer computer) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, REQUEST_CREATE, true, computer.getName(),
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
	public void update(Computer computer) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, REQUEST_UPDATE, false, computer.getName(),
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
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, REQUEST_DELETE, false, computer.getId());
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

	private List<Computer> getComputers(String request, Object... param) throws DAOException {
		System.out.println("ok");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			/* Récupération d'une connection depuis la Factory */
			connection = factory.getConnection();
			preparedStatement = initPreparedStatement(connection, request, false, param);
			resultSet = preparedStatement.executeQuery();
			System.out.println("ok2");
			/* Parcours de la ligne de données de l'éventuel ResulSet retourné */
			while (resultSet.next()) {
				computers.add(map(resultSet));
				System.out.println("ok3");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			silentShutdown(resultSet, preparedStatement, connection);
		}

		return computers;
	}

	@Override
	public List<Computer> getFromName(String name) throws DAOException {
		return getComputers(REQUEST_SELECT_NAME, name);
	}

	@Override
	public List<Computer> getFromId(String id) throws DAOException {
		return getComputers(REQUEST_SELECT_ID, id);
	}

	@Override
	public List<Computer> getFromCompanyId(String id) throws DAOException {
		return getComputers(REQUEST_SELECT_COMPANY);
	}

	@Override
	public List<Computer> getAll() throws DAOException {
		return getComputers(REQUEST_SELECT_ALL);
	}
}