package service;

import org.junit.Before;
import org.junit.Test;

import Model.Product;
import junit.framework.Assert;
import repository.ProductListRepositoryStub;
import util.ProductListHelperTest;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListServiceTest {

	private ProductListRepositoryStub repository;
	private ProductListServiceImpl productListService;

	@Before
	public void setUp() {
		repository = new ProductListRepositoryStub();
		productListService = new ProductListServiceImpl(repository);
	}

	@Test
	public void shouldAddNewProduct() {
		Product productToAdd = ProductListHelperTest.getProductListDummy("", "");
		Assert.assertTrue(productListService.addProduct(productToAdd));
	}

	@Test
	public void shouldDeleteProduct() {
		Product productToDelete = ProductListHelperTest.getProductListDummy("", "");
		repository.addProduct(productToDelete);
		Assert.assertTrue(productListService.deleteProduct(productToDelete));
	}

	@Test
	public void shouldReturnTrueForExistingProduct() {
		Product productExists = ProductListHelperTest.getProductListDummy("", "");
		repository.addProduct(productExists);
		Assert.assertTrue(productListService.existsProduct(productExists));
	}

	@Test
	public void shouldReturnProductListWithThreeElements() {

		int productCount = 3;

		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Tasse");
		repository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Teller");
		repository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Teekanne");
		repository.addProduct(productThree);

		Assert.assertEquals(productCount, productListService.getAllProducts().size());
	}

	@Test
	public void shouldReturnProductListCountThree() {

		int productCount = 3;

		Product productOne = ProductListHelperTest.getProductListDummy("IdProd1", "Löffel");
		repository.addProduct(productOne);
		Product productTwo = ProductListHelperTest.getProductListDummy("IdProd2", "Laubbaum");
		repository.addProduct(productTwo);
		Product productThree = ProductListHelperTest.getProductListDummy("IdProd3", "Baumstamm");
		repository.addProduct(productThree);

		Assert.assertEquals(productCount, productListService.countProducts());
	}

}
