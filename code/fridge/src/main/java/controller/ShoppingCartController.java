package controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.MongoProvider;
import model.ShoppingListItem;

/**
 * Created by phi on 08.12.16. .___. {o,o} /)___) --"-"--
 */
@Stateless
@Path("/shopping_cart")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartController {

	@EJB
	MongoProvider mongoProvider;

	@GET

	/** Shopping carts (Warenkörbe): non-persistent */
	public Set<ShoppingListItem> createShoopingCart() throws ParseException {

		// DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		// Map<Product, Integer> shoppingListProducts =
		// db_shoppingList.getAllProductsFromShoppingList(); // miau

		Set<ShoppingListItem> shoppingListProducts = new HashSet<>();
		shoppingListProducts.add(new ShoppingListItem("i1", 1.99d, "Belegte Banane", 1, 1, 2, true));
		shoppingListProducts.add(new ShoppingListItem("i2", 2.99d, "Abenteurlicher Apfel", 1, 5, 10, true));
		shoppingListProducts.add(new ShoppingListItem("i3", 458.99d, "Emmentaler", 1, -1, 1, false));
		shoppingListProducts.add(new ShoppingListItem("i4", 0.45d, "Gouda", 1, -1, 2, false));
		shoppingListProducts.add(new ShoppingListItem("i4", 2.99d, "Eier", 6, 6, 10, true));

		return shoppingListProducts;
	}
}
