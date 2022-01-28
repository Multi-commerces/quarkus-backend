package fr.commerces.microservices.catalog.categories.lang;

import static fr.commerces.commons.resources.ConstApi.MEDIA_JSON_API;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.commons.resources.ConstApi;
import fr.commerces.commons.resources.JsonApiResource;
import fr.commerces.microservices.catalog.categories.basic.CategoryManager;
import fr.commerces.microservices.catalog.categories.data.ShemaCategoryData;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryRelationData;

@Path(CategoryLangResource.PATH)
@Produces(MEDIA_JSON_API)
@Consumes(MEDIA_JSON_API)
@Tag(name = "Ressource Catégories - Langs", description = "Ressource pour la gestion des catégories")
public class CategoryLangResource extends JsonApiResource<CategoryLangData> {

	public static final String PATH = "/categories/{categoryId}";

	@Inject
	CategoryManager manager;

	public CategoryLangResource() {
		super(CategoryLangData.class, CategoryLangRelationData.class);
	}
	
	@Override
	public List<Class<?>> getClazz() {
		return List.of(CategoryData.class, CategoryRelationData.class);
	}

	@Operation(operationId = "getCategoryLangs", summary = "Recherche les produits de la catégorie", description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès.", 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCategoryData.class))) })
	@GET
	@Path("/langs")
	public byte[] getCategoryLangs(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("categoryId") @NotNull Long categoryId)
			throws JsonProcessingException, IllegalAccessException, DocumentSerializationException {
		final List<CategoryLangRelationData> value = manager.findCategoryLangCompositeDataByCategoryId(categoryId);

		return converter
				.writeDocumentCollection(new JSONAPIDocument<List<CategoryLangRelationData>>(value, objectMapper));
	}

	@Operation(operationId = "getCategoryRelationshipsLang", summary = "Recherche les produits de la catégorie", description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès.", 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCategoryData.class))) })
	@GET
	@Path("/relationships/langs")
	public Response getCategoryRelationshipsLang(
			@Parameter(description = "Identifiant de la catégorie") @PathParam("id") @NotNull @NotNull Long categoryId)
			throws DocumentSerializationException {
		final List<CategoryLangBaseData> relationships = manager.findRelationshipsLangs(categoryId);
		return writeRelationships(relationships);
	}

	@Operation(operationId = "getCategoryLang", 
			summary = "Recherche les produits de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", 
					description = "[OK] - Opération de recherche effectuée avec succès.", 
					content = @Content(mediaType = ConstApi.MEDIA_JSON_API, 
					schema = @Schema(title = "category", type = SchemaType.OBJECT, implementation = ShemaCategoryData.class))) })
	@Path("/langs/{languageCode}")
	@GET
	public Response getCategoryLang(
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") 
			@NotNull Long categoryId,
			@Parameter(description = "Langue de la catégorie recherchée") 
			@PathParam("languageCode") 
			@NotNull LanguageCode languageCode)
			throws DocumentSerializationException {
		final CategoryLangRelationData data = manager.findCategoryLangCompositeData(categoryId, languageCode);
		
		return writeJsonApiResponse(data);
	}

}