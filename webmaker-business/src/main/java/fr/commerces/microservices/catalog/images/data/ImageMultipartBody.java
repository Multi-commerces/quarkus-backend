package fr.commerces.microservices.catalog.images.data;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import fr.commerces.microservices.catalog.images.enums.ShopImageDirectoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageMultipartBody {

	@FormParam("fileName")
	@PartType(MediaType.TEXT_PLAIN)
	private String fileName;

	@FormParam("caption")
	@PartType(MediaType.TEXT_PLAIN)
	private String caption;

	@FormParam("thumnail")
	@PartType(MediaType.TEXT_PLAIN)
	public Boolean thumnail;
	
	@FormParam("cover")
	@PartType(MediaType.TEXT_PLAIN)
	public Boolean cover;
	
	@FormParam("imageType")
	@PartType(MediaType.TEXT_PLAIN)
	public ShopImageDirectoryType imageType;

	@FormParam("file")
	@PartType("image/gif, image/jpg, image/jpeg, image/pjpeg, image/png, image/x-png")
	@ToString.Exclude
	private InputStream file;
	
	

}
