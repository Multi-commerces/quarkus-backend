package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_CREATED_20000004;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000001;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000002;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000003;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000004;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;

import com.neovisionaries.i18n.LanguageCode;

import fr.commerces.microservices.catalog.categories.entity.Category;
import fr.commerces.microservices.catalog.categories.entity.CategoryLang;
import fr.webmaker.data.category.CategoryData;
import fr.webmaker.data.category.CategoryLangData;
import fr.webmaker.data.category.CategoryCompositeData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
public class CategoryManagerTest {

	@Inject
	CategoryManager manager;

	/**
	 * Vérification de l'arbre hiérarchique des catégories
	 */
	@Test
	public void testFindCategoryHierarchy() {
		
		
		// Execution -----------------------------------------
		final List<CategoryCompositeData> categories = manager.findCategoryHierarchy();
		
		Function<Long, CategoryCompositeData> findCategoryHierarchy = catId -> 
				categories.stream().filter(o -> Long.valueOf(o.getId()).equals(catId)).findAny().get();
		
		// Verification --------------------------------------
		assertNotNull(categories);
		assertThat(categories.size(), is(3));

		CategoryCompositeData subCategory;
		List<CategoryLangData> categoryData;

		// CATEGORY_ID_20000001
		subCategory = findCategoryHierarchy.apply(CATEGORY_ID_20000001);
		assertNotNull(subCategory);
		
		categoryData = Collections.emptyList();
		assertNotNull(categoryData);
//		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000001));
//		assertThat(categoryData.getName(), is("DESIGNATION 20000001"));
//		assertThat(categoryData.getDescription(), is("DESCRIPTION 20000001"));

		// CATEGORY_ID_20000002
		subCategory = findCategoryHierarchy.apply(CATEGORY_ID_20000002);
		assertNotNull(subCategory);
		
		categoryData = Collections.emptyList();
		assertNotNull(categoryData);
//		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000002));
//		assertThat(categoryData.getName(), is("DESIGNATION 20000002"));
//		assertThat(categoryData.getDescription(), is("DESCRIPTION 20000002"));

		// CATEGORY_ID_20000003
		subCategory = findCategoryHierarchy.apply(CATEGORY_ID_20000003);
		assertNotNull(subCategory);
		
		categoryData = Collections.emptyList();
		assertNotNull(categoryData);
//		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000003));
//		assertThat(categoryData.getName(), is("DESIGNATION 20000003"));
//		assertThat(categoryData.getDescription(), is("DESCRIPTION 20000003"));

		final List<CategoryCompositeData> subCategories = subCategory.getSubCategories();
		assertThat(subCategories.size(), is(1));
		
		// CATEGORY_ID_20000004
		subCategory = subCategories.get(0);
		assertThat(subCategory.getId(), is(CATEGORY_ID_20000004));
		
		categoryData = Collections.emptyList();
//		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000004));
//		assertThat(categoryData.getName(), is("DESIGNATION 20000004"));
//		assertThat(categoryData.getDescription(), is("DESCRIPTION 20000004"));
	}

	@Test
	public void testFindById() {
		// Execution -----------------------------------------
		final CategoryCompositeData categorySubCategories = manager.findCategoryHierarchyById(CATEGORY_ID_20000003);
		
		
		// Verification --------------------------------------
		assertNotNull(categorySubCategories);
		final List<CategoryLangData> categoryData = Collections.emptyList();
		assertNotNull(categoryData);
		
//		assertThat(categoryData.getName(), is("DESIGNATION 20000003"));
//		assertThat(categoryData.getDescription(), is("DESCRIPTION 20000003"));

		final List<CategoryCompositeData> subCategories = categorySubCategories.getSubCategories();
		assertThat(subCategories.size(), is(1));
	}

