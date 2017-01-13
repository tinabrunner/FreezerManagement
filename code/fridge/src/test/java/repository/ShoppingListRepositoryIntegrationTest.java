package repository;

import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import model.ShoppingListItem;
import util.ShoppingListHelper;
import util.ProductHelperTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by JD on 16.12.2016.
 */

public class ShoppingListRepositoryIntegrationTest {


    private static final String databaseName = "fridgeIT";
    private ShoppingListRepository shoppingListRepository;
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
        getMongoConnection().getCollection(ShoppingListHelper.collectionName).drop();
    }

    @Test
    public void checkIfAddingFreezerProductWentWell(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        Assert.assertTrue(shoppingListRepository.addProduct(product));
    }

    @Test
    public void shouldReturnTrueIfProductAlreadyInShoppingList(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        shoppingListRepository.addProduct(product);

        Assert.assertTrue(shoppingListRepository.existsProduct(product));
    }

    @Test
    public void shouldReturnFalseIfProductIsNotInShoppingList(){
        Assert.assertFalse(shoppingListRepository.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueIfExistingProductIsDeleted(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        shoppingListRepository.addProduct(product);

        Assert.assertTrue(shoppingListRepository.deleteProduct(product));
    }

    @Test
    public void shouldReturnThreeAsProductCount(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("BMW 1", "1");
        shoppingListRepository.addProduct(product);
        product = ProductHelperTest.getProductDummy("BMW 2", "2");
        shoppingListRepository.addProduct(product);
        product = ProductHelperTest.getProductDummy("BMW 3", "3");
        shoppingListRepository.addProduct(product);

        Assert.assertTrue(shoppingListRepository.getProductCount() == 3);
    }

    @Test
    public void shouldReturnMapWithSizeThree(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("BMW 1", "1");
        shoppingListRepository.addProduct(product);
        product = ProductHelperTest.getProductDummy("BMW 2", "2");
        shoppingListRepository.addProduct(product);
        product = ProductHelperTest.getProductDummy("BMW 3", "3");
        shoppingListRepository.addProduct(product);

        Assert.assertEquals(3, shoppingListRepository.getAllProducts().size());
    }

    @Test
    public void shouldReturnTrueIfWeDeleteAnExistingProduct(){
        ShoppingListItem product = ProductHelperTest.getProductDummy("", "");
        shoppingListRepository.addProduct(product);

        Assert.assertTrue(shoppingListRepository.deleteProduct(product));
    }

    @Test
    public void shouldReturnFalseIfWeTryToDeleteAnonExistingProduct(){
        Assert.assertFalse(shoppingListRepository.deleteProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @After
    public void tearDown(){
        getMongoConnection().getCollection(ShoppingListHelper.collectionName).drop();
        getMongoConnection().disconnect();
    }


}



