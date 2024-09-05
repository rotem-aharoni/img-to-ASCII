package errors;

/**
 * An exception representing a general error condition.
 * This exception is used when a generic error occurs and no specific exception type is appropriate.
 */
public class GeneralException extends Exception {

    /**
     * Constructs a new GeneralException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public GeneralException(String message) {
        super(message);
    }
}
