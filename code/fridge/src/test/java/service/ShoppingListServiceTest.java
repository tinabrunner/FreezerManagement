package service;

import model.ShoppingListItem;
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
        repository.addProduct(ProductHelperTest.getProductDummy("", ""));

        Assert.assertTrue(shoppingListService.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void checkIfAddingAnewProductWorks(){

        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnFalseBecauseProductAlreadyExists(){

        repository.addProduct(ProductHelperTest.getProductDummy("", ""));

        Assert.assertFalse(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueBecauseAddingProductDoesNotExitsInList(){

        repository.addProduct(ProductHelperTest.getProductDummy("BMW 123d", "123"));

        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueIfDeletionOfProductWentWell(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        repository.addProduct(product);

        Assert.assertTrue(shoppingListService.deleteProduct(product));
    }

    @Test
    public void shouldReturnFalseIfDeletionOfNonExistingProduct(){
        Assert.assertFalse(shoppingListService.deleteProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTheSameProductWeInsertedBefore(){
        String searchId = "id-to-search";
        ShoppingListItem searchProduct = ProductHelperTest.getProductDummy("BMW 0", searchId);
        repository.addProduct(searchProduct);
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 1", "123"));
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 2", "456"));
        repository.addProduct(ProductHelperTest.getProductDummy("BMW 3", "789"));

        Assert.assertEquals(searchProduct.getId(), shoppingListService.getProduct(ProductHelperTest.getProductDummy("some name", searchId)).getId());
    }

}
