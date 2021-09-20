package fr.commerces.commons.exceptions.crud;

/**
 * Permet de forger une exception dans le cas d'une demande de mise en jour en
 * échec pour cause objet introuvable.
 * 
 * @author julien ILARI
 *
 */
public class NotFoundUpdateException extends NotFoundException {

	private static final long serialVersionUID = 1L;

	public NotFoundUpdateException(Object identifier) {
		super(CrudExceptionType.UPDATE, identifier);

	}

}
