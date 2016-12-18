package com.freezer.repository;

import com.freezer.domain.Product;

import java.util.Map;

/**
 * Created by JD on 10.12.2016.
 *
 */
public class ShoppingListRepositoryImpl implements ShoppingListRepository {

    private ShoppingListRepositoryImpl() {
    }

    @Override
    public boolean existsProduct(Product product) {
        return false;
    }

    @Override
    public boolean addProduct(Product product, int amount) {

        return false;
    }

    @Override
    public boolean deleteProduct(Product product) {
        return false;
    }

    @Override
    public Map<Product, Integer> getAllProducts() {
        return null;
    }

    @Override
    public Product getProduct(Product product) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

}
