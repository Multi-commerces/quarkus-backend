package fr.webmaker.product.data;

import lombok.Data;

@Data
public class ProductImageData {

	private String name = "Nom image";
	
	private String title = "Titre image";
	
	public Boolean cover = true;
	
	private String href = "http://d1fmx1rbmqrxrr.cloudfront.net/cnet/optim/i/edit/2019/04/eso1644bsmall__w770.jpg";
	


	public ProductImageData() {
		super();
	}

}
