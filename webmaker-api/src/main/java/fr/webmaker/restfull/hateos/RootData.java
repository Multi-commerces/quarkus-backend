package fr.webmaker.restfull.hateos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.webmaker.restfull.hateos.LinkData.REL;
import fr.webmaker.restfull.model.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Modèle de présentation (ROOT générique)
 * <p>
 * exempple : { "status" : "...", "message": "...", "meta": { ... }, "data" : T , "errors" = { ... } }
 * </p>
 * @author Julien ILARI 2022-01
 *
 * @param <T>
 */
@Getter @Setter
@JsonPropertyOrder({ "status", "message", "meta", "errors", "data" })
public class RootData<T>
{

	public static final String JSON_NODE_PAGE = "meta";
	public static final String JSON_NODE_ITEMS = "data";
	public static final String JSON_NODE_NAV = "links";
	public static final String JSON_NODE_SELF = "self";
	public static final String JSON_NODE_INCLUDES = "included";
	
	/**
	 * Statut applicable à la demande
	 */
	@Schema(readOnly = true)
	private String status = "success";

	/**
	 * Message lisible par l'homme
	 */
	@Schema(readOnly = true)
	private String message = "OK";
	
	/**
	 * Navigation
	 * <ul>
	 * <li>first: la première page de données</li>
	 * <li>last: la dernière page de données</li>
	 * <li>prev: la page précédente de données</li>
	 * <li>next: la page de données suivante</li>
	 * </ul>
	 * 
	 * <p>
	 * Les clés DOIVENT être omises ou avoir une valeur null pour indiquer qu'un lien
	 * particulier n'est pas disponible.
	 * </p>
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JsonProperty(JSON_NODE_NAV) 
	private Map<REL, String> nagigation;

	/**
	 * Les « données primaires » de la ressource
	 */
	@Valid
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	/**
	 * Une liste des erreurs
	 * <p>
	 * Les membres data et errors NE DOIVENT PAS coexister
	 * </p>
	 */
	@Schema(hidden = true)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<ErrorResponse> errors;

	/**
	 * Un méta-objet qui contient des méta-informations non standard.
	 */
	@Schema(readOnly = true)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, Object> meta;

	/**
	 * Contructeur par défaut
	 */
	public RootData()
	{
		this.nagigation = new EnumMap<>(REL.class);
		this.errors = new ArrayList<>();
		this.meta = new HashMap<>();
	}

	/**
	 * Constructeur pour un simple retour status + message
	 * @param status
	 * @param message
	 */
	public RootData(@NotNull String status, @NotNull String message)
	{
		this();
		this.status = Objects.requireNonNull(status);
		this.message = Objects.requireNonNull(message);
		this.nagigation = new EnumMap<>(REL.class);
	}

	/**
	 * Constructeur pour retourner nos données (avec 'success', 'OK')
	 * @param data
	 */
	public RootData(@NotNull T data)
	{
		this();
		this.data = data;
		this.nagigation = new EnumMap<>(REL.class);
		
		nagigation.put(REL.FIRST, null);
		nagigation.put(REL.NEXT, null);
		nagigation.put(REL.PREV, null);
		nagigation.put(REL.LAST, null);
	}

	/**
	 * Constructeur pour retourner nos données avec un status et message spécifique
	 * @param data
	 * @param status
	 * @param message
	 */
	public RootData(@NotNull T data, @NotNull String status, @NotNull String message)
	{
		this();
		this.data = data;
		this.status = Objects.requireNonNull(status);
		this.message = Objects.requireNonNull(message);
		this.nagigation = new EnumMap<>(REL.class);
	}

	/**
	 * Constructeur pour retourner une liste d'erreurs
	 * @param status
	 * @param message
	 * @param errors
	 */
	public RootData(@NotNull String status, @NotNull String message, List<ErrorResponse> errors)
	{
		
		super();
		this.status = Objects.requireNonNull(status);
		this.message = Objects.requireNonNull(message);
		this.errors = Objects.requireNonNull(errors);
		this.nagigation = new EnumMap<>(REL.class);
	}

	@JsonIgnore
	public boolean isRelevant()
	{
		if (data == null)
		{
			return false;
		}

		if (data instanceof Collection)
		{
			return !Arrays.asList(data).isEmpty();
		}

		return true;
	}

}
