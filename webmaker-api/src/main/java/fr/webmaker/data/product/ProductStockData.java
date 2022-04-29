package fr.webmaker.data.product;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value= "stock", path = "/products/{id}/stock")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductStockData extends BaseResource {

	@Schema(description = "Quantité en stock.")
	private JsonNullable<Integer> quantity = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasQuantity() {
		return quantity != null && quantity.isPresent();
	}

	@Schema(description = "Quantité minimum pour la vente.", implementation = Integer.class)
	private JsonNullable<Integer> minimumQuantityForSale = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasMinimumQuantityForSale() {
		return minimumQuantityForSale != null && minimumQuantityForSale.isPresent();
	}

	@Schema(description = "Emplacement du stock.", implementation = String.class)
	private JsonNullable<String> stockLocation = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasStockLocation() {
		return stockLocation != null && stockLocation.isPresent();
	}

	@Schema(description = "Niveau du stock d'alerte.", implementation = Integer.class)
	private JsonNullable<Integer> stockLowLevel = JsonNullable.undefined();
	
	@JsonIgnore
	public boolean hasStockLowLevel() {
		return stockLowLevel != null && stockLowLevel.isPresent();
	}

	@Schema(description = "Permet de recevoir un e-mail lorsque la quantité est inférieure ou égale à stockLowLevel.", implementation = Boolean.class)
	private JsonNullable<Boolean> stockLowLevelAlert = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasStockLowLevelAlert() {
		return stockLowLevelAlert != null && stockLowLevelAlert.isPresent();
	}

	@Schema(description = "Permet de paramétrer une adresse e-mail spécifique.", implementation = String.class)
	private JsonNullable<String> stockLowLevelSpecificEmail = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasStockLowLevelSpecificEmail() {
		return stockLowLevelSpecificEmail != null && stockLowLevelSpecificEmail.isPresent();
	}

	/*
	 * Availability preferences Comportement en cas de rupture de stock
	 */

}
