package controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db_communication.DB_FridgeInventory;
import db_communication.DB_ShoppingList;
import domain.MongoProvider;
import domain.MongoProvider2;
import model.InventoryProduct;
import model.ShoppingListItem;
import org.bson.Document;
import repository.ShoppingListRepositoryMongoImpl;
import service.ShoppingListServiceImpl;
import util.ShoppingListHelper;

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
	/** Shopping carts (Warenkörbe): non-persistent */
	public Set<ShoppingListItem> createShoopingCart() throws ParseException {

		/* current items in shopping list */
		DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau
		
		/* current items in inventory: <categoryId, inventoryProduct> */
		DB_FridgeInventory db_fridgeInventory = new DB_FridgeInventory();
		Map<String, InventoryProduct> inventoryProducts = db_fridgeInventory.getAllProducts();
		
/*
		MongoProvider2 mongoProvider = new MongoProvider2("localhost", 27017);
		mongoProvider.setDatabaseName("fridge");
		mongoProvider.connect();
		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(mongoProvider);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);
		shoppingListService.addProduct(new ShoppingListItem("i1", 1.99d, "Belegte Banane", 1, 1, 2, true));
		shoppingListService.addProduct(new ShoppingListItem("i2", 2.99d, "Abenteurlicher Apfel", 1, 5, 10, true));
		shoppingListService.addProduct(new ShoppingListItem("i3", 458.99d, "Emmentaler", 1, -1, 1, false));
		shoppingListService.addProduct(new ShoppingListItem("i4", 0.45d, "Gouda", 1, -1, 2, false));
		shoppingListService.addProduct(new ShoppingListItem("i4", 2.99d, "Eier", 6, 6, 10, true));
*/
		return shoppingListProducts;
	}
}
