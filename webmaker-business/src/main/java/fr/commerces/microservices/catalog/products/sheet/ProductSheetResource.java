package fr.commerces.microservices.catalog.products.sheet;

import static fr.commerces.commons.resources.ConstApi.CODE200GET;
import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.products.entity.Product;
import fr.commerces.microservices.catalog.products.entity.ProductLang;
import fr.commerces.microservices.catalog.products.self.ProductResourceApi.ShemaCollectionData;
import fr.webmaker.data.DualData;
import fr.webmaker.data.product.ProductSheetData;
import fr.webmaker.exception.crud.NotFoundException;

@Path("/product-tranlated-sheets")
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Produit - Fiche Produit", description = "Ressource pour la gestion des fiches produit")
public class ProductSheetResource extends JsonApiResource<ProductSheetData> {
	
	@Inject
	ProductSheetMapper mapper;
	
	public ProductSheetResource() {
		super(ProductSheetData.class, ProductSheetData.class);
	}

	@GET
	@Path("/")
	@Operation(operationId = "getProductSheet", 
		summary = "Recherche la fiche des produits", description = CODE200GET)
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = CODE200GET, 
					content = @Content(mediaType = MEDIA_JSON_API, 
					schema = @Schema(implementation = ShemaCollectionData.class))), })
	@Transactional
	public Response getProductSheet(
			@Schema(description = "Langue de traduction", required = true) 
			@QueryParam("languageCode") 
			@NotNull LanguageCode languageCode) throws InterruptedException {
		Thread.sleep(2000);
		
		if(!List.of(LanguageCode.fr, LanguageCode.an, LanguageCode.en).contains(languageCode))
		{
			throw new BadRequestException("languageCode invalide (valeur possible 'fr'|'an')");
		}
		
		var entities = ProductLang.findByLanguageCode(languageCode).list();
		var data = mapper.wrap(entities);

		return writeResponse(data,List.of());
	}
	
	@GET
	@Path("/{productId}")
	@Transactional
	public Response getProductSheetById(@PathParam("productId") long productId) {
		return writeResponse(
				mapper.wrap(
					ProductLang.findByLanguageCode(LanguageCode.fr)
						.stream()
						.filter(entity -> productId == entity.getId())
						.findAny()
						.orElseThrow(() -> new NotFoundException(productId))
				));
	}

	@PATCH
	@Path("/{productId}")
	@Transactional
	public Response patchProductSheet(@PathParam("productId") long productId, byte[] flux) {
		var data = (ProductSheetData) readData(flux);
		
		Product product = mapper.unwrap(data, Product.findByIdOrElseThrow(productId));
		ProductLang lang = product.getProductLangs()
			.stream()
			.filter(o -> LanguageCode.fr == o.getLang())
			.findAny()
			.get();
		
		lang = mapper.unwrap(data, lang);		
		
		// Réponse API
		return writeResponse(mapper.wrap(lang));
	}

	@POST
	@Path("/")
	@Transactional
	public Response postProductSheet(byte[] flux) {
		var data = (ProductSheetData) readData(flux);
		
		// Lecture et mapping des données
		final DualData<Product, ProductLang> entities = mapper.unwrap(data);	
		var product = entities.getDataA();
		var lang = entities.getDataB();
		
		lang.setProductLangPK(null, data.getLanguageCode());
		
		// Ajout dans les deux sens
		lang.setProduct(product);
		product.getProductLangs().add(lang);
		
		// Persistance des données du produit et traduction
		product.persist();
		
		// Réponse API
		var response = mapper.wrap(lang);
		return writeResponseCreated(response, response.getId());
	}
	
	

}