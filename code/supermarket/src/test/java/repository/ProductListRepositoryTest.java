package repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Model.Product;
import util.ProductListHelperTest;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListRepositoryTest {

	ProductListRepository repository;

	@Before
	public void setUp() {
		repository = new ProductListRepositoryStub();
	}

	@Test
	public void shouldAddNewProduct() {
		Product productToAdd = ProductListHelperTest.getProductListDummy("", "");
		Assert.assertTrue(repository.addProduct(productToAdd));
	}

	@Test
	public void shouldDeleteProduct() {
		Product productToDelete = ProductListHelperTest.getProductListDummy("", "");
		repository.addProduct(productToDelete);
		Assert.assertTrue(repository.deleteProduct(productToDelete));
	}

	@Test
	public void shouldFindAnExistingProduct() {
		Product productToFind = ProductListHelperTest.getProductListDummy("", "");
		repository.addProduct(productToFind);
		Assert.assertTrue(repository.existsProduct(productToFind));
	}

	@Test
	public void shouldFindOurProductOutOfAbigList() {
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		repository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		repository.addProduct(productTwo);

		Product productToFind = ProductListHelperTest.getProductListDummy("", "");
		repository.addProduct(productToFind);

		Assert.assertTrue(repository.existsProduct(productToFind));
	}

	@Test
	public void shouldFindOurProductAfterNameChanging() {
		String uniqueProductId = "IdUnique123";
		Product productToFind = ProductListHelperTest.getProductListDummy(uniqueProductId, "");
		repository.addProduct(productToFind);
		Product productChanged = ProductListHelperTest.getProductListDummy(uniqueProductId, "Kaiserpinguin");

		Assert.assertTrue(repository.existsProduct(productChanged));
	}

	@Test
	public void shouldReturnOurProductListWithThreeElements() {

		int productCount = 3;
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		repository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		repository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Känguru");
		repository.addProduct(productThree);

		Assert.assertEquals(productCount, repository.getAllProducts().size());
	}

	@Test
	public void shouldReturnProductListCountThree() {

		int productCount = 3;
		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löwe");
		repository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Eisbär");
		repository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Känguru");
		repository.addProduct(productThree);

		Assert.assertTrue(repository.countProducts() == productCount);
	}

	@Test
	public void shouldReturnAnUpdatedProduct() {
		Product itemToUpdate = ProductListHelperTest.getProductListDummy("BMW 123d", "2312");
		Product itemToFind = ProductListHelperTest.getProductListDummy("---", itemToUpdate.getId());
		repository.addProduct(itemToUpdate);
		repository.addProduct(ProductListHelperTest.getProductListDummy("BMW 330d", "312"));
		repository.addProduct(ProductListHelperTest.getProductListDummy("BMW M4", "123"));
		repository.addProduct(ProductListHelperTest.getProductListDummy("BMW M135i", "789"));
		itemToUpdate.setName("Nissan");

		repository.updateProduct(itemToUpdate);

		itemToFind = repository.getProduct(itemToUpdate);

		Assert.assertEquals(itemToFind.getName(), itemToUpdate.getName());

	}

}
