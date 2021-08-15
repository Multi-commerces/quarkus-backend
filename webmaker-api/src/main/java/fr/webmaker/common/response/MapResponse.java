package fr.webmaker.common.response;

import java.util.Map;

import fr.webmaker.common.Identifier;
import fr.webmaker.common.PagingData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapResponse<I extends Identifier<?>, M> {

	protected PagingData _paging;

	protected Map<I, M> _dataByIdentifier;
	
	public static <I extends Identifier<?>, M> MapResponse<?, ?> newInstance()
	{
		return new MapResponse<Identifier<?>, M>();
	}

}
