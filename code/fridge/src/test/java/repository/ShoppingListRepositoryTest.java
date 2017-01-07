package repository;

import model.Product;
import utils.ProductHelperTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JD on 16.12.2016.
 */
public class ShoppingListRepositoryTest {


    private ShoppingListRepository repository;

    @Before
    public void setUp(){
        this.repository = new ShoppingListRepositoryStub();
    }

    private Product getFreezerProductDummy(String name, String id){
        return ProductHelperTest.getProductDummy(name, id);
    }

    @Test
    public void checkIfFreezerDummyProductWorksWithEmptyName(){
        Assert.assertEquals(ProductHelperTest.productDummyName, getFreezerProductDummy("", "").getName());
    }

    @Test
    public void checkIfFreezerDummyProductWorksWithEmptyId(){
        Assert.assertEquals(ProductHelperTest.productDummyId, getFreezerProductDummy("", "").getId());
    }

    @Test
    public void checkIfFreezerDummyProductWorksWithNameSet(){
        String freezerProductName = "BMW 123d";
        Assert.assertEquals(freezerProductName, getFreezerProductDummy(freezerProductName, "").getName());
    }

    @Test
    public void checkIfFreezerDummyProductWorksWithIdSet(){
        String freezerProductId = "BMW X3 xDrive30d";
        Assert.assertEquals(freezerProductId, getFreezerProductDummy("", freezerProductId).getId());
    }

    @Test
    public void shouldReturnAnEmptyShoppingList(){
        Assert.assertTrue(repository.getAllProducts().isEmpty());
    }

    @Test
    public void canInsertOneNewFreezerProduct(){

        int amount = 10;
        repository.addProduct(getFreezerProductDummy("", ""), amount);
        Assert.assertEquals(1, repository.getAllProducts().size());
    }

    @Test
    public void shouldReturnTrueIfProductIsAlreadyInShoppingList(){

        int amount = 10;
        repository.addProduct(getFreezerProductDummy("", ""), amount);

        Assert.assertTrue(repository.existsProduct(getFreezerProductDummy("", "")));
    }

    @Test
    public void shouldReturnFalseIfProductIsNotInShoppingList(){
        int amount = 10;
        repository.addProduct(getFreezerProductDummy("", ""), amount);

        Product product = new Product(0, null, 0, 0, 0, 0, false, null);
        product.setId("1987");
        product.setName("Elephant");

        Assert.assertFalse(repository.existsProduct(product));
    }

    @Test
    public void shouldReturnTheSameProductAsWeInserted(){
        int amount = 10;
        Product insertedProduct = getFreezerProductDummy("", "");
        repository.addProduct(insertedProduct, amount);

        Product extractedProduct = repository.getProduct(insertedProduct);

        Assert.assertEquals(insertedProduct.getId(), extractedProduct.getId());
    }

    @Test
    public void shouldReturnTheSameProductAsWeInsertedOutOfBiggerShoppingList(){
        int amount = 10;
        Product findThisProduct = getFreezerProductDummy("", "");

        repository.addProduct(findThisProduct, amount);
        repository.addProduct(getFreezerProductDummy("BMW 330d", "312"), 1);
        repository.addProduct(getFreezerProductDummy("BMW M4", "123"), 1);
        repository.addProduct(getFreezerProductDummy("BMW M135i", "789"), 1);

        Assert.assertEquals(findThisProduct.getId(), repository.getProduct(findThisProduct).getId());
    }

    @Test
    public void shouldReturnThreeAsProductCountFromShoppingList(){
        repository.addProduct(getFreezerProductDummy("BMW 330d", "312"), 1);
        repository.addProduct(getFreezerProductDummy("BMW M4", "123"), 1);
        repository.addProduct(getFreezerProductDummy("BMW M135i", "789"), 1);

        Assert.assertTrue(repository.getProductCount() == 3);
    }
}
