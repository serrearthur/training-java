package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Utility functions used in both {@link DAOCompany} and @{link {@link DAOComputer}.
 * @author aserre
 */
public final class DAOUtility {
    /**
     * Converts a {@link LocalDateTime} to a {@link Timestamp}.
     * @param ldt {@link LocalDateTime} to convert
     * @return converted {@link Timestamp}
     */
    public static Timestamp dateTimeToTimestamp(LocalDateTime ldt) {
        if (ldt != null) {
            return Timestamp.valueOf(ldt);
        } else {
            return null;
        }
    }

    /**
     * Converts a {@link Timestamp} to a {@link LocalDateTime}.
     * @param tsp {@link Timestamp} to convert
     * @return converted {@link LocalDateTime}
     */
    public static LocalDateTime timestampToDateTime(Timestamp tsp) {
        if (tsp != null) {
            return tsp.toLocalDateTime();
        } else {
            return null;
        }
    }

    /**
     * Silent shutdown of a {@link ResultSet}.
     * @param resultSet {@link ResultSet} to close
     */
    public static void silentShutdown(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Unable to close ResultSet : " + e.getMessage());
            }
        }
    }

    /**
     * Silent shutdown of a {@link Statement}.
     * @param statement {@link Statement} to close
     */
    public static void silentShutdown(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Unable to close Statement : " + e.getMessage());
            }
        }
    }

    /**
     * Silent shutdown of a {@link Connection}.
     * @param connection {@link Connection} to close
     */
    public static void silentShutdown(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Unable to close Connection : " + e.getMessage());
            }
        }
    }

    /**
     * Silent shutdown of a {@link Connection}, {@link Statement} and {@link Connection}.
     * @param resultSet {@link ResultSet} to close
     * @param statement {@link Statement} to close
     * @param connection {@link Connection} to close
     */
    public static void silentShutdown(ResultSet resultSet, Statement statement, Connection connection) {
        silentShutdown(resultSet);
        silentShutdown(statement);
        silentShutdown(connection);
    }

    /**
     * Creates a {@link PreparedStatement} taking in account the {@link Connection}, the request,
     * and formats all the objects passed as arguments.
     * @param connection {@link Connection} to the database
     * @param request request to be executed
     * @param returnGeneratedKeys <code>true</code> if the request needs generated keys,
     * <code>false</code> otherwise
     * @param objects dynamic range of arguments
     * @return the resulting {@link PreparedStatement}
     * @throws SQLException thrown during the creation of the {@link PreparedStatement}
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