package fr.commerces.microservices.catalog.products;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.commons.data.BaseResource;
import fr.webmaker.commons.data.ImageData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Représentation d'un produit
 * 
 * @author Julien ILARI
 *
 */
@Type(value = "product", path = "/products")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductData extends BaseResource {

	/**
	 * Référence (unique) produit
	 */
	@NotNull
	private String reference;

	private ImageData imageCover = new ImageData();

	private List<ImageData> images = new ArrayList<ImageData>();



}