package fr.webmaker.exception.crud;

/**
 * Permet de forger une exception dans le cas d'une demande crud en
 * échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String identifierDebug;

	public NotFoundException()
	{
		super("Ressource introuvable");
	}
			
	public NotFoundException(Object identifier) {
		this(CrudExceptionType.READ, identifier);
	}

	public NotFoundException(CrudExceptionType crudType, Object identifier) {
		super(String.format("Opération de " + crudType.getSummary() + " impossible car l'occurence portant l'identifiant " + identifier.toString() + " à "
				+ " est introuvable."));

		identifierDebug = String.format("%s",
				identifier != null ? identifier.toString() : "identifier non fourni (null)");
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getIdentifierDebug() {
		return identifierDebug;
	}

}
