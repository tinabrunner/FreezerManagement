package controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import Model.Product;

/*
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.json.simple.JSONArray;
import db_communication.DB_ProductList;
import java.util.List;
*/

/**
 * @author Marius Koch
 * @author JD - 10.01.2017 - Testdummy
 */
// @Stateless
@Path("/productlist")
public class ProductListController {

	/*
	 * @EJB DB_ProductList db_productList;
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public JSONArray getProductList() {
	 * JSONArray arr = new JSONArray(); List<Product> prods =
	 * db_productList.getAllProducts(); for (Product p : prods) { arr.add(p); }
	 * return arr; }
	 */

	@GET
	@Produces(MediaType.APPLICATION_JSON)
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

}
