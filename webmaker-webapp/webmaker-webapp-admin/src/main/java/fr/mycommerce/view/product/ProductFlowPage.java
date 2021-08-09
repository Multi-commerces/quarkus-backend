package fr.mycommerce.view.product;

import java.util.stream.Stream;

import lombok.Getter;


public class ProductFlowPage 
{
	enum FlowPage {

		DEFAULT(-1, "product"), BASIC(0, "product-edit"), COMBINATIONS(1, "product-combinations"),
		STOCK(2, "product-stock"), SHIPPING(3, "product-shipping"), PRICING(4, "product-pricing"),
		SEO(5, "product-seo");

		@Getter
		private Integer tabNUm;
		@Getter
		private String page;

		private FlowPage(Integer tabNUm, String page) {
			this.tabNUm = tabNUm;
			this.page = page;
		}

		public static Stream<FlowPage> stream() {
			return Stream.of(FlowPage.values());
		}

	}
}

