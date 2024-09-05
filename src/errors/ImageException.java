package errors;

/**
 * An exception representing an error related to image processing.
 * This exception is used to indicate errors that occur during image handling or processing operations.
 */
public class ImageException extends Exception {

    /**
     * Constructs a new ImageException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ImageException(String message) {
        super(message);
    }
}
