package service;

import java.util.List;

import Model.Product;
import repository.ProductListRepository;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListServiceImpl implements ProductListService {

	private final ProductListRepository productListRepository;

	public ProductListServiceImpl(ProductListRepository productListRepository) {
		this.productListRepository = productListRepository;
	}

	@Override
	public Boolean addProduct(Product product) {
		if (productListRepository.existsProduct(product)) {
			return productListRepository.updateProduct(product);
		} else {
			return productListRepository.addProduct(product);
		}
	}

	@Override
	public Boolean deleteProduct(Product product) {
		return productListRepository.existsProduct(product) && productListRepository.deleteProduct(product);
	}

	@Override
	public Boolean existsProduct(Product product) {
		return productListRepository.existsProduct(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productListRepository.getAllProducts();
	}

	@Override
	public long countProducts() {
		return productListRepository.countProducts();
	}

}
