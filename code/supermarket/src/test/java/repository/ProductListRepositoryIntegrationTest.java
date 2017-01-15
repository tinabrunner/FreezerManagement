package repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Model.Product;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import util.ProductListHelper;
import util.ProductListHelperTest;

/*
 * @author: JD, 13.01.2017
 */

public class ProductListRepositoryIntegrationTest {

	private static final String databaseName = "supermarketIT";
	private ProductListRepositoryMongoImpl productListRepository;
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
		getMongoConnection().getCollection(ProductListHelper.collectionName).drop();
	}

	@Test
	public void shouldAddNewProduct() {
		Product productToAdd = ProductListHelperTest.getProductListDummy("", "");
		Assert.assertTrue(productListRepository.addProduct(productToAdd));
	}

	@Test
	public void shouldDeleteProduct() {
		Product productToDelete = ProductListHelperTest.getProductListDummy("", "");
		productListRepository.addProduct(productToDelete);
		Assert.assertTrue(productListRepository.deleteProduct(productToDelete));
	}

	@Test
	public void shouldFindAnExistingProduct() {
		Product productToFind = ProductListHelperTest.getProductListDummy("", "");
		productListRepository.addProduct(productToFind);
		Assert.assertTrue(productListRepository.existsProduct(productToFind));
	}

	@Test
	public void shouldFindOurProductOutOfAbigList() {
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		productListRepository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		productListRepository.addProduct(productTwo);

		Product productToFind = ProductListHelperTest.getProductListDummy("", "");
		productListRepository.addProduct(productToFind);

		Assert.assertTrue(productListRepository.existsProduct(productToFind));
	}

	@Test
	public void shouldFindOurProductAfterNameChanging() {
		String uniqueProductId = "IdUnique123";
		Product productToFind = ProductListHelperTest.getProductListDummy(uniqueProductId, "");
		productListRepository.addProduct(productToFind);
		Product productChanged = ProductListHelperTest.getProductListDummy(uniqueProductId, "Kaiserpinguin");

		Assert.assertTrue(productListRepository.existsProduct(productChanged));
	}

	@Test
	public void shouldReturnOurProductListWithThreeElements() {

		int productCount = 3;
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		productListRepository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		productListRepository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Känguru");
		productListRepository.addProduct(productThree);

		Assert.assertEquals(productCount, productListRepository.getAllProducts().size());
	}

	@Test
	public void shouldReturnProductListCountThree() {

		int productCount = 3;
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		productListRepository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		productListRepository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Känguru");
		productListRepository.addProduct(productThree);

		Assert.assertTrue(productListRepository.countProducts() == productCount);
	}

	@Test
	public void shouldReturnAnUpdatedProduct() {
		Product itemToUpdate = ProductListHelperTest.getProductListDummy("BMW 123d", "2312");
		Product itemToFind = ProductListHelperTest.getProductListDummy("---", itemToUpdate.getId());
		productListRepository.addProduct(itemToUpdate);
		productListRepository.addProduct(ProductListHelperTest.getProductListDummy("BMW 330d", "312"));
		productListRepository.addProduct(ProductListHelperTest.getProductListDummy("BMW M4", "123"));
		productListRepository.addProduct(ProductListHelperTest.getProductListDummy("BMW M135i", "789"));
		itemToUpdate.setName("Nissan");

		productListRepository.updateProduct(itemToUpdate);

		itemToFind = productListRepository.getProduct(itemToUpdate);

		Assert.assertEquals(itemToFind.getName(), itemToUpdate.getName());

	}

}
