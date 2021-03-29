package fr.commerces.services.products.ressources.seo;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import fr.commerces.services._transverse.GenericResponse;
import fr.commerces.services.products.data.ProductSeoData;


@RequestScoped
public class ProductSeoResource implements ProductSeoApi {

	@Override
	public GenericResponse<ProductSeoData, Long> getProductSeoById(Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<GenericResponse<ProductSeoData, Long>> getProductSEOs(String languageCode, Long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response createProductSeo(@NotNull @Valid ProductSeoData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateProductSeo(String languageCode, Long productId, @NotNull @Valid ProductSeoData seo) {
		// TODO Auto-generated method stub
		return null;
	}

	

}