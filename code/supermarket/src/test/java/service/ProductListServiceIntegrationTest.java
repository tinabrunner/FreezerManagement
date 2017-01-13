package service;

import org.junit.Before;
import org.junit.Test;

import Model.Product;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import junit.framework.Assert;
import repository.ProductListRepositoryMongoImpl;
import util.ProductListHelper;
import util.ProductListHelperTest;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListServiceIntegrationTest {

	private static final String databaseName = "supermarketIT";
	private ProductListRepositoryMongoImpl productListRepository;
	private ProductListServiceImpl productListService;
	private MongoProvider mongoProvider;
	private DatabaseProviderImpl mongoConnection;

	private DatabaseProviderImpl getMongoConnection() {
		DatabaseProviderImpl mongoDB = new DatabaseProviderImpl(this.mongoProvider);
		mongoDB.setDatabaseName(databaseName);
		mongoDB.connect();

		return mongoDB;
	}

	@Before
	public void setUp() {
		mongoProvider = new MongoProvider();
		mongoProvider.init();
		mongoConnection = new DatabaseProviderImpl(mongoProvider);

		productListRepository = new ProductListRepositoryMongoImpl(getMongoConnection());
		productListService = new ProductListServiceImpl(productListRepository);
		getMongoConnection().getCollection(ProductListHelper.collectionName).drop();
	}

	@Test
	public void shouldAddNewProduct() {
		Product productToAdd = ProductListHelperTest.getProductListDummy("", "");
		Assert.assertTrue(productListService.addProduct(productToAdd));
	}

	@Test
	public void shouldDeleteProduct() {
		Product productToDelete = ProductListHelperTest.getProductListDummy("", "");
		productListService.addProduct(productToDelete);
		Assert.assertTrue(productListService.deleteProduct(productToDelete));
	}

	@Test
	public void shouldReturnTrueForExistingProduct() {
		Product productExists = ProductListHelperTest.getProductListDummy("", "");
		productListService.addProduct(productExists);
		Assert.assertTrue(productListService.existsProduct(productExists));
	}

	@Test
	public void shouldReturnProductListWithThreeElements() {

		int productCount = 3;

		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Tasse");
		productListService.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Teller");
		productListService.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Teekanne");
		productListService.addProduct(productThree);

		Assert.assertEquals(productCount, productListService.getAllProducts().size());
	}

	@Test
	public void shouldReturnProductListCountThree() {

		int productCount = 3;

		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löffel");
		productListService.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Laubbaum");
		productListService.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Baumstamm");
		productListService.addProduct(productThree);

		Assert.assertEquals(productCount, productListService.countProducts());
	}

}
