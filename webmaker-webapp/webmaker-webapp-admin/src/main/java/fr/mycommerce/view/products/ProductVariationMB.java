package fr.mycommerce.view.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import fr.mycommerce.service.product.ProductVariationRestClient;
import fr.mycommerce.transverse.AbstractCrudView;
import fr.mycommerce.transverse.ActionType;
import fr.mycommerce.transverse.JavaFacesTool;
import fr.mycommerce.transverse.Model;
import fr.webmaker.microservices.catalog.products.data.ProductVariationData;
import fr.webmaker.microservices.catalog.products.id.ProductVariationID;
import lombok.Getter;

@Named("adminProductVariationMB")
@ViewScoped
public class ProductVariationMB extends AbstractCrudView<ProductVariationData, ProductVariationID> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private ProductVariationRestClient service;

	@Getter
	private Long productId;

	@Getter
	private Long variationId;

	public ProductVariationMB() {
		super();

		final String param1 = JavaFacesTool.getValueParam("id");
		if (param1 != null) {
			productId = Long.valueOf(param1);
		}
		final String param2 = JavaFacesTool.getValueParam("variationId");
		if (param2 != null) {
			variationId = Long.valueOf(param2);
		}
		
	}

	@Override
	public List<Model<ProductVariationData, ProductVariationID>> findAll() {
		if (productId == null) {
			return new ArrayList<>();
		}

		final List<Model<ProductVariationData, ProductVariationID>> values = service.getVariations(productId).stream()
		.map(o -> new Model<ProductVariationData, ProductVariationID>(o.getIdentifier(), o.getData()))
		.collect(Collectors.toList());
		
		return values;
	}

	@Override
	public void create() {
		service.create(productId, model.getData());
	}

	@Override
	public void update() {
		service.update(model.getIdentifier().getProductId(), model.getIdentifier().getVariationId(), model.getData());
		
	}

	@Override
	public void delete(ProductVariationID identifier) {
		getService().delete(productId, identifier.getId());
	}

	public List<Model<ProductVariationData, ProductVariationID>> loadByProductId(final Long productId) {
		this.productId = productId;
		//TODO reprendre loadItems(getService().get("fr", productId));

		return new ArrayList<Model<ProductVariationData,ProductVariationID>>();//this.items;
	}

	@Override
	protected void loadItems(final List<Model<ProductVariationData, ProductVariationID>> items) {
		super.loadItems(items);

		if (variationId != null) {
			setModel(this.items.stream().filter(o -> o.getIdentifier().getId().equals(variationId)).findAny().orElse(null));

			if (model != null && model.getIdentifier().getId() != null) {
				this.action = ActionType.UPDATE;
			}
		}
	}

	@Override
	protected ProductVariationData newDataInstance() {
		return new ProductVariationData();
	}

	@Override
	protected void delete(List<ProductVariationID> ids) {
		// TODO Auto-generated method stub
		
	}

}
