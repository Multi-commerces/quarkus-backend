package fr.webmaker.exception.crud;

/**
 * Permet de forger une exception dans le cas d'une demande de création en
 * échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundCreateException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public NotFoundCreateException(Object identifier) {
		super(CrudExceptionType.CREATE, identifier);

	}

}
