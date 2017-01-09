package repository;

import java.util.HashSet;
import java.util.Set;

import domain.DatabaseProviderImpl;
import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;

import model.ShoppingListItem;
import util.ShoppingListHelper;

/**
 * Created by JD on 11.12.2016.
 */
public class ShoppingListRepositoryMongoImpl implements ShoppingListRepository {

	/**
	 * Internal usage of database provider
	 */
	private final DatabaseProviderImpl mongoProvider;

	/**
	 * Naming for "table" equivalent holding the data
	 */
	private static final String collectionName = ShoppingListHelper.collectionName;

	/**
	 * Constructor to set database for this implementation using MongoDB
	 * 
	 * @param provider
	 */
	public ShoppingListRepositoryMongoImpl(DatabaseProviderImpl provider) {
		this.mongoProvider = provider;
	}

	/**
	 * Encapsulation for getting the "table"
	 * 
	 * @return a Collection of MongoDB-Documents
	 */
	private MongoCollection<Document> getShoppingListCollection() {
		return mongoProvider.getCollection(collectionName);
	}

	/**
	 * Checks if a product is in the data
	 *
	 * @param product
	 *            to check
	 * @return true if the product is in the data
	 */
	@Override
	public boolean existsProduct(ShoppingListItem product) {
		try {
			return getShoppingListCollection()
					.count(new Document(ShoppingListHelper.documentShoppingListProductId, product.getId())) == 1;
		} catch (Exception e) {
			System.out.println("MongoConnection(exists): " + e.getMessage());
			return false;
		}
	}

	/**
	 * Saves a product in the data
	 *
	 * @param product
	 *            to add
	 * @return
	 */
	@Override
	public boolean addProduct(ShoppingListItem product) {
		try {
			getShoppingListCollection().insertOne(ShoppingListHelper.convertProductToDatabaseItem(product));
			return true;
		} catch (Exception e) {
			System.out.println("MongoConnection(insert): " + e.getMessage());
			return false;
		}
	}

	/**
	 * Delete a product from data. NO LOGICAL CHECK if the product is in data
	 *
	 * @param product
	 *            to delete
	 * @return true if the product was deleted
	 */
	@Override
	public boolean deleteProduct(ShoppingListItem product) {
		try {
			DeleteResult dr = getShoppingListCollection()
					.deleteOne(new Document(ShoppingListHelper.documentShoppingListProductId, product.getId()));
			return dr.wasAcknowledged() && dr.getDeletedCount() == 1;
		} catch (Exception e) {
			System.out.println("MongoConnection(delete): " + e.getMessage());
			return false;
		}
	}

	/**
	 * Returns all available products from data
	 *
	 * @return a map
	 */
	@Override
	public Set<ShoppingListItem> getAllProducts() {
		Set<ShoppingListItem> freezerProducts = new HashSet<>();
		getShoppingListCollection().find().forEach((Block<Document>) document -> {
			ShoppingListItem s = ShoppingListHelper.convertDatabaseItemToProduct(document);
			freezerProducts.add(s);
		});

		return freezerProducts;
	}

	/**
	 * Counting the rows of data
	 * 
	 * @return
	 */
	public long getProductCount() {
		return getShoppingListCollection().count();
	}

	/**
	 * Extracts a product out of data
	 *
	 * @param product
	 *            to find with further information
	 * @return
	 */
	@Override
	public ShoppingListItem getProduct(ShoppingListItem product) {
		final ShoppingListItem[] findProduct = new ShoppingListItem[1];
		getShoppingListCollection()
				.find(new Document(ShoppingListHelper.documentShoppingListProductId, product.getId()))
				.forEach((Block<Document>) document -> {
					ShoppingListItem s = ShoppingListHelper.convertDatabaseItemToProduct(document);
					final ShoppingListItem f = (ShoppingListItem) s;
					findProduct[0] = f;
				});
		return findProduct[0];
	}
}
