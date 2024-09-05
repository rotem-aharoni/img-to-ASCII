package errors;

/**
 * An exception representing an error related to removal operations.
 * This exception is used to indicate errors that occur during removal operations.
 */
public class RemoveException extends Exception {

    /**
     * Constructs a new RemoveException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public RemoveException(String message) {
        super(message);
    }
}
