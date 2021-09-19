package fr.commerces.microservices.catalog.categories;

import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_CREATED_20000001;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_CREATED_20000002;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_CREATED_20000003;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_CREATED_20000004;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_IDENTIFIER_20000001FR;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_IDENTIFIER_20000002FR;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_IDENTIFIER_20000003FR;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000003;
import static fr.commerces.commons.utilities.UtilityTest.CATEGORY_ID_20000004;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import com.neovisionaries.i18n.LanguageCode;

import fr.webmaker.commons.identifier.LangID;
import fr.webmaker.microservices.catalog.categories.data.CategoryData;
import fr.webmaker.microservices.catalog.categories.data.CategoryHierarchyData;
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
		final List<CategoryHierarchyData> categories = manager.findCategoryHierarchy(LanguageCode.fr);
		
		Function<LangID, CategoryHierarchyData> findCategoryHierarchy = catId -> 
				categories.stream().filter(o -> o.getIdentifier().equals(catId)).findAny().get();
		

		// Verification --------------------------------------
		assertNotNull(categories);
		assertThat(categories.size(), is(3));

		CategoryHierarchyData categorySubCategories;
		CategoryData categoryData;

		// CATEGORY_ID_20000001
		categorySubCategories = findCategoryHierarchy.apply(CATEGORY_IDENTIFIER_20000001FR);
		assertNotNull(categorySubCategories);
		
		categoryData = categorySubCategories.getCategory();
		assertNotNull(categoryData);
		assertNotNull(categoryData.getDescription());
		assertNotNull(categoryData.getName());
		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000001));

		// CATEGORY_ID_20000002
		categorySubCategories = findCategoryHierarchy.apply(CATEGORY_IDENTIFIER_20000002FR);
		assertNotNull(categorySubCategories);
		
		categoryData = categorySubCategories.getCategory();
		assertNotNull(categoryData);
		assertNotNull(categoryData.getDescription());
		assertNotNull(categoryData.getName());
		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000002));

		// CATEGORY_ID_20000003
		categorySubCategories = findCategoryHierarchy.apply(CATEGORY_IDENTIFIER_20000003FR);
		assertNotNull(categorySubCategories);
		
		categoryData = categorySubCategories.getCategory();
		assertNotNull(categoryData);
		assertNotNull(categoryData.getDescription());
		assertNotNull(categoryData.getName());
		assertThat(categoryData.getCreated(), is(CATEGORY_CREATED_20000003));

		final List<CategoryHierarchyData> subCategories = categorySubCategories.getSubCategories();
		assertThat(subCategories.size(), is(1));
	}

	@Test
	public void testFindById() {
		// Execution -----------------------------------------
		final CategoryHierarchyData categorySubCategories = manager.findCategoryHierarchyById(CATEGORY_ID_20000003, LanguageCode.fr);

		// Verification --------------------------------------
		assertNotNull(categorySubCategories);

		final List<CategoryHierarchyData> subCategories = categorySubCategories.getSubCategories();
		assertThat(subCategories.size(), is(1));
	}

	@Test
	@TestTransaction
	public void testupdate() throws ParseException, InterruptedException {

		LocalDateTime dateUpdate = LocalDateTime.now();
		String nameUpdate = "NAME UPDATE";
		String descUpdate = "DESCRIPTION UPDATE";
		int positionUpdate = 2;
		boolean displayedUpdate = false;

		// Preparation --------------------------------------
		final CategoryData data = new CategoryData();
		data.setName(nameUpdate);
		data.setDescription(descUpdate);
		data.setPosition(positionUpdate);
		data.setDisplayed(displayedUpdate);
		data.setCreated(LocalDateTime.now()); // Ignore
		data.setUpdated(LocalDateTime.now()); // Ignore

		// Execution -----------------------------------------
		manager.update(CATEGORY_ID_20000004, LanguageCode.fr, data);

		// Verification --------------------------------------
		final CategoryLang categoryLang = CategoryLang.findByCategoryLangPK(CATEGORY_ID_20000004, LanguageCode.fr).get();
		final Category category = categoryLang.getCategory();
		assertTrue(dateUpdate.isBefore(category.getUpdated()), "La date de mise semble ne pas être mise à jour");

		assertThat(categoryLang.getName(), is(nameUpdate));
		assertThat(categoryLang.getDescription(), is(descUpdate));
		assertThat(category.getCreated(), is(CATEGORY_CREATED_20000004));
		assertThat(category.getPosition(), is(positionUpdate));
		assertThat(category.isDisplayed(), is(displayedUpdate));
	}

	@Test
	@TestTransaction
	public void testDeleteChildrenCategory() {
		// Execution -----------------------------------------
		manager.delete(CATEGORY_ID_20000004);

		// Verification --------------------------------------
		// N'existe plus
		assertNull(Category.findById(CATEGORY_ID_20000004)); 
		
		// Aucune autre catégorie en moins
		assertThat(Category.listAll().size(), is(3)); 
		
		// La traduction ne peut exister sans la catégorie
		assertThat(CategoryLang.listAll().size(), is(3)); 

		// Ne fait plus partie des enfants de CATEGORY_ID_20000003
		final Category entity = Category.findById(CATEGORY_ID_20000003);
		assertThat(entity.getChildrenCategory().size(), is(0)); 
		
		// TODO : Tester la non suppression des produits
	}

	@Test
	@TestTransaction
	public void testCreateChildrenCategory() {
		LocalDateTime toDay = LocalDateTime.now();

		// Preparation --------------------------------------
		final CategoryData data = new CategoryData();
		data.setName("BIDON NAME");
		data.setDescription("BIDON DESC");
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
	
	@Test
	@TestTransaction
	public void testCreateRootCategory() {
		LocalDateTime toDay = LocalDateTime.now();

		// Preparation --------------------------------------
		final CategoryData data = new CategoryData();
		data.setName("BIDON NAME");
		data.setDescription("BIDON DESC");
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