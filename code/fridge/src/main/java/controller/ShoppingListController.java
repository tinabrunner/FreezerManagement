package controller;

import java.text.ParseException;
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

import db_communication.DB_ShoppingList;
import model.ShoppingListItem;

/*
 * Created by JD; 08.01.2017
 */

@Stateless
@Path("/shoppinglist")
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingListController {

	@EJB
	DB_ShoppingList db_shoppingList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingListItem> getAllProductsFromShoppingList() throws ParseException {
		List<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList();
		return shoppingListProducts;
	}

	@DELETE
	@Path("{product}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean deleteProductFromShoppingList(@PathParam("product") String productId) {
		ShoppingListItem itemToDelete = new ShoppingListItem();
		itemToDelete.setId(productId);
		return db_shoppingList.deleteProductFromShoppingList(itemToDelete);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean addItem(String item) {
		ShoppingListItem shoppingListItem = new Gson().fromJson(item, ShoppingListItem.class);
		return db_shoppingList.addProductToShoppingList(shoppingListItem);
	}

	@GET
	@Path("/lordgiveusdata")
	@Produces(MediaType.APPLICATION_JSON)
	public void generateRealWorldData() {
		ShoppingListItem item = new ShoppingListItem();
		item.setId("1");
		item.setName("RealWorld 1");
		item.setPreis(9.99);
		item.setRegelmaessig(false);
		db_shoppingList.addProductToShoppingList(item);

		ShoppingListItem item2 = new ShoppingListItem();
		item2.setId("2");
		item2.setName("RealWorld 2");
		item2.setPreis(99.99);
		item2.setRegelmaessig(true);
		db_shoppingList.addProductToShoppingList(item2);
	}

}
