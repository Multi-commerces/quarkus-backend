package fr.mycommerce.view.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import fr.mycommerce.commons.models.Model;
import fr.mycommerce.commons.tools.JavaFacesTool;
import fr.mycommerce.commons.views.AbstractCrudView;
import fr.mycommerce.commons.views.ActionType;
import fr.mycommerce.service.product.ProductVariationRestClient;
import fr.webmaker.data.product.ProductVariationData;
import lombok.Getter;

@Named("adminProductVariationMB")
@ViewScoped
public class ProductVariationMB extends AbstractCrudView<ProductVariationData> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@RestClient
	@Getter
	private ProductVariationRestClient service;

	@Getter
	private String productId;

	@Getter
	private Long variationId;

	public ProductVariationMB() {
		super();

		final String param1 = JavaFacesTool.getValueParam("id");
		if (param1 != null) {
			productId = param1;
		}
		final String param2 = JavaFacesTool.getValueParam("variationId");
		if (param2 != null) {
			variationId = Long.valueOf(param2);
		}
		
	}

	@Override
	public List<Model<ProductVariationData>> findAll() {
		if (productId == null) {
			return new ArrayList<>();
		}
		byte[] flux = service.getVariations(Long.valueOf(productId));

		final List<Model<ProductVariationData>> values = converter.readDocumentCollection(flux, ProductVariationData.class).get().stream()
				.map(o -> new Model<ProductVariationData>(o))
				.collect(Collectors.toList());
		
		return values;
	}

	@Override
	public void create() {
		model.getData();
		service.create(Long.valueOf(productId), null);
	}

	@Override
	public void update() {
		model.getData();
		service.update(Long.valueOf(model.getIdentifier()), Long.valueOf(model.getIdentifier()), null);
	}

	@Override
	public void delete(String identifier) {
		getService().delete(Long.valueOf(productId), Long.valueOf(identifier));
	}

	public List<Model<ProductVariationData>> loadByProductId(final String productId) {
		this.productId = productId;
		
		
		List<ProductVariationData> items = converter.readDocumentCollection(getService().getVariations(Long.valueOf(productId)), ProductVariationData.class).get();
		loadItems(items.stream()
				.map(o -> new Model<ProductVariationData>(o))
				.collect(Collectors.toList()));

		return new ArrayList<Model<ProductVariationData>>();//this.items;
	}

	@Override
	protected void loadItems(final List<Model<ProductVariationData>> items) {
		super.loadItems(items);

		if (variationId != null) {
			setModel(this.items.stream()
						.filter(o -> Long.valueOf(o.getIdentifier()).equals(variationId))
						.findAny()
						.orElse(null));

			if (model != null && model.getIdentifier() != null) {
				this.action = ActionType.UPDATE;
			}
		}
	}

	@Override
	protected ProductVariationData newDataInstance() {
		return new ProductVariationData();
	}

	@Override
	protected void delete(List<String> ids) {
		// TODO Auto-generated method stub
		
	}

}
