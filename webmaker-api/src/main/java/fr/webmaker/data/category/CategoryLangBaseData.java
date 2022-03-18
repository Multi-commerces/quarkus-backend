package fr.webmaker.data.category;

import javax.validation.constraints.NotNull;

import com.github.jasminb.jsonapi.annotations.Type;
import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.data.BaseResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Type(value = "categoryLang")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CategoryLangBaseData extends BaseResource {
	
	protected Long categoryId;

	@NotNull
	protected LanguageCode languageCode = LanguageCode.undefined;
	
	public CategoryLangBaseData(Long categoryId, LanguageCode languageCode) {
		super();
		this.languageCode = languageCode;
		this.categoryId = categoryId;
		this.id = String.join("/",String.valueOf(categoryId), languageCode.toString());
	}
	
	

}
