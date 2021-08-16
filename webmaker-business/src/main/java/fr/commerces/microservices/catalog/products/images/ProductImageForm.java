package fr.commerces.microservices.catalog.products.images;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductImageForm {
	
	@FormParam("fileName")
	private String fileName;

	@FormParam("caption")
	private String caption;

	@FormParam("thumnail")
	public Boolean thumnail;

	@FormParam("image")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	@ToString.Exclude
	private InputStream content;

}
