package service;

import domain.Product;

import java.util.Map;

/**
 * Created by JD on 11.12.2016.
 */
interface ShoppingListService {

    boolean existsProduct(Product product);
    boolean addProduct(Product product, int amount);
    boolean deleteProduct(Product product);
    Map<Product, Integer> getAllProducts();
    Product getProduct(Product product);
}
