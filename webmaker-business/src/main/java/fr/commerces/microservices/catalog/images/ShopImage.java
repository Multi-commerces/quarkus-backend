package fr.commerces.microservices.catalog.images;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity 
@Table(name = "IMAGE", uniqueConstraints = { 
		@UniqueConstraint(columnNames = { "PRODUCT_IMAGE_ID" }),
		@UniqueConstraint(columnNames = { "FILE_NAME" }),
})
@Getter @Setter @ToString
public class ShopImage extends PanacheEntityBase {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IMAGE_ID")
	public Long id;

	@Column(name = "FILE_NAME", nullable = false)
	public String fileName;

	@Column(name = "TITLE", nullable = false)
	public String caption;

	@ToString.Exclude
	@Column(name = "PICTURE", nullable = false)
	public byte[] picture;
	
	@ToString.Exclude
	@Column(name = "THUMBAIL", nullable = true)
	public byte[] thumbnail;
	
}
