package fr.webmaker.data.category;

import com.github.jasminb.jsonapi.annotations.Type;

import fr.webmaker.data.BaseResource;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Type(value = CategoryData.TYPE, path = "/categories/{id}")
public class CategoryBaseData extends BaseResource {

	public CategoryBaseData(String id) {
		super(id);
	}

}
