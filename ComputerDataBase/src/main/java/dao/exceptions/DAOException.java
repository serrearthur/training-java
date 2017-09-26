package dao.exceptions;

/**
 * Custom exception to handle DAO errors.
 * @author aserre
 */
public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param message custom message
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message custom message
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}