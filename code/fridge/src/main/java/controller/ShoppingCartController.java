package controller;

import domain.MongoProvider;
import domain.Product;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by phi on 08.12.16.
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
@Path("/shopping_cart")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartController {
	
	@EJB
	MongoProvider mongoProvider;
	
	@GET
	/** Shopping carts (Warenkörbe):  non-persistent */
	public Product createShoopingCart() throws ParseException {
		
		//DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		//Map<Product, Integer> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau
		
		Set<Product> shoppingListProducts = new HashSet<>();
		shoppingListProducts.add(new Product(1.99d,"Belegte Banane", 1,
				1, 2, 9999,
				true, null, "1"));
		shoppingListProducts.add(new Product(2.99d,"Abenteurlicher Apfel", 1,
				5, 10, 9999,
				true, null, "2"));
		shoppingListProducts.add(new Product(458.99d,"Emmentaler", 1,
				-1, 1, 9999,
				false, null, "3"));
		shoppingListProducts.add(new Product(0.45d,"Gouda", 1,
				-1, 2, 9999,
				false, null, "4"));
		shoppingListProducts.add(new Product(2.99d,"Eier", 6,
				6, 10, 9999,
				true, null, "5"));
		
		//return shoppingListProducts.toArray(new Product[]{})[0];
		return new Product(458.99d,"Emmentaler", 1,
				-1, 1, 9999,
				false, null, "3");
	}
}



