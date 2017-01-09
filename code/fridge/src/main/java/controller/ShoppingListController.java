package controller;

import java.text.ParseException;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingListController {
	
	@EJB
	DB_ShoppingList db_shoppingList;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllProductsFromShoppingList() throws ParseException {
		
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList();

		// TODO: (JD) Dummy entfernen
		/*
		 * Set<ShoppingListItem> shoppingListProducts = new
		 * HashSet<ShoppingListItem>(); shoppingListProducts.add(new
		 * ShoppingListItem("i1", 1.99d, "Belegte Banane", 1, 1, 2, true));
		 * shoppingListProducts.add(new ShoppingListItem("i2", 2.99d,
		 * "Abenteurlicher Apfel", 1, 5, 10, true));
		 * shoppingListProducts.add(new ShoppingListItem("i3", 458.99d,
		 * "Emmentaler", 1, -1, 1, false)); shoppingListProducts.add(new
		 * ShoppingListItem("i4", 0.45d, "Gouda", 1, -1, 2, false));
		 * shoppingListProducts.add(new ShoppingListItem("i4", 2.99d, "Eier", 6,
		 * 6, 10, true));
		 */
		System.out.println(shoppingListProducts);
		return new Gson().toJson(shoppingListProducts);
	}

	@DELETE
	@Path("{product}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean deleteProductFromShoppingList(@PathParam("product") String productId) {
		ShoppingListItem itemToDelete = new ShoppingListItem();
		itemToDelete.setId(productId);

		DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		return db_shoppingList.deleteProductFromShoppingList(itemToDelete);
		// TODO: (JD) Dummy entfernen
		// return true;
	}

}
