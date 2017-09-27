package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
     * Creates a {@link PreparedStatement} taking in account the {@link Connection}, the request,
     * and formats all the objects passed as arguments.
     * @param connection {@link Connection} to the database
     * @param request request to be executed
     * @param returnGeneratedKeys <code>true</code> if the request needs generated keys,
     * <code>false</code> otherwise
     * @param params dynamic range of arguments
     * @return the resulting {@link PreparedStatement}
     * @throws SQLException thrown during the creation of the {@link PreparedStatement}
     */
    public static PreparedStatement initPreparedStatement(Connection connection, String request,
            boolean returnGeneratedKeys, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(request,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }
}