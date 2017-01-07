package db_communication;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.MongoProvider2;
import model.ShoppingListItem;
import repository.ShoppingListRepositoryMongoImpl;
//import repository.ShoppingListRepositoryMongoImpl;
import service.ShoppingListServiceImpl;

import java.util.Set;

@Stateless
public class DB_ShoppingList {

	@EJB
	MongoProvider2 mongoProvider;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/shoppinglist")
	public Set<ShoppingListItem> getAllProductsFromShoppingList () {
		
		this.mongoProvider = new MongoProvider2("localhost", 27017); // TODO ?
		this.mongoProvider.setDatabaseName("fridge");
		this.mongoProvider.connect();

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(this.mongoProvider);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);
		return shoppingListService.getAllProducts();
	}	
}
