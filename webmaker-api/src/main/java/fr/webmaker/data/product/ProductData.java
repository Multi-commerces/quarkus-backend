package fr.webmaker.data.product;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Type(value = "product", path = "/products")
public class ProductData extends BaseResource {

	@Schema(description = "Référence unique du produit", example = "REF10000001", implementation = String.class)
	@JsonProperty(value = "reference")
	private JsonNullable<String> reference;

	@JsonIgnore
	public boolean hasReference() {
		return reference != null && reference.isPresent();
	}

	@Schema(description = "Quantité disponible", example = "32", implementation = Long.class)
	@JsonProperty(value = "quantity")
	private JsonNullable<Long> quantity = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasQuantity() {
		return quantity != null && quantity.isPresent();
	}

	@Schema(description = "Prix unitaire (Hors Taxe)", example = "13", implementation = Double.class)
	@JsonProperty(value = "priceHT")
	private JsonNullable<Double> priceHT = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasPriceHT() {
		return priceHT != null && priceHT.isPresent();
	}

	@Schema(description = "Taxe appliquée", example = "5", implementation = Double.class)
	@JsonProperty(value = "taxeRule")
	private JsonNullable<Double> taxeRule = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasTaxeRule() {
		return taxeRule != null && taxeRule.isPresent();
	}

}