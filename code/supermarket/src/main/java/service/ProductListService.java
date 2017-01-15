package service;

import java.util.List;

import Model.Product;

/*
 * @author: JD, 13.01.2017
 */

public interface ProductListService {

	/**
	 * Add or update a given product
	 * 
	 * @param product
	 *            to add or update
	 * @return true if updated or added
	 */
	Boolean addProduct(Product product);

	/**
	 * Deletes a product from the supermarket
	 * 
	 * @param product
	 *            to delete
	 * @return true if it was deleted
	 */
	Boolean deleteProduct(Product product);

	/**
	 * Checks if a product exists in the supermarket (by Id)
	 * 
	 * @param product
	 *            to check
	 * @return true if it´s exists
	 */
	Boolean existsProduct(Product product);

	/**
	 * Returns a list with all products from the supermarket
	 * 
	 * @return a list with all products
	 */
	List<Product> getAllProducts();

	/**
	 * Count all products in the supermarket
	 * 
	 * @return a product count
	 */
	long countProducts();
}
