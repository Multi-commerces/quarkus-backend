package fr.commerces.services._transverse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ResponseLinkData<I> {

	private I id;

	@JsonProperty("_rel_self")
	private LinkData self;

	@JsonProperty("_rel_list")
	private LinkData list;

	public ResponseLinkData(final I id, final LinkData self, final LinkData list) {
		super();
		this.id = id;
		this.self = self;
		this.list = list;
	}

}
