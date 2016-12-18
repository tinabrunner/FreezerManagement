package repository.integration;

import FreezerManagementApplication;
import domain.Product;
import domain.MongoProvider;
import repository.ShoppingListRepository;
import repository.ShoppingListRepositoryMongoImpl;
import util.ShoppingListHelper;
import utils.ProductHelperTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by JD on 16.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FreezerManagementApplication.class)
public class ShoppingListRepositoryIT {

    private static final String databaseName = "freezer";
    private MongoProvider mongo;
    private ShoppingListRepository shoppingListRepository;

    @Before
    public void setUp(){
        mongo = new MongoProvider("localhost", 27017);
        mongo.setDatabaseName(databaseName);
        mongo.connect();

        mongo.getClient().getDatabase(databaseName).getCollection(ShoppingListHelper.collectionName).drop();

        shoppingListRepository = new ShoppingListRepositoryMongoImpl(mongo);
    }

    @Test
    public void checkIfAddingFreezerProductWentWell(){
        Product product = ProductHelperTest.getProductDummy("", "");
        int amount = 1;
        Assert.assertTrue(shoppingListRepository.addProduct(product, amount));
    }

    @Test
    public void shouldReturnTrueIfProductAlreadyInShoppingList(){
        Product product = ProductHelperTest.getProductDummy("", "");
        int amount = 1;
        shoppingListRepository.addProduct(product, amount);

        Assert.assertTrue(shoppingListRepository.existsProduct(product));
    }

    @Test
    public void shouldReturnFalseIfProductIsNotInShoppingList(){
        Assert.assertFalse(shoppingListRepository.existsProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @Test
    public void shouldReturnTrueIfExistingProductIsDeleted(){
        Product product = ProductHelperTest.getProductDummy("", "");
        int amount = 1;
        shoppingListRepository.addProduct(product, amount);

        Assert.assertTrue(shoppingListRepository.deleteProduct(product));
    }

    @Test
    public void shouldReturnThreeAsProductCount(){
        int amount = 1;
        Product product = ProductHelperTest.getProductDummy("BMW 1", "1");
        shoppingListRepository.addProduct(product, amount);
        product = ProductHelperTest.getProductDummy("BMW 2", "2");
        shoppingListRepository.addProduct(product, amount+1);
        product = ProductHelperTest.getProductDummy("BMW 3", "3");
        shoppingListRepository.addProduct(product, amount+2);

        Assert.assertEquals(3, shoppingListRepository.getProductCount());
    }

    @Test
    public void shouldReturnMapWithSizeThree(){
        int amount = 1;
        Product product = ProductHelperTest.getProductDummy("BMW 1", "1");
        shoppingListRepository.addProduct(product, amount);
        product = ProductHelperTest.getProductDummy("BMW 2", "2");
        shoppingListRepository.addProduct(product, amount+1);
        product = ProductHelperTest.getProductDummy("BMW 3", "3");
        shoppingListRepository.addProduct(product, amount+2);

        Assert.assertEquals(3, shoppingListRepository.getAllProducts().size());
    }

    @Test
    public void shouldReturnTrueIfWeDeleteAnExistingProduct(){
        int amount = 1;
        Product product = ProductHelperTest.getProductDummy("", "");
        shoppingListRepository.addProduct(product, amount);

        Assert.assertTrue(shoppingListRepository.deleteProduct(product));
    }

    @Test
    public void shouldReturnFalseIfWeTryToDeleteAnonExistingProduct(){
        Assert.assertFalse(shoppingListRepository.deleteProduct(ProductHelperTest.getProductDummy("", "")));
    }

    @After
    public void tearDown(){
        mongo.getDatabase().getCollection(ShoppingListHelper.collectionName).drop();
        mongo.getClient().close();
    }


}
