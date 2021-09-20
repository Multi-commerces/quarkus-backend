package fr.commerces.microservices.catalog.categories;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.commons.data.LinkData;
import fr.webmaker.commons.data.SingleCompositeData;
import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.commons.identifier.LongID;
import fr.webmaker.commons.response.CollectionResponse;
import fr.webmaker.microservices.catalog.categories.data.CategoryHierarchyData;
import fr.webmaker.microservices.catalog.categories.data.CategoryLangData;
import fr.webmaker.microservices.catalog.categories.response.CategoryHierarchySingleResponse;
import fr.webmaker.microservices.catalog.products.data.ProductLangData;
import lombok.Getter;

@Tag(name = "Ressource Catégories", description = "Ressource pour la gestion des catégories")
@Path("/categories")
@RequestScoped
public class CategoryResource {

	@Context @Getter
	UriInfo uriInfo;
	
	@Inject
	CategoryManager manager;
	
	@Path("/hierarchy")
	@GET
	public CollectionResponse<CategoryLangData, LangID> getCategories(
			@Parameter(description = "Langue des catégories recherchées") 
			@QueryParam("languageCode") @NotNull String languageCode,
			@Parameter(description = "Inclure les sous-catégories") 
			@QueryParam("includeSubCategories") Boolean includeSubCategories) 
	{
		
		// Recherche des catégories de façon Hierarchique
		final List<CategoryHierarchyData> values = manager.findCategoryHierarchy(LanguageCode.getByCode(languageCode, false));
		
		// Construction included => CategoryHierarchyData
		final List<SingleCompositeData<CategoryHierarchyData, LongID>> included = new ArrayList<>();
		for (CategoryHierarchyData categoryHierarchyData : values) {
			var list = categoryHierarchyData.getSubCategories().stream().map(value -> {
			return SingleCompositeData.<CategoryHierarchyData, LongID>builder()
				.identifier(value.getIdentifier())
				.data(value)
				.withLinks(Arrays.asList(new LinkData("/categories/hierarchy?categoryId=" + value.getIdentifier().getId())))
				.build();
			}).collect(Collectors.toList());
			
			included.addAll(list);
		}
		

		
		// Collection SingleResponse
		final List<SingleCompositeData<CategoryLangData, LangID>> data = values.stream()
				.map(categoryHierarchy -> new CategoryHierarchySingleResponse(categoryHierarchy))
				.collect(Collectors.toList());
		
		var col = CollectionResponse.<CategoryLangData, LangID>builder(uriInfo)
			.collection(data)
			.included("categoryHierarchy", included)
			.build();
		

		
		return col;
	}
	
	@Path("/")
	@POST
	public Response post(
			@Parameter(description = "Langue des catégories recherchées") 
			@PathParam("languageCode") @NotNull String languageCode,
			@NotNull @Valid  CategoryLangData data) 
	{	
		var newId = manager.createCategory(null, data);		
		
		final URI uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(newId)).build();
		return Response.created(uri).language(Locale.FRENCH).build();
	}
	
	@Path("/{id}")
	@PUT
	public Response put(
			@Parameter(description = "Langue de la catégaroie") 
			@PathParam("languageCode") @NotNull String languageCode,
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") @NotNull Long categoryId,
			@NotNull @Valid CategoryLangData data) 
	{	
		LanguageCode lang = LanguageCode.getByCode(languageCode, false);
		manager.update(categoryId, lang, data);		
		return Response.noContent().build();
	}
	
	@Operation(
			operationId = "getCategoryProducts", 
			summary = "Recherche les produits de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "404", 
					description = "[NOK] - Aucun porduit trouvé sur la catégorie"),
			@APIResponse(responseCode = "200",
	                description = "[OK] - Opération de recherche effectuée avec succès.",
	                content = @Content(mediaType = "application/json",
	                		schema = @Schema(type = SchemaType.OBJECT, implementation = ProductLangData.class)))
	})
	@Path("/{id}/products")
	@PUT
	public Response getCategoryProducts(
			@Parameter(
					description = "Langue de la catégaroie",
					example = "foo", 
		            schema = @Schema(type = SchemaType.STRING)) 
			@PathParam("languageCode") @NotNull String languageCode,
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") @NotNull Long categoryId,
			@NotNull @Valid CategoryLangData data) 
	{	
			
		return Response.noContent().build();
	}
	
	@Operation(
			operationId = "getCategoryProducts", 
			summary = "Recherche les produits de la catégorie", 
			description = "Retourne les informations du produit.")
	@APIResponses(value = { 
			@APIResponse(responseCode = "404", 
					description = "[NOK] - Aucun porduit trouvé sur la catégorie"),
			@APIResponse(responseCode = "200",
	                description = "[OK] - Opération de recherche effectuée avec succès.",
	                content = @Content(mediaType = "application/json",
	                		schema = @Schema(type = SchemaType.OBJECT, implementation = ProductLangData.class)))
	})
	@Path("/{id}")
	@GET
	public Response getCategory(
			@Parameter(
					description = "Langue de la catégaroie",
					example = "foo", 
		            schema = @Schema(type = SchemaType.STRING)) 
			@PathParam("languageCode") @NotNull String languageCode,
			@Parameter(description = "Identifiant de la catégorie") 
			@PathParam("id") @NotNull Long categoryId,
			@NotNull @Valid CategoryLangData data) 
	{	
			
		return Response.noContent().build();
	}

}