package db_communication;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import Model.Product;
import domain.MongoProvider;

@Stateless
public class DB_ProductList {

	@EJB
	MongoProvider mongoProvider;

	public List<Product> getAllProducts() {
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("supermarket");
		DBCollection products = (DBCollection) db.getCollection("productlist");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection
		List<Product> allProducts = new ArrayList<Product>();
		DBCursor cursor = products.find();

		while (cursor.hasNext()) {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			String id = doc.getString("id");
			String name = doc.getString("name");
			int verpackungsgroesse = doc.getInt("verpackungsgroesse");
			double price = doc.getDouble("price");
			allProducts.add(new Product(id, name, verpackungsgroesse, price));
		}
		return allProducts;
	}
}
