package fr.commerces.microservices.catalog.products.relations.images;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import fr.commerces.microservices.catalog.images.data.ImageMultipartBody;
import fr.commerces.microservices.catalog.images.entities.ShopImageDimentionConfig;
import fr.commerces.microservices.catalog.images.enums.ShopImageDirectoryType;
import fr.commerces.microservices.catalog.images.modules.CatalogStorageManager;

@Path("/products/{productId}/images")
@Tag(name = "Ressource Produits - Images", description = "Ressource de gestion des image du produit")
public class ProductImageResource {

	@Inject
	CatalogStorageManager storageService;

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/")
	public String form(@PathParam(value = "productId") @NotNull Long productId,
			@MultipartForm ImageMultipartBody formData) throws IOException {
		return storageService.upload(ShopImageDirectoryType.PRODUCTS, productId, formData.getFile(), true, true);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	@Transactional
	public List<ShopImageDimentionConfig> getProductImages(@PathParam(value = "productId") @NotNull Long productId)
			throws IOException {
		return ShopImageDimentionConfig.<ShopImageDimentionConfig>findAll().list();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/testSynchro")
	public String form(@PathParam(value = "productId") @NotNull Long productId) throws IOException {

		storageService.synchroProduct();

		return "ok";

	}

}