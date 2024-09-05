package errors;

/**
 * An exception thrown when an error occurs while attempting to add elements.
 * This exception is typically thrown in situations where an attempt to add elements
 * fails due to incorrect format or other related issues.
 */
public class AddException extends Exception {

    /**
     * Constructs a new AddException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public AddException(String message) {
        super(message);
    }
}
