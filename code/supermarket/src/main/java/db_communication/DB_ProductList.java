package db_communication;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Model.Product;
import domain.MongoProvider;

@Stateless
public class DB_ProductList {

	@EJB
	MongoProvider mongoProvider;

	public List<Product> getAllProducts() {
		mongoProvider = new MongoProvider("localhost", 27017);
		mongoProvider.setDatabaseName("supermarket");
		mongoProvider.connect();
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("supermarket");
		MongoCollection<Document> products = db.getCollection("productlist");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection
		List<Product> allProducts = new ArrayList<Product>();

		products.find().forEach((Block<Document>) doc -> {
			String id = doc.getString("id");
			String name = doc.getString("name");
			int verpackungsgroesse = doc.getInteger("verpackungsgroesse");
			double price = doc.getDouble("price");
			allProducts.add(new Product(id, name, verpackungsgroesse, price));
		});
		return allProducts;
	}
}
