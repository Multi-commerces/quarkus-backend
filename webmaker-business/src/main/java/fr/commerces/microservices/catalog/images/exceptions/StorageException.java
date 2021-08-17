package fr.commerces.microservices.catalog.images.exceptions;
public class StorageException extends RuntimeException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}