package fr.webmaker.exception.crud;

/**
 * Permet de forger une exception dans le cas d'une demande crud en échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	private final String identifierDebug;

	public NotFoundException()
	{
		super("Ressource introuvable");
		this.identifierDebug = "null";
	}

	public NotFoundException(Object identifier)
	{
		this(CrudExceptionType.READ, identifier);
	}

	public NotFoundException(CrudExceptionType crudType, Object identifier)
	{
		super(String.format("Opération de "
			+ crudType == null ? CrudExceptionType.READ.getSummary() : crudType.getSummary()
			+ " impossible car l'occurence portant l'identifiant "
			+ identifier == null
				? "(null)"
				: identifier.toString()
					+ " est introuvable."));
		String id = identifier == null ? "identifier non fourni (null)" : identifier.toString();
		identifierDebug = String.format("%s", id);
	}

	public NotFoundException(String message, Throwable cause)
	{
		super(message, cause);
		this.identifierDebug = "null";
	}

	public String getIdentifierDebug()
	{
		return identifierDebug;
	}

}
