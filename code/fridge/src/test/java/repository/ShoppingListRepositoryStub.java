package repository;

import model.ShoppingListItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by JD on 16.12.2016.
 */
public class ShoppingListRepositoryStub implements ShoppingListRepository {

    private final Set<ShoppingListItem> shoppingList;

    private Set<ShoppingListItem> getShoppingList(){
        return this.shoppingList;
    }

    public ShoppingListRepositoryStub(){
        this.shoppingList = new HashSet<>();
    }

    @Override
    public boolean existsProduct(ShoppingListItem product) {

        return this.getShoppingList()
                .stream()
                .filter(map -> map.getId().equals(product.getId()))
                .count() > 0;
    }

    @Override
    public boolean addProduct(ShoppingListItem product) {
        try {
            this.getShoppingList().add(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteProduct(ShoppingListItem product) {
        try {
            this.getShoppingList().remove(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<ShoppingListItem> getAllProducts() {
        return this.getShoppingList();
    }

    @Override
    public ShoppingListItem getProduct(ShoppingListItem product) {

        return this.getShoppingList()
                .stream()
                .filter(map -> map.getId().equals(product.getId()))
                .findFirst()
                .orElse(
                        new ShoppingListItem(product.getId(), product.getPreis(), product.getName(), product.getVerpackungsGroesse(), product.getMindestBestand(), product.getHoechstBestand(), product.isRegelmaessig()));
    }

    @Override
    public long getProductCount() {
        return this.getShoppingList().size();
    }
    
    @Override
    public Boolean updateProduct() {
    	return true;
    }
}
