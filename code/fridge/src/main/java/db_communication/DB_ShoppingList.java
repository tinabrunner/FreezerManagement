package db_communication;

import java.util.Set;

import javax.ejb.Stateless;

import domain.MongoProvider2;
import model.ShoppingListItem;
import repository.ShoppingListRepositoryMongoImpl;
//import repository.ShoppingListRepositoryMongoImpl;
import service.ShoppingListServiceImpl;

@Stateless
public class DB_ShoppingList {

	private static final String MONGO_HOST = "localhost";
	private static final int MONGO_PORT = 27017;
	private static final String MONGO_DB = "fridge";

	private MongoProvider2 mongoConnection;

	private MongoProvider2 getMongoConnection() {
		MongoProvider2 mongoDB = new MongoProvider2(MONGO_HOST, MONGO_PORT);
		mongoDB.setDatabaseName(MONGO_DB);
		mongoDB.connect();

		return mongoDB;
	}

	public DB_ShoppingList() {
		this.mongoConnection = getMongoConnection();
	}

	// GET
	public Set<ShoppingListItem> getAllProductsFromShoppingList() {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);

		this.mongoConnection.disconnect();

		return shoppingListService.getAllProducts();
	}

	// DELETE
	public Boolean deleteProductFromShoppingList(ShoppingListItem item) {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);

		this.mongoConnection.disconnect();

		return shoppingListService.deleteProduct(item);
	}

	// ADD
	public Boolean addProductToShoppingList(ShoppingListItem item) {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);

		this.mongoConnection.disconnect();

		return shoppingListService.addProduct(item);
	}

}
