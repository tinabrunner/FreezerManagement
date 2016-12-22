package service;

import java.util.Map;

import domain.Product;
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
    public boolean existsProduct(Product product) {
        return this.shoppingListRepository.existsProduct(product);
    }

    /**
     * Add product only, if it's not existing in data
     *
     * @param product
     * @param amount
     * @return
     */
    @Override
    public boolean addProduct(Product product, int amount) {
        return !this.shoppingListRepository.existsProduct(product) &&
                this.shoppingListRepository.addProduct(product, amount);
    }

    /**
     * Delete product, but before: check if it's existing in data
     *
     * @param product
     * @return
     */
    @Override
    public boolean deleteProduct(Product product) {
        return this.shoppingListRepository.existsProduct(product) &&
                this.shoppingListRepository.deleteProduct(product);
    }

    /**
     * @return
     */
    @Override
    public Map<Product, Integer> getAllProducts() {
        return this.shoppingListRepository.getAllProducts();
    }

    /**
     * @param product
     * @return
     */
    @Override
    public Product getProduct(Product product) {
        return this.shoppingListRepository.getProduct(product);
    }
}
