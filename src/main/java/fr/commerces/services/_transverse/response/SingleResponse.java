package fr.commerces.services._transverse.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.services._transverse.data.LinkData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<M, I> {
	
	@JsonProperty("id")
	protected I id;
	
	protected LanguageCode languageCode;

	@JsonProperty("data")
	protected M data;

	/**
	 * Le principe HATEOAS introduit tout simplement des transitions possibles entre
	 * les différents états d’une même ressource, ainsi qu’entre les ressources
	 * elles-mêmes.
	 */
	@JsonProperty("_links")
	protected List<LinkData> links = new ArrayList<>();



}
