package fr.commerces.services.internal.categories;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryData {


	public CategoryData parentCategory;

	public String description;

	public String designation;

	public int position;

	public boolean displayed;

	public byte[] bigPicture;

	public byte[] smallPicture;



}
