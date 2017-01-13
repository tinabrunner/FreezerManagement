package service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import model.ShoppingListItem;
import repository.ShoppingListRepositoryMongoImpl;
import repository.ShoppingListRepository;
import util.ShoppingListHelper;
import util.ProductHelperTest;

/*
 * @author: JD, 13.01.2017
 */
public class ShoppingListServiceIntegrationTest {
	

	private ShoppingListServiceImpl shoppingListService;
	private ShoppingListRepository shoppingListRepository;
	
	private static final String databaseName = "fridgeIT";
    private MongoProvider mongoProvider;
    private DatabaseProviderImpl mongoConnection;

    private DatabaseProviderImpl getMongoConnection() {
        DatabaseProviderImpl mongoDB = new DatabaseProviderImpl(this.mongoProvider);
        mongoDB.setDatabaseName(databaseName);
        mongoDB.connect();

        return mongoDB;
    }
	
	@Before
	public void setUp(){
		mongoProvider = new MongoProvider();
        mongoProvider.init();
        mongoConnection = new DatabaseProviderImpl(mongoProvider);

        shoppingListRepository = new ShoppingListRepositoryMongoImpl(getMongoConnection());
        shoppingListService = new ShoppingListServiceImpl(shoppingListRepository);
        getMongoConnection().getCollection(ShoppingListHelper.collectionName).drop();
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
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("", ""));

        Assert.assertTrue(shoppingListService.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void checkIfAddingAnewProductWorks(){

        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", "")));
    }
    
    @Test
    public void shouldReturnUpdatedProductWhenAddingWithSameId(){
    	String id = "1202";
    	ShoppingListItem itemToAdd = ProductHelperTest.getProductDummy("", id);
    	ShoppingListItem itemUpdate = ProductHelperTest.getProductDummy("", id);
        shoppingListRepository.addProduct(itemToAdd);
        itemToAdd.setName("Hund");
        shoppingListRepository.addProduct(itemToAdd);
        
        itemUpdate = shoppingListService.getProduct(itemToAdd);
        
        Assert.assertEquals(itemUpdate.getName(), itemToAdd.getName());        
    }
    
    @Test
    public void shouldReturnUpdatedProductWhenUpdating(){
    	String id = "1202";
    	ShoppingListItem itemToAdd = ProductHelperTest.getProductDummy("", id);
    	ShoppingListItem itemToCheck = ProductHelperTest.getProductDummy("", id);
        shoppingListRepository.addProduct(itemToAdd);
        itemToAdd.setName("Hund");
        shoppingListRepository.updateProduct(itemToAdd);
        
        itemToCheck = shoppingListService.getProduct(itemToAdd);
        
        Assert.assertEquals(itemToCheck.getName(), itemToAdd.getName());        
    }

    

    @Test
    public void shouldReturnTrueBecauseAddingProductDoesNotExitsInList(){
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW 123d", "123"));
        Assert.assertTrue(shoppingListService.addProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueIfDeletionOfProductWentWell(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        shoppingListRepository.addProduct(product);

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
        shoppingListRepository.addProduct(searchProduct);
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW 1", "123"));
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW 2", "456"));
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW 3", "789"));

        Assert.assertEquals(searchProduct.getId(), shoppingListService.getProduct(ProductHelperTest.getProductDummy("some name", searchId)).getId());
    }
    
    @Test
    public void shouldReturnAnUpdatedProduct(){
        ShoppingListItem itemToUpdate = ProductHelperTest.getProductDummy("BMW 123d", "2312");
        ShoppingListItem itemToFind = ProductHelperTest.getProductDummy("---", itemToUpdate.getId());
        shoppingListRepository.addProduct(itemToUpdate);
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW 330d", "312"));
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW M4", "123"));
        shoppingListRepository.addProduct(ProductHelperTest.getProductDummy("BMW M135i", "789"));        
        itemToUpdate.setName("Nissan");
        
        shoppingListRepository.updateProduct(itemToUpdate);
        
        itemToFind = shoppingListRepository.getProduct(itemToUpdate);
        
        Assert.assertEquals(itemToFind.getName(), itemToUpdate.getName());
    }

}
