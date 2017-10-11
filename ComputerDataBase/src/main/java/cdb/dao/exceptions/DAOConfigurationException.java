package cdb.dao.exceptions;

/**
 * Custom exception to handle DAO configuration errors.
 * @author aserre
 */
public class DAOConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param message custom message
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message custom message
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }
}