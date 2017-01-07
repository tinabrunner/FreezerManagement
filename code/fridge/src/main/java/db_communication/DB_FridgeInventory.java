package db_communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import domain.MongoProvider;
import model.InventoryProduct;

/* 
 * Die Klasse DB_FridgeInventory schreibt alle Produkte inkl. aller Attribute (Anzahl, ID, Name) in die Datenbank,
 * kann sie auch wieder auslesen, sowie editieren und löschen.
 * 
 */

/*
 * 
 * Kleiner Tipp: NIEMALS, N I E M A L S auf "getippte" Zeichenketten gehen. Daf�r bitte eine �bersetzungstabelle anlegen.
 * siehe util.ShoppingListHelper (e.g. documentShoppingListProductId = "id", klar?)
 */
public class DB_FridgeInventory {

	@EJB
	private MongoProvider mongoProvider;// = new MongoProvider();

	private static final String _frigde = "fridge";
	private static final String _inventoryProducts = "inventoryProducts";
	private static final String _prodCategoryID = "prodCategoryID";
	private static final String _name = "name";
	private static final String _expiryDate = "expiryDate";
	private static final String _username = "username";
	private static final String _id = "_id";

	/*
	 * STANDARDMETHODS FOR RE-USE
	 */

	// create a client and get the database and productsCollection
	private MongoCollection<Document> getProductsCollection() {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(_frigde);
		MongoCollection<Document> products = db.getCollection(_inventoryProducts);
		// DBCollection users = (DBCollection) db.getCollection("users");
		return products;
	}

	private Document convertInventoryProdToDocument(InventoryProduct product) {
		Document doc = new Document(_prodCategoryID, product.getProdCategoryId()).append(_name, product.getName())
				.append(_expiryDate, product.getExpiryDate());
		// TODO: Hier noch den Username aus HTTP-Header auslesen und der DB
		// mitgeben !!!
		return doc;
	}

	private InventoryProduct convertDocumentToInventoryProd(Document doc) {
		String prodCategoryID = doc.getString(_prodCategoryID);
		String name = doc.getString(_name);
		Date expiryDate = doc.getDate(_expiryDate);
		InventoryProduct prod = new InventoryProduct(prodCategoryID, name, expiryDate);
		return prod;
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
	public void deleteProductFromDBInventory(String id) {
		MongoCollection<Document> products = getProductsCollection();

		Bson filter = Filters.eq(_id, id);
		products.findOneAndDelete(filter);
	}

	// Method to get all Product from Inventory
	public List<InventoryProduct> getAllProducts() {
		MongoCollection<Document> products = getProductsCollection();
		List<InventoryProduct> allProducts = new ArrayList<InventoryProduct>();

		FindIterable<Document> search = products.find();
		for (Document current : search) {
			allProducts.add(convertDocumentToInventoryProd(current));
		}

		return allProducts;
	}

}
