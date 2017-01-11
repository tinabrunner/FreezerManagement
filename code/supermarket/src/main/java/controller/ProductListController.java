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

/**
 * @author Marius Koch
 *
 */

@Stateless
@Path("/productlist")
public class ProductListController {

	@EJB
	private DB_ProductList db_productList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getProductList() {
		JSONArray arr = new JSONArray();
		for (Product p : db_productList.getAllProducts()) {
			arr.add(p);
		}
		return arr;
	}
}
