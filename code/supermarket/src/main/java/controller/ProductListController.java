package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import Model.Product;
import db_communication.DB_ProductList;

/**
 * @author Marius Koch
 *
 */

@Stateless
@Path("/productlist")
public class ProductListController {

	DB_ProductList db_productList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductList() {
		return db_productList.getAllProducts();
	}
}
