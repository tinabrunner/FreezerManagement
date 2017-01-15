package repository;

import model.ShoppingListItem;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by JD on 16.12.2016.
 */
public class ShoppingListRepositoryStub implements ShoppingListRepository {

    private final List<ShoppingListItem> shoppingList;

    private List<ShoppingListItem> getShoppingList(){
        return this.shoppingList;
    }

    public ShoppingListRepositoryStub(){
        this.shoppingList = new ArrayList<>();
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
    public List<ShoppingListItem> getAllProducts() {
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
    public Boolean updateProduct(ShoppingListItem product) {
		this.getShoppingList().remove(
				this.getShoppingList().stream().filter(map -> map.getId().equals(product.getId())).findFirst().get());

		this.getShoppingList().add(product);
		return true;
	}
}
