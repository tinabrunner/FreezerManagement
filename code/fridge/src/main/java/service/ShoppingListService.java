package service;

import model.ShoppingListItem;

import java.util.Map;
import java.util.Set;

/**
 * Created by JD on 11.12.2016.
 */
interface ShoppingListService {

    boolean existsProduct(ShoppingListItem product);
    boolean addProduct(ShoppingListItem product);
    boolean deleteProduct(ShoppingListItem product);
    Set<ShoppingListItem> getAllProducts();
    ShoppingListItem getProduct(ShoppingListItem product);
}
