package fr.commerces.microservices.catalog.categories.lang;

import java.time.LocalDateTime;

import org.openapitools.jackson.nullable.JsonNullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.restfull.databind.LocalDateTimeDeserializer;
import fr.webmaker.restfull.databind.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Type(value = "categoryLang")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryLangData extends CategoryLangBaseData {

	public static final String ATT_CREATED = "created";
	public static final String ATT_UPDATED = "updated";

	public static final String ATT_NAME = "name";
	public static final String ATT_DESC = "description";

	public static final String ATT_META_TITLE = "meta-title";
	public static final String ATT_META_DESC = "meta-description";
	public static final String ATT_META_URL = "friendly-url";



	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonProperty(ATT_CREATED)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	private LocalDateTime created;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
	@JsonProperty(ATT_UPDATED)
	private LocalDateTime updated;


	@JsonProperty(ATT_NAME)
	private JsonNullable<String> name = JsonNullable.undefined();

	@JsonProperty(ATT_DESC)
	private JsonNullable<String> description = JsonNullable.undefined();

	@JsonProperty(ATT_META_TITLE)
	private JsonNullable<String> friendlyURL = JsonNullable.undefined();

	@JsonProperty(ATT_META_DESC)
	private JsonNullable<String> metaDescription = JsonNullable.undefined();

	@JsonProperty(ATT_META_URL)
	private JsonNullable<String> metaTitle = JsonNullable.undefined();

}
