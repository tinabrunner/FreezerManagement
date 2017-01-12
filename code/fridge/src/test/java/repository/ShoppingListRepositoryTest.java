package repository;

import model.ShoppingListItem;
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

    private ShoppingListItem getFreezerProductDummy(String name, String id){
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

        repository.addProduct(getFreezerProductDummy("", ""));
        Assert.assertEquals(1, repository.getAllProducts().size());
    }

    @Test
    public void shouldReturnTrueIfProductIsAlreadyInShoppingList(){

        repository.addProduct(getFreezerProductDummy("", ""));

        Assert.assertTrue(repository.existsProduct(getFreezerProductDummy("", "")));
    }

    @Test
    public void shouldReturnFalseIfProductIsNotInShoppingList(){
        repository.addProduct(getFreezerProductDummy("", ""));
    
        ShoppingListItem product = new ShoppingListItem();
        product.setId("1987");
        product.setName("Elephant");

        Assert.assertFalse(repository.existsProduct(product));
    }

    @Test
    public void shouldReturnTheSameProductAsWeInserted(){
        ShoppingListItem insertedProduct = getFreezerProductDummy("", "");
        repository.addProduct(insertedProduct);
    
        ShoppingListItem extractedProduct = repository.getProduct(insertedProduct);

        Assert.assertEquals(insertedProduct.getId(), extractedProduct.getId());
    }

    @Test
    public void shouldReturnTheSameProductAsWeInsertedOutOfBiggerShoppingList(){
        ShoppingListItem findThisProduct = getFreezerProductDummy("", "");

        repository.addProduct(findThisProduct);
        repository.addProduct(getFreezerProductDummy("BMW 330d", "312"));
        repository.addProduct(getFreezerProductDummy("BMW M4", "123"));
        repository.addProduct(getFreezerProductDummy("BMW M135i", "789"));

        Assert.assertEquals(findThisProduct.getId(), repository.getProduct(findThisProduct).getId());
    }

    @Test
    public void shouldReturnThreeAsProductCountFromShoppingList(){
        repository.addProduct(getFreezerProductDummy("BMW 330d", "312"));
        repository.addProduct(getFreezerProductDummy("BMW M4", "123"));
        repository.addProduct(getFreezerProductDummy("BMW M135i", "789"));

        Assert.assertTrue(repository.getProductCount() == 3);
    }
    
    @Test
    public void shouldReturnAnUpdatedProduct(){
    	ShoppingListItem itemToUpdate = getFreezerProductDummy("BMW 123d", "2312");
    	ShoppingListItem itemToFind = getFreezerProductDummy("---", itemToUpdate.getId());
    	repository.addProduct(itemToUpdate);
    	repository.addProduct(getFreezerProductDummy("BMW 330d", "312"));
        repository.addProduct(getFreezerProductDummy("BMW M4", "123"));
        repository.addProduct(getFreezerProductDummy("BMW M135i", "789"));        
        itemToUpdate.setName("Nissan");
        
        repository.updateProduct(itemToUpdate);
        
        itemToFind = repository.getProduct(itemToUpdate);
        
        Assert.assertEquals(itemToFind.getName(), itemToUpdate.getName());
        
    }
}