	@Test @TestTransaction
	public void testupdate() throws ParseException, InterruptedException {
		final CategoryLang oldCategoryLang = CategoryLang.findByCategoryLangPK(CATEGORY_ID_20000004, LanguageCode.fr).get();
		LocalDateTime dateUpdated = oldCategoryLang.getCategory().getUpdated();
		LocalDateTime dateCreated = oldCategoryLang.getCategory().getCreated();
		
		String nameUpdate = "NAME UPDATE";
		String descUpdate = "DESCRIPTION UPDATE";
		int positionUpdate = 2;
		boolean displayedUpdate = false;

		// Preparation --------------------------------------
		final CategoryLangData data = new CategoryLangData();
		data.setName(JsonNullable.of(nameUpdate));
		data.setDescription(JsonNullable.of(descUpdate));
		data.setCreated(LocalDateTime.now()); // Ignore
		data.setUpdated(LocalDateTime.now()); // Ignore

		// Execution -----------------------------------------
		manager.update(CATEGORY_ID_20000004, LanguageCode.fr, data);

		// Verification --------------------------------------
		final CategoryLang categoryLang = CategoryLang.findByCategoryLangPK(CATEGORY_ID_20000004, LanguageCode.fr).get();
		final Category category = categoryLang.getCategory();
		
		assertTrue(dateCreated.equals(category.getCreated()), "La date de création ne doit pas changée");
		
		assertFalse(dateUpdated.equals(category.getUpdated()), "La date de mise à jour n'a pas changée");
		assertTrue(dateUpdated.isBefore(category.getUpdated()), "La date de mise semble ne pas être mise à jour");

		assertThat(categoryLang.getName(), is(nameUpdate));
		assertThat(categoryLang.getDescription(), is(descUpdate));
		assertThat(category.getCreated(), is(CATEGORY_CREATED_20000004));
		assertThat(category.getPosition(), is(positionUpdate));
		assertThat(category.isDisplayed(), is(displayedUpdate));
	}

	@Test @TestTransaction
	public void testDeleteChildrenCategory() {
		// Execution -----------------------------------------
		manager.delete(CATEGORY_ID_20000004);

		// Verification --------------------------------------
		// N'existe plus
		assertNull(Category.findById(CATEGORY_ID_20000004)); 
		
		// Aucune autre catégorie en moins
		assertThat(Category.listAll().size(), is(4)); 
		
		// La traduction ne peut exister sans la catégorie
		assertThat(CategoryLang.listAll().size(), is(8)); 

		// Ne fait plus partie des enfants de CATEGORY_ID_20000003
		final Category entity = Category.findById(CATEGORY_ID_20000003);
		assertThat(entity.getChildrenCategory().size(), is(1)); 
		
		// TODO : Tester la non suppression des produits
	}

	@Test @TestTransaction
	public void testCreateChildrenCategory() {
		LocalDateTime toDay = LocalDateTime.now();

		// Preparation --------------------------------------
		final CategoryData data = new CategoryData();
		data.setPosition(2);
		data.setDisplayed(true);
		data.setCreated(toDay); // Ignore
		data.setUpdated(toDay); // Ignore

		// Execution -----------------------------------------
		// parentCategory not null lors de l'enregistrement
		manager.createCategory(CATEGORY_ID_20000003, data);

		// Verification --------------------------------------
		final List<Category> categories = Category.findCategoryHierarchy().list();
		assertThat(categories.size(), is(3)); 
		
		final Category category = Category.findById(CATEGORY_ID_20000003);
		assertThat(category.getChildrenCategory().size(), is(2));

	}
	
	@Test @TestTransaction
	public void testCreateRootCategory() {
		LocalDateTime toDay = LocalDateTime.now();

		// Preparation --------------------------------------
		final CategoryData data = new CategoryData();
		data.setPosition(2);
		data.setDisplayed(true);
		data.setCreated(toDay); // Ignore
		data.setUpdated(toDay); // Ignore

		// Execution -----------------------------------------
		// parentCategory is null lors de l'enregistrement
		manager.createCategory(null, data);

		// Verification --------------------------------------
		final List<Category> categories = Category.findCategoryHierarchy().list();
		assertThat(categories.size(), is(4)); 
	}

}