package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import Model.Product;
import db_communication.DB_ProductList;

/**
 * @author Marius Koch
 * @author JD - 10.01.2017 - Testdummy
 * @author phi mongo impl
 */
// @Stateless
@Path("/productlist")
@Produces(MediaType.APPLICATION_JSON)
public class ProductListController {
	
	@EJB
	private DB_ProductList db_productList;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getProductList() {
		List<Product> prods = new ArrayList<>();
		try {
			prods = this.db_productList.getAllProducts();
		} catch (Exception e) {
			
		}
		if (prods.isEmpty()) {
				/*
				 * OHNE Id kann in der DB keine CRUD-Operation gemacht werden
				 * Also: ANGEBEN!
				 */
			List<Product> dummies = new ArrayList<>();
			dummies.add(new Product("idEier", "Eier", 10, 2.99, 150));
			dummies.add(new Product("idSchinken", "Schinken", 5, 3.56, 450));
			dummies.add(new Product("idTomaten", "Tomaten", 8, 2.69, 80));
			dummies.add(new Product("idJoghurt", "Joghurt", 1, 1.29, 0));
			prods = dummies;
			
			try {
				this.db_productList.addProducts(dummies);
			} catch(Exception e1) {
				
			}
		}
		return new Gson().toJson(prods);
	}

	/*
	 * @GET
	 * 
	 * public String getProductList() {
	 * 
	 * // Testdummy Set<Product> myDummyProducts = new HashSet();
	 * 
	 * Product prodDummy = new Product("idEier", "Eier", 10, 2.99);
	 * prodDummy.setCalories(150); myDummyProducts.add(prodDummy); prodDummy =
	 * new Product("idSchinken", "Schinken", 5, 3.56);
	 * prodDummy.setCalories(450); myDummyProducts.add(prodDummy); prodDummy =
	 * new Product("idTomaten", "Tomaten", 8, 2.69); prodDummy.setCalories(80);
	 * myDummyProducts.add(prodDummy); prodDummy = new Product("idJoghurt",
	 * "Joghurt", 1, 1.29); prodDummy.setCalories(0);
	 * myDummyProducts.add(prodDummy);
	 * 
	 * return new Gson().toJson(myDummyProducts); }
	 */
	
}
