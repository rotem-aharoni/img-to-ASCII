package errors;

/**
 * An exception thrown when an error occurs during algorithm execution.
 * This exception is typically thrown when an algorithm encounters an unexpected condition or fails to execute
 * properly.
 */
public class AlgorithmException extends Exception {

    /**
     * Constructs a new AlgorithmException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public AlgorithmException(String message) {
        super(message);
    }
}
