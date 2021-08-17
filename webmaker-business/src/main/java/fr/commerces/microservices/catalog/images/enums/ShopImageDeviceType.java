package fr.commerces.microservices.catalog.images.enums;

import java.util.stream.Stream;

public enum ShopImageDeviceType {
	
	DEFAULT, MOBILE, TABLET;
	
	public static Stream<ShopImageDeviceType> stream() {
        return Stream.of(ShopImageDeviceType.values()); 
    }
}