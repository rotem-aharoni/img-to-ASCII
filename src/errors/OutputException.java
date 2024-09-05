package errors;

/**
 * An exception representing an error related to output handling.
 * This exception is used to indicate errors that occur during output handling or formatting operations.
 */
public class OutputException extends Exception {

    /**
     * Constructs a new OutputException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public OutputException(String message) {
        super(message);
    }
}
