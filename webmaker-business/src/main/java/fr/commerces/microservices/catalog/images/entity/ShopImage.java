package fr.commerces.microservices.catalog.images.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "IMAGE", uniqueConstraints = { @UniqueConstraint(columnNames = { "IMAGE_ID" }),
		@UniqueConstraint(columnNames = { "FILE_NAME" }), })
@Getter
@Setter
@ToString
public class ShopImage extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IMAGE_ID")
	public Long id;

	@Column(name = "NAME", nullable = false)
	public String name;

	@Column(name = "OBJ_ID", nullable = false)
	public Long objId;

	@Column(name = "FILE_NAME", nullable = false)
	public String fileName;

	@Column(name = "TITLE", nullable = false)
	public String caption;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ShopImageDimentionConfig.class)
	@JoinColumn(name = "CONFIG_IMAGE_ID", nullable = false)
	public ShopImageDimentionConfig dimentionConfig;
	
	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false, insertable = true)
	public LocalDateTime created;
	
	@Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false, insertable = true)
	public LocalDateTime updated;

//	@ToString.Exclude
//	@Column(name = "IMAGE", nullable = true)
//	public byte[] image;

	public static PanacheQuery<ShopImage> findByFileName(final String fileName) {
		return find("fileName = ?1", fileName);
	}

}
