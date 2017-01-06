package controller;

import db_communication.DB_ShoppingList;
import domain.MongoProvider;
import domain.MongoProvider2;
import domain.Product;
import repository.ShoppingListRepositoryMongoImpl;
import service.ShoppingListServiceImpl;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

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
	public String createShoopingCart() {
		
		DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		// Map<Product, Integer> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau
		
		// bsp
		/*MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		MongoCollection<Document> col = db.getCollection("shoppingListProducts");
		Document d = col.find().first();
		return d.toJson();*/
		
		return "123";
	}
}



