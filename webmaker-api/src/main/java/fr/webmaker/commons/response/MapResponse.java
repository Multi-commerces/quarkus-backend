package fr.webmaker.commons.response;

import java.util.Map;

import fr.webmaker.commons.PagingData;
import fr.webmaker.commons.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapResponse<I extends Identifier<?>, M> {

	protected PagingData _paging;

	protected Map<I, M> dataByIdentifier;
	
	public static <I extends Identifier<?>, M> MapResponse<?, ?> newInstance()
	{
		return new MapResponse<Identifier<?>, M>();
	}

}
