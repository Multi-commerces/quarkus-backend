package fr.webmaker.restfull.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageRequest {

	@QueryParam("page")
	@DefaultValue("1")
	@Positive
	@Max(999999999)
	@Min(1)
	@Parameter(description = "Page cible (> 1 et < 999999999)")
	private int page;

	@QueryParam("size")
	@DefaultValue("20")
	@Positive
	@Min(1)
	@Parameter(description = "Taille de la page cible (> 1)")
	private int size;
	

}