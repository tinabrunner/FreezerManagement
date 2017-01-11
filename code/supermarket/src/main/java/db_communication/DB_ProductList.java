package db_communication;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import domain.DatabaseProviderImpl;
import org.bson.Document;

import com.mongodb.client.MongoCollection;

import Model.Product;
import domain.MongoProvider;

@Stateless
public class DB_ProductList {

	@EJB
	MongoProvider mongoProvider;
	
	DatabaseProviderImpl db;
	
	@PostConstruct
	public void init() {
		db = new DatabaseProviderImpl( this.mongoProvider );
		db.setDatabaseName("supermarket");
		db.connect();
	}
	
	public List<Product> getAllProducts() {
		
		MongoCollection<Document> products = db.getCollection("productlist");
		
		// Create a list for all invoices and get a cursor to go through the
		// DBCollection
		List<Product> allProducts = new ArrayList<Product>();

		/*
		products.find().forEach((Block<Document>) doc -> {
			String id = doc.getString("id");
			String name = doc.getString("name");
			int verpackungsgroesse = doc.getInteger("verpackungsgroesse");
			double price = doc.getDouble("price");
			allProducts.add(new Product(id, name, verpackungsgroesse, price));
		}); */
		for( Document doc: products.find() ) {
			String id = doc.getObjectId ("_id").toHexString();
			String name = doc.getString("name");
			int verpackungsgroesse = doc.getInteger("verpackungsgroesse");
			double price = doc.getDouble("price");
			int calories = doc.getInteger("calories");
			allProducts.add(new Product(id, name, verpackungsgroesse, price, calories));
		};
		return allProducts;
	}
	
	public void addProducts( List<Product> products) {
		
		MongoCollection<Document> col = db.getCollection("productlist");
	
		List<Document> docs = new ArrayList<>();
		for( Product prod : products ) {
			Document doc =  new Document("name", prod.getName())
					.append("verpackungsgroesse", prod.getVerpackungsgroesse())
					.append("price", prod.getPreis())
					.append("calories", prod.getCalories());
			docs.add(doc);
		};
		
		col.insertMany(docs);
	}
}
