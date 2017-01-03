package db_communication;

import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import domain.Product;
import freezers.MongoProvider;

/* 
 * Die Klasse DB_FridgeInventory schreibt alle Produkte inkl. aller Attribute (Anzahl, ID, Name) in die Datenbank,
 * kann sie auch wieder auslesen, sowie editieren und löschen.
 * 
 */
public class DB_FridgeInventory {
		
	@EJB
	private MongoProvider mongoProvider;
	
	// Method to Insert a Product
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void insertProductToDBInventory (Product product, int amount) {
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		MongoCollection<Document> products = db.getCollection("inventoryProducts");
		
		if (!productExistsInInventory(product)) {	
			Document doc = new Document ("id", product.getId())
					.append("price", product.getPreis())
					.append("name", product.getName())
					.append("packagingsize", product.getVerpackungsGroesse())
					.append("minimumstock", product.getMindestBestand())
					.append("maximumstock", product.getHoechstBestand())
					.append("actualamount", amount)
					.append("regularly", product.isRegelmaessig())
					.append("expirydate", product.getAblaufDatum());
			products.insertOne(doc);
		}
		else {
			updateProductFromDBInventory (product, amount);
		}
	}

	// Method to Search for a Product
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProductFromDBInventory (int productId) {
		// create Product variable for the return-statement
		Product product = null;
		
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection products = (DBCollection) db.getCollection("inventoryProducts");
		
		// create a query to search for the Product with the passed 'productd'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("id", productId);
		DBCursor cursor = products.find(whereQuery);
		
		// get the attributes for Product
		if (cursor.count() != 1) {
			System.out.print("Something went wrong! More than one products were found for the given Product-ID.");
		}
		else {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			String id = doc.getString("id");
			double preis = doc.getDouble("price");
			String name = doc.getString("name");
			int verpgroesse = doc.getInt("packagingsize");
			int mindbestand = doc.getInt("minimumstock");
			int hoechstbestand = doc.getInt("maximumstock");
			int aktuellerbestand = doc.getInt("actualamount");
			boolean regelmaessig = doc.getBoolean("regularly");
			Date ablaufdatum = doc.getDate("expirydate");
			product = new Product (preis, name, verpgroesse, mindbestand, hoechstbestand, 
					aktuellerbestand, regelmaessig, ablaufdatum, id);
		}
		return product;
	}
	
	// Method to get the actual amount for a Product
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public int getAmountForProduct (String productId) {
		// create Product variable for the return-statement
		int amount = -1;
			
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection products = (DBCollection) db.getCollection("inventoryProducts");
			
		// create a query to search for the Product with the passed 'productd'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("id", productId);
		DBCursor cursor = products.find(whereQuery);
		
		// get the amount for the Product
		if (cursor.count() != 1) {
			System.out.print("Something went wrong! More than one products were found for the given Product-ID.");
		}
		else {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			amount = doc.getInt("amount");
		}
		
		return amount;	
			
	}

	
	// Method to Update a Product
	@POST
	public void updateProductFromDBInventory (Product product, int amount) {
		int actualAmount = -1;
		
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection products = (DBCollection) db.getCollection("inventoryProducts");
		
		// check if the product exists
		if (productExistsInInventory(product)) {
			// get the amount and add the new amount
			actualAmount = getAmountForProduct(product.getId());
		}
		
		// if the amount is MORE or EQUAL 1 --> update the product
		if ((actualAmount + amount) >= 1) {
			int newAmount = actualAmount + amount;
			BasicDBObject updateProduct = new BasicDBObject("id", product.getId())
					.append("price", product.getPreis())
					.append("name", product.getName())
					.append("packagingsize", product.getVerpackungsGroesse())
					.append("minimumstock", product.getMindestBestand())
					.append("maximumstock", product.getHoechstBestand())
					.append("actualamount", amount)
					.append("regularly", product.isRegelmaessig())
					.append("expirydate", product.getAblaufDatum());
		
			BasicDBObject searchQuery = new BasicDBObject().append("id", product.getId());
			products.update(searchQuery, updateProduct);
		}
		// if the amount is EQUAL 0  --> delete the product from the inventory
		else if ((actualAmount + amount) == 0) {
			deleteProductFromDBInventory(product.getId());
		}
		// if the amount is LESS than 0 --> something went wrong! you cannot remove more than the actual amount
		else if ((actualAmount + amount) < 0) {
			throw new IllegalArgumentException ("There aren't enough products stored in the fridge for this removal!");
		}
 		
	}
	
	
	// Method to Delete a Product
	@DELETE
	public void deleteProductFromDBInventory (String productId) {
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection products = (DBCollection) db.getCollection("inventoryProducts");
		
		// create a query to get the product and delete it
		BasicDBObject deleteProduct= new BasicDBObject();
		
		// TODO check userid and if the product exists!
		
		deleteProduct.put("id", productId);
		products.remove(deleteProduct);
	}
	
	// Method to Check if a Product exists
	public boolean productExistsInInventory (Product product) {
		boolean ret = false;
		
		// create a client and get the database and productsCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection products = (DBCollection) db.getCollection("inventoryProducts");
				
		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkProduct= new BasicDBObject();
		checkProduct.put("id", product.getId());
		if (products.getCount(checkProduct) > 0)
			ret = true;
		return ret;
	}
	


}
