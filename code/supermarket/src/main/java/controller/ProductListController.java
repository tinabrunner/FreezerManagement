package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import Model.Product;
import db_communication.DB_ProductList;

/**
 * @author JD - 13.01.2017, saubere Umstellung auf Service/Repository
 */

@Stateless
@Path("/productlist")
@Produces(MediaType.APPLICATION_JSON)
public class ProductListController {

	@EJB
	private DB_ProductList db_productList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Retun all Products from the supermarket
	 * 
	 * @return a list with all product entities
	 */
	public List<Product> getProductList() {
		List<Product> prods = new ArrayList<>();
		try {
			prods = this.db_productList.getAllProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prods;
	}

	@PUT
	/**
	 * Adding or updating a product entity in the supermarket
	 * 
	 * @param product
	 *            to add/update
	 * @return true if succeeded
	 */
	public Boolean addProduct(String product) {
		Product productToAdd = new Gson().fromJson(product, Product.class);
		return db_productList.addSingleProduct(productToAdd);
	}

	@DELETE
	@Path("{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Delete a product entity from the supermarket
	 * 
	 * @param productId
	 *            of product to delete
	 * @return true if deleted
	 */
	public Boolean deleteProduct(@PathParam("productId") String productId) {
		Product productToDelete = new Product();
		productToDelete.setId(productId);
		return db_productList.deleteProduct(productToDelete);
	}

	@GET
	@Path("/checkId/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Check if a given product id is already in use
	 * 
	 * @param productId
	 *            to check
	 * @return true if the id already exists
	 */
	public Boolean checkProductId(@PathParam("productId") String productId) {
		Product productToCheck = new Product();
		productToCheck.setId(productId);
		return db_productList.existsProduct(productToCheck);
	}

}
