package fr.webmaker.commons.data;

import lombok.Data;

@Data
public class ImageData {

	private String caption = "Nom image";
	
	private String url = "http://d1fmx1rbmqrxrr.cloudfront.net/cnet/optim/i/edit/2019/04/eso1644bsmall__w770.jpg";

	public ImageData() {
		super();
	}

}
