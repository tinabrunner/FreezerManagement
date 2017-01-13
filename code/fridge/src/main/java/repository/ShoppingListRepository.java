package repository;

import java.util.List;

import model.ShoppingListItem;

/**
 * Created by JD on 10.12.2016. A D V I C E All products are identified by its
 * id.
 */
public interface ShoppingListRepository {

	/**
	 * Checks if a given Product is in the current shoppinglist
	 *
	 * @param product
	 *            to check
	 * @return boolean if the product exists
	 */
	boolean existsProduct(ShoppingListItem product);

	/**
	 * Adds a product an it's amount to the shoppinglist
	 *
	 * @param product
	 *            to add
	 * @return true if it was added
	 */
	boolean addProduct(ShoppingListItem product);

	/**
	 * Deletes the given product from the shoppinglist
	 *
	 * @param product
	 *            to delete
	 * @return true only if the product was (really) deleted (acknowledged AND
	 *         deletionCount)
	 */
	boolean deleteProduct(ShoppingListItem product);

	/**
	 * Returns a map with all products and its amount of the shoppinglist
	 *
	 * @return Map
	 */
	List<ShoppingListItem> getAllProducts();

	/**
	 * Returns a given product out of the shoppinglist (not really useful now,
	 * but we have it)
	 *
	 * @param product
	 *            to find with further information
	 * @return the selected product
	 */
	ShoppingListItem getProduct(ShoppingListItem product);

	/**
	 * Updates a given product with all values
	 * 
	 * @param product
	 *            to update
	 * @return boolean; TRUE if acknowledged and updated
	 */
	Boolean updateProduct(ShoppingListItem product);

	/**
	 * Count all products in the shoppinglist (NOT amount!)
	 *
	 * @return an integer
	 */
	long getProductCount();
}
