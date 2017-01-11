package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.Product;
import db_communication.DB_ProductList;

/**
 * @author Marius Koch
 * @author JD - 10.01.2017 - Testdummy
 * @author phi mongo impl
 */
@Stateless
@Path("/productlist")
@Produces(MediaType.APPLICATION_JSON)
public class ProductListController {
	
	
	@EJB
	DB_ProductList db_productList;
	
	@GET
	public List<Product> getProductList() {
		List<Product> prods = db_productList.getAllProducts();
		
		if(prods.isEmpty()) {
			List<Product> dummies = new ArrayList<>();
			dummies.add( new Product("", "Eier", 10, 2.99, 150) );
			dummies.add( new Product("", "Schinken", 5, 3.56,450));
			dummies.add( new Product("", "Tomaten", 8, 2.69, 80));
			dummies.add( new Product( "","Joghurt", 1, 1.29, 0));
			db_productList.addProducts(dummies);
			prods = db_productList.getAllProducts();
		}
	
		return prods;
	}
	
	/*
	@GET
	
	public String getProductList() {

		// Testdummy
		Set<Product> myDummyProducts = new HashSet();

		Product prodDummy = new Product("idEier", "Eier", 10, 2.99);
		prodDummy.setCalories(150);
		myDummyProducts.add(prodDummy);
		prodDummy = new Product("idSchinken", "Schinken", 5, 3.56);
		prodDummy.setCalories(450);
		myDummyProducts.add(prodDummy);
		prodDummy = new Product("idTomaten", "Tomaten", 8, 2.69);
		prodDummy.setCalories(80);
		myDummyProducts.add(prodDummy);
		prodDummy = new Product("idJoghurt", "Joghurt", 1, 1.29);
		prodDummy.setCalories(0);
		myDummyProducts.add(prodDummy);

		return new Gson().toJson(myDummyProducts);
	}
	*/
	
}
