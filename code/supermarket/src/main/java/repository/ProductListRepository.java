package repository;

import java.util.List;

import Model.Product;

/*
 * @author: JD, 13.01.2017
 */
public interface ProductListRepository {

	/**
	 * Adds a new Product to the supermarket
	 * 
	 * @param product
	 *            to add
	 * @return true if succeeded
	 */
	Boolean addProduct(Product product);

	/**
	 * Deletes a product from the supermarket
	 * 
	 * @param product
	 *            to delete
	 * @return true if succeded
	 */
	Boolean deleteProduct(Product product);

	/**
	 * Updates a product in the supermarket (by Id)
	 * 
	 * @param product
	 *            to update
	 * @return true if updated
	 */
	Boolean updateProduct(Product product);

	/**
	 * Checks if a product exists in the supermarket (by Id)
	 * 
	 * @param product
	 *            to check
	 * @return true if exits
	 */
	Boolean existsProduct(Product product);

	/**
	 * Get a product form the supermarket (by Id)
	 * 
	 * @param id
	 *            for product to select
	 * @return selected product
	 */
	Product getProduct(Product product);

	/**
	 * Returns all products from the supermarket
	 * 
	 * @return a list with all products
	 */
	List<Product> getAllProducts();

	/**
	 * Counts all products
	 * 
	 * @return return the product count
	 */
	long countProducts();

}
