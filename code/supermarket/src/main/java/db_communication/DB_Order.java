package db_communication;

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Model.Order;
import Model.Product;
import domain.MongoProvider;

public class DB_Order {

	@EJB
	private MongoProvider mongoProvider;

	public void insertOrderToDB(Order order) {
		// create a client and get the database and invoicesCollection
		mongoProvider = new MongoProvider("localhost", 27017);
		mongoProvider.setDatabaseName("supermarket");
		mongoProvider.connect();
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("supermarket");
		MongoCollection<Document> orders = db.getCollection("orders");

		if (!orderExist(order.getId())) {
			Document doc = new Document("id", order.getId()).append("recieveDate", order.getRecieveDate())
					.append("totalPrice", order.getTotalPrice()).append("customerId", order.getCustomerId())
					.append("order", order.getOrder());
			orders.insertOne(doc);
		} else
			System.out.print("Invoice already exists!");
	}

	public boolean orderExist(String id) {
		boolean ret = false;

		// create a client and get the database and invoicesCollection
		mongoProvider = new MongoProvider("localhost", 27017);
		mongoProvider.setDatabaseName("supermarket");
		mongoProvider.connect();
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("supermarket");
		MongoCollection<Document> orders = db.getCollection("orders");

		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkOrder = new BasicDBObject();
		checkOrder.put("id", id);
		if (orders.count(checkOrder) > 0)
			ret = true;
		return ret;
	}

	public Order getOrder(String id) {
		mongoProvider = new MongoProvider("localhost", 27017);
		mongoProvider.setDatabaseName("supermarket");
		mongoProvider.connect();
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("supermarket");
		MongoCollection<Document> orders = db.getCollection("orders");

		// Create Invoice-Element for return-statement
		Order order = null;

		// create a query to search for the Invoice with the passed 'id'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("id", id);

		FindIterable<Document> cursor = orders.find(whereQuery);
		// get the attributes for invoice
		if (((DBCollection) cursor).count() != 1) {
			System.out.print("Something went wrong! More than one invoice was found for the given id.");
		} else {
			Document doc = (Document) orders.find(whereQuery);
			Date receiveDate = doc.getDate("receiveDate");
			double totalPrice = doc.getDouble("totalPrice");
			String customerId = doc.getString("customerId");
			Map<Product, Integer> order1 = (Map<Product, Integer>) doc.get("order");
			order = new Order(id, receiveDate, totalPrice, customerId, order1);
		}
		return order;
	}
}
