package fr.webmaker.exception.crud;

/**
 * Permet de forger une exception dans le cas d'une demande de suppression en
 * échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundDeleteException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public NotFoundDeleteException(Object identifier) {
		super(CrudExceptionType.DELETE, identifier);

	}

}
