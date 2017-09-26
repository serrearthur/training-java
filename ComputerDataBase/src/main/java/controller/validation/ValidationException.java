package controller.validation;

/**
 * Custom exception to handle validation errors.
 * @author aserre
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param message custom message
     * @see RuntimeException#RuntimeException(String)
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message custom message
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param cause cause for the exception
     * @see RuntimeException#RuntimeException(String)
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
