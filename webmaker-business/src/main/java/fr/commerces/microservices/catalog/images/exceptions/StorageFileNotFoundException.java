package fr.commerces.microservices.catalog.images.exceptions;
public class StorageFileNotFoundException extends StorageException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}