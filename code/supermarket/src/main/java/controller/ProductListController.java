package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;

import Model.Product;
import db_communication.DB_ProductList;

import java.util.List;

/**
 * @author Marius Koch
 *
 */

@Stateless
@Path("/productlist")
public class ProductListController {

	@EJB
	DB_ProductList db_productList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getProductList() {
		JSONArray arr = new JSONArray();
		List<Product> prods = db_productList.getAllProducts();
		for (Product p : prods) {
			arr.add(p);
		}
		return arr;
	}
}
