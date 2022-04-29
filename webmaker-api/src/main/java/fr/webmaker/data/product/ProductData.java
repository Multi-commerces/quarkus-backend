package fr.webmaker.data.product;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Type(value = "product", path = "/products")
public class ProductData extends BaseResource {

	@NotBlank
	@Schema(description = "Référence unique du produit", example = "REF10000001")
	@JsonProperty(value = "reference", index = 1)
	private JsonNullable<String> reference = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasReference() {
		return reference != null && reference.isPresent();
	}
	
	@NotNull
	@Min(0) @Max(9999)
	@JsonProperty(value = "quantity", index = 2)
	@Schema(description = "Quantité disponible", example = "32", implementation = Long.class)
	private JsonNullable<Long> quantity = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasQuantity() {
		return quantity != null && quantity.isPresent();
	}
	
	@NotNull 
	@Min(0) @Max(10000)
	@Schema(description = "Montant (Hors Taxe)", example = "13")
	@JsonProperty(value = "amount", index = 3)
	private JsonNullable<Double> priceHT = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasPriceHT() {
		return priceHT != null && priceHT.isPresent();
	}

	@Schema(description = "Taxe appliquée", example = "5")
	@JsonProperty(value = "taxeRule", index =4)
	private JsonNullable<Double> taxeRule = JsonNullable.undefined();

	@JsonIgnore
	public boolean hasTaxeRule() {
		return taxeRule != null && taxeRule.isPresent();
	}

}