package controller;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import db_communication.DB_ProductList;

/**
 * @author Marius Koch
 *
 */

@Stateless
@Path("/productlist")
public class ProductListController {

	DB_ProductList db_productList;

	public void getProductList() {
		db_productList.getAllProducts();
	}
}
