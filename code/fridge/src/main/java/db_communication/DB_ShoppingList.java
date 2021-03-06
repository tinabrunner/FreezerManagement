package db_communication;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import model.ShoppingListItem;
import repository.ShoppingListRepositoryMongoImpl;
import service.ShoppingListServiceImpl;

/**
 * 
 * @author JD
 *
 */
@Stateless
public class DB_ShoppingList {

	private static final String MONGO_DB = "fridge";

	@EJB
	private MongoProvider mongoProvider;

	private DatabaseProviderImpl mongoConnection;

	private DatabaseProviderImpl getMongoConnection() {
		DatabaseProviderImpl mongoDB = new DatabaseProviderImpl(this.mongoProvider);
		mongoDB.setDatabaseName(MONGO_DB);
		mongoDB.connect();

		return mongoDB;
	}

	@PostConstruct
	public void init() {
		this.mongoConnection = getMongoConnection();
	}

	// GET
	public List<ShoppingListItem> getAllProductsFromShoppingList() {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);

		return shoppingListService.getAllProducts();

	}

	// DELETE
	public Boolean deleteProductFromShoppingList(ShoppingListItem item) {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);

		Boolean result = shoppingListService.deleteProduct(item);

		return result;
	}

	// ADD
	public Boolean addProductToShoppingList(ShoppingListItem item) {

		ShoppingListRepositoryMongoImpl shoppingListRepositoryMongo = new ShoppingListRepositoryMongoImpl(
				this.mongoConnection);
		ShoppingListServiceImpl shoppingListService = new ShoppingListServiceImpl(shoppingListRepositoryMongo);
		Boolean result = shoppingListService.addProduct(item);

		return result;
	}
}
