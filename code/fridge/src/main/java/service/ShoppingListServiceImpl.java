package service;

import java.util.List;

import model.ShoppingListItem;
import repository.ShoppingListRepository;

/**
 * Created by JD on 12.12.2016.
 */
public class ShoppingListServiceImpl implements ShoppingListService {

	private final ShoppingListRepository shoppingListRepository;

	/**
	 * @param shoppingListRepository
	 */
	public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository) {
		this.shoppingListRepository = shoppingListRepository;
	}

	/**
	 * Check if product is in data
	 *
	 * @param product
	 * @return
	 */
	@Override
	public boolean existsProduct(ShoppingListItem product) {
		return this.shoppingListRepository.existsProduct(product);
	}

	/**
	 * Add product only, if it's not existing in data
	 *
	 * @param product
	 * @return
	 */
	@Override
	public boolean addProduct(ShoppingListItem product) {
		if (this.shoppingListRepository.existsProduct(product)) {
			return this.updateProduct(product);
		} else {
			return this.shoppingListRepository.addProduct(product);
		}
	}

	/**
	 * Delete product, but before: check if it's existing in data
	 *
	 * @param product
	 * @return
	 */
	@Override
	public boolean deleteProduct(ShoppingListItem product) {
		return this.shoppingListRepository.existsProduct(product) && this.shoppingListRepository.deleteProduct(product);
	}

	/**
	 * @return
	 */
	@Override
	public List<ShoppingListItem> getAllProducts() {
		return this.shoppingListRepository.getAllProducts();
	}

	/**
	 * @param product
	 * @return
	 */
	@Override
	public ShoppingListItem getProduct(ShoppingListItem product) {
		return this.shoppingListRepository.getProduct(product);
	}

	/**
	 * @param product
	 * @return boolean; TRUE if updated
	 */
	@Override
	public boolean updateProduct(ShoppingListItem product) {
		return this.shoppingListRepository.updateProduct(product);
	}

}
