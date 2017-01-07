package repository;

import model.ShoppingListItem;

import java.util.Map;
import java.util.Set;

/**
 * Created by JD on 10.12.2016.
 *
 */
public class ShoppingListRepositoryImpl implements ShoppingListRepository {

    private ShoppingListRepositoryImpl() {
    }

    @Override
    public boolean existsProduct(ShoppingListItem product) {
        return false;
    }

    @Override
    public boolean addProduct(ShoppingListItem product) {

        return false;
    }

    @Override
    public boolean deleteProduct(ShoppingListItem product) {
        return false;
    }

    @Override
    public Set<ShoppingListItem> getAllProducts() {
        return null;
    }

    @Override
    public ShoppingListItem getProduct(ShoppingListItem product) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

}
