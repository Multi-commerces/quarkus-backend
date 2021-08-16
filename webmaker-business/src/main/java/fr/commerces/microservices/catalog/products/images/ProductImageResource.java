package fr.commerces.microservices.catalog.products.images;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import fr.commerces.microservices.storage.FileSystemStorageService;

@Path("/products/{productId}/images")
@Tag(name = "Resource Produits - Images", description = "Resource de gestion des image du produit")
public class ProductImageResource {
	
	@Inject
	FileSystemStorageService storageService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/")
	public String form(@PathParam(value = "productId") @NotNull Long productId, @MultipartForm ProductImageForm formData) throws IOException {
		return storageService.store(String.valueOf(productId), formData.getFileName(), formData.getContent().readAllBytes());
	}
}