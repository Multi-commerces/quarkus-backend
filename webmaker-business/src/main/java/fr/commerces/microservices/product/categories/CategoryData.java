package fr.commerces.microservices.product.categories;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryData implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public LocalDateTime created;

	public LocalDateTime updated;
	

	/**
	 * Nom de la catégorie
	 */
	public String name;

	/**
	 * Description
	 */
	public String description;

	/**
	 * Position (utilisé lors de l'affichage dans un tableau)
	 */
	public int position;

	/**
	 * Condition pour que la catégorie apparaisse dans le menu de la boutique
	 */
	public boolean displayed;

	/**
	 * Image principale de la catégorie
	 */
	@ToString.Exclude
	public byte[] coverImage;

	/**
	 * une petite image pour la page de la catégorie parent.
	 */
	@ToString.Exclude
	public byte[] thumbnail;

	/**
	 * une vignette de la catégorie pour le menu sous la forme d'une petite image
	 * représentant la catégorie.
	 */
	@ToString.Exclude
	public byte[] menuThumbnail;

	@ToString.Exclude
	public Map<Long, CategoryData> subCategories;

}
