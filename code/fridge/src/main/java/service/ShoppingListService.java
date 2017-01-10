package service;

import java.util.Set;

import model.ShoppingListItem;

/**
 * Created by JD on 11.12.2016.
 */
interface ShoppingListService {

	boolean existsProduct(ShoppingListItem product);

	boolean addProduct(ShoppingListItem product);

	boolean deleteProduct(ShoppingListItem product);

	Set<ShoppingListItem> getAllProducts();

	ShoppingListItem getProduct(ShoppingListItem product);

	boolean updateProduct(ShoppingListItem product);
}
