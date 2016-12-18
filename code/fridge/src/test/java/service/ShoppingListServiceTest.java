package service;

import domain.Product;
import repository.ShoppingListRepositoryStub;
import utils.ProductHelperTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JD on 16.12.2016.
 */
public class ShoppingListServiceTest {

    private ShoppingListRepositoryStub repository;
    private ShoppingListServiceImpl shoppingListService;

    @Before
    public void setUp(){
        repository = new ShoppingListRepositoryStub();
        shoppingListService = new ShoppingListServiceImpl(repository);
    }

    @Test
    public void shouldReturnAnEmptyShoppingList(){
        Assert.assertTrue(shoppingListService.getAllProducts().isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenSearchingProductBecauseListIsEmpty(){
        Assert.assertFalse(shoppingListService.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueWhenSearchingBecauseProductExists(){
        int amount = 10;
        repository.addProduct(ProductHelperTest.getProductDummy("", ""), amount);

        Assert.assertTrue(shoppingListService.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void checkIfAddingAnewProductWorks(){
        int amount = 10;

        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", ""), amount));
    }

    @Test
    public void shouldReturnFalseBecauseProductAlreadyExists(){
        int amount = 10;

        repository.addProduct(ProductHelperTest.getProductDummy("", ""), amount);

        Assert.assertFalse(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", ""), amount));
    }

    @Test
    public void shouldReturnTrueBecauseAddingProductDoesNotExitsInList(){
        int amount = 10;

        repository.addProduct(ProductHelperTest.getProductDummy("BMW 123d", "123"), amount);

        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", ""), amount));
    }

    @Test
    public void shouldReturnTrueIfDeletionOfProductWentWell(){
        int amount = 10;
        Product product = ProductHelperTest.getProductDummy("", "");
        repository.addProduct(product, amount);

        Assert.assertTrue(shoppingListService.deleteProduct(product));
    }

    @Test
    public void shouldReturnFalseIfDeletionOfNonExistingProduct(){
        Assert.assertFalse(shoppingListService.deleteProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTheSameProductWeInsertedBefore(){
        String searchId = "id-to-search";
        Product searchProduct = ProductHelperTest.getProductDummy("BMW 0", searchId);
        int amount = 10;
        repository.addProduct(searchProduct, amount);
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 1", "123"), amount);
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 2", "456"), amount);
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 3", "789"), amount);

        Assert.assertEquals(searchProduct.getId(), shoppingListService.getProduct(ProductHelperTest.getProductDummy("some name", searchId)).getId());
    }

}
