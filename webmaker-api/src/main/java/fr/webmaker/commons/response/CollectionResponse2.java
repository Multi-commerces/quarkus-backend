package fr.webmaker.commons.response;

import java.util.List;

import fr.webmaker.commons.PagingData;
import lombok.Data;

/**
 * Permet d'inclure la pagination dans a réponse
 * @author julien ILARI
 *
 * @param <M>
 * @param <I>
 */
@Data
public class CollectionResponse2<S extends SingleResponse<?, ?>> {

	protected PagingData _paging;

	protected List<S> _embedded;

	public CollectionResponse2() {
		super();
	}
	
	public CollectionResponse2(final List<S> _embedded) {
		super();
		this._embedded = _embedded;
	}
	
	

}
