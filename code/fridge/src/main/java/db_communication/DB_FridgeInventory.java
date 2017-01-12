package db_communication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import domain.MongoProvider;
import model.InventoryProduct;

/**
 * @author Christina Brunner
 */

@Stateless
public class DB_FridgeInventory {

	@EJB
	private MongoProvider mongoProvider;

	private static final String _fridge = "fridge";
	private static final String _inventoryProducts = "inventoryProducts";
	public static final String _prodCategoryId = "prodCategoryId";
	public static final String _name = "name";
	public static final String _expiryDate = "expiryDate";
	public static final String _username = "username";
	public static final String _id = "_id";

	/*
	 * STANDARDMETHODS FOR RE-USE
	 */

	// create a client and get the database and productsCollection
	private MongoCollection<Document> getProductsCollection() {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(_fridge);

		MongoCollection<Document> products = db.getCollection(_inventoryProducts);
		// DBCollection users = (DBCollection) db.getCollection("users");

		return products;
	}

	private Document convertInventoryProdToDocument(InventoryProduct product) {
		Document doc = new Document(_prodCategoryId, product.getProdCategoryId()).append(_name, product.getName())
				.append(_expiryDate, product.getExpiryDate());
		// TODO: Hier noch den Username aus HTTP-Header auslesen und der DB
		// mitgeben !!!
		return doc;
	}

	private Map<String, InventoryProduct> convertDocumentToInventoryProd(Document doc) {
		String prodCategoryID = doc.getString(_prodCategoryId);
		String name = doc.getString(_name);
		Date expiryDate = doc.getDate(_expiryDate);
		InventoryProduct prod = new InventoryProduct(prodCategoryID, name, expiryDate);
		String id = doc.getObjectId(_id).toHexString();
		Map<String, InventoryProduct> ret = new HashMap<String, InventoryProduct>();
		ret.put(id, prod);
		return ret;
	}

	/*
	 * METHODS TO COMMUNICATE WITH DB
	 */

	// Method to Insert a Product
	public void insertProductToDBInventory(InventoryProduct product) {
		MongoCollection<Document> products = getProductsCollection();
		Document doc = convertInventoryProdToDocument(product);
		products.insertOne(doc);
	}

	// Method to Delete a Product
	public boolean deleteProductFromDBInventory(String id) {
		MongoCollection<Document> products = getProductsCollection();

		if (productExists(id)) {
			ObjectId objId = new ObjectId(id);
			Bson filter = Filters.eq(_id, objId);
			products.findOneAndDelete(filter);
			return true;
		} else
			return false;
	}

	// Method to Search for a Product
	public boolean productExists(String id) {
		MongoCollection<Document> products = getProductsCollection();
		ObjectId objId = new ObjectId(id);
		Bson filter = Filters.eq(_id, objId);
		if (products.count(filter) > 0)
			return true;
		else
			return false;
	}

	// Method to get all Product from Inventory
	public Map<String, InventoryProduct> getAllProducts() {
		MongoCollection<Document> products = getProductsCollection();
		Map<String, InventoryProduct> allProducts = new HashMap<String, InventoryProduct>();

		FindIterable<Document> search = products.find();
		for (Document current : search) {
			allProducts.putAll(convertDocumentToInventoryProd(current));
		}

		return allProducts;
	}

}
