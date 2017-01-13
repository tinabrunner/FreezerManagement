package repository;

import java.util.ArrayList;
import java.util.List;

import Model.Product;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListRepositoryStub implements ProductListRepository {

	private final List<Product> productList;

	public ProductListRepositoryStub() {
		this.productList = new ArrayList<>();
	}

	@Override
	public Boolean addProduct(Product product) {
		return productList.add(product);
	}

	@Override
	public Boolean deleteProduct(Product product) {
		return productList.remove(product);
	}

	@Override
	public Boolean existsProduct(Product product) {
		return productList.stream().filter(item -> item.getId().equals(product.getId())).count() == 1;
	}

	@Override
	public Product getProduct(Product product) {
		return this.getAllProducts().stream().filter(map -> map.getId().equals(product.getId())).findFirst()
				.orElse(new Product());
	}

	@Override
	public List<Product> getAllProducts() {
		return productList;
	}

	@Override
	public long countProducts() {
		return productList.size();
	}

	@Override
	public Boolean updateProduct(Product product) {
		this.getAllProducts().remove(
				this.getAllProducts().stream().filter(item -> item.getId().equals(product.getId())).findFirst());
		return this.getAllProducts().add(product);
	}

}
