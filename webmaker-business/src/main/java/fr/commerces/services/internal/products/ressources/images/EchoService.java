package fr.commerces.services.internal.products.ressources.images;

import java.net.URI;
import java.util.Locale;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import fr.commerces.services.internal.products.entity.ProductImage;
import lombok.Getter;

@Path("/products/{productId}/images")
@Tag(name = "Resource Produits - Images", description = "Resource de gestion des produits")
public class EchoService {

	@Context @Getter
	protected UriInfo uriInfo;
	
    @POST
    public Response echo(@PathParam(value = "productId") Long productId, ProductImage image) throws Exception {
    	
    	
    	URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(productId)).build();
		return Response.created(uri).language(Locale.FRENCH).build();
    }
}