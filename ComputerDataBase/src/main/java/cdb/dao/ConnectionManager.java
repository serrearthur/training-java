package cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cdb.dao.exceptions.DAOException;

/**
 * Class containing the methods to configure a connection to the database.
 * @author aserre
 */
@Component
public class ConnectionManager {
    private static final ThreadLocal<Connection> THREAD_CONNECTION =
            new ThreadLocal<Connection>() {
        @Override public Connection initialValue() {
            return null;
        }
    };

    private DataSource dataSource;

    /**
     * Contructor for a new ConnectionManager.
     * @param ds DataSource used
     */
    @Autowired
    private ConnectionManager(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Gets a connection from the pool or creates a new one.
     * @return a connection taken from the pool
     * @throws DAOException thrown if Hikari is unable to connect to the database
     */
    public Connection getConnection() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() == null) {
                THREAD_CONNECTION.set(this.dataSource.getConnection());
            }
            return THREAD_CONNECTION.get();
        } catch (SQLException e) {
            throw new DAOException("Unable to connect to database : " + e.getMessage());
        }
    }

    /**
     * Closes the current connection.
     * @throws DAOException thrown when an error is encountered when closing the connection
     */
    public void closeConnection() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().close();
                THREAD_CONNECTION.remove();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while closing the connection : " + e.getMessage());
        }
    }

    /**
     * Commits the current transaction.
     * @throws DAOException thrown when an error is encountered when commiting
     */
    public void commit() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().commit();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while commiting the transaction : " + e.getMessage());
        }
    }

    /**
     * Commits the current transaction.
     * @throws DAOException thrown when an error is encountered during the rollback
     */
    public void rollback() throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().rollback();
            }
        } catch (SQLException e) {
            throw new DAOException("Error while rolling back the transaction : " + e.getMessage());
        }
    }

    /**
     * Change the value for autocommit.
     * @param flag new value for autocommit
     * @throws DAOException thrown when an error is encountered during the operation
     */
    public void setAutoCommit(boolean flag) throws DAOException {
        try {
            if (THREAD_CONNECTION.get() != null) {
                THREAD_CONNECTION.get().setAutoCommit(flag);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while updating the autocommit : " + e.getMessage());
        }
    }

    /**
     * Gets value for autocommit.
     * @return the value of the autocommit flag
     * @throws DAOException thrown when an error is encountered during the operation
     */
    public boolean getAutoCommit() throws DAOException {
        try {
            return THREAD_CONNECTION.get().getAutoCommit();
        } catch (SQLException | NullPointerException e) {
            throw new DAOException("Error while getting the autocommit value : " + e.getMessage());
        }
    }
}