package fr.webmaker.restfull.model;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class IncludeRequest {

	@QueryParam("includeMeta")
	@DefaultValue("false")
	@Parameter(description = "Inclure dans la réponse les méta-données")
	private boolean includeMeta;

	@QueryParam("includeRelations")
	@DefaultValue("false")
	@Parameter(description = "Inclure dans la réponse les attributs des relations de la ressources")
	private boolean includeRelations;

}