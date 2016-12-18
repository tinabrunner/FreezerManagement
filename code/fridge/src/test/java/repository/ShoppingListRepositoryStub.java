package com.freezer.repository;

import com.freezer.domain.Product;
import com.freezer.domain.ShoppingListItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JD on 16.12.2016.
 */
public class ShoppingListRepositoryStub implements ShoppingListRepository {

    private final Map<Product, Integer> shoppingList;

    private Map<Product, Integer> getShoppingList(){
        return this.shoppingList;
    }

    public ShoppingListRepositoryStub(){
        this.shoppingList = new HashMap<>();
    }

    @Override
    public boolean existsProduct(Product product) {

        return this.getShoppingList().entrySet()
                .stream()
                .filter(map -> map.getKey().getId().equals(product.getId()))
                .count() > 0;
    }

    @Override
    public boolean addProduct(Product product, int amount) {
        try {
            this.getShoppingList().put(product, amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Product product) {
        try {
            this.getShoppingList().remove(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<Product, Integer> getAllProducts() {
        return this.getShoppingList();
    }

    @Override
    public Product getProduct(Product product) {

        return this.getShoppingList().entrySet()
                .stream()
                .filter(map -> map.getKey().getId().equals(product.getId()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(new ShoppingListItem());
    }

    @Override
    public long getProductCount() {
        return this.getShoppingList().size();
    }

}
