package db_communication;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import Model.ProcessedOrder;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import Model.Order;
import Model.Product;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;

@Stateless
public class DB_Order {

	@EJB
	MongoProvider mongoProvider;

	DatabaseProviderImpl db;

	@PostConstruct
	public void init() {
		db = new DatabaseProviderImpl(this.mongoProvider);
		db.setDatabaseName("supermarket");
		db.connect();
	}

	public void insertOrderToDB(ProcessedOrder order) {

		MongoCollection<Document> orders = db.getCollection("orders");

		if (order.getId() == null) {
			int id = Integer.parseInt(this.getLastId());
			id = id + 1;
			String newId = Integer.toString(id);

			order.setId(newId);
		}

		if (!orderExist(order.getId())) {
			Document doc = new Document("id", order.getId()).append("recieveDate", order.getRecieveDate())
					.append("totalPrice", order.getTotalPrice()).append("customerId", order.getCustomerId())
					.append("items", order.getItemsProcessed());
			orders.insertOne(doc);
		} else
			System.out.print("Invoice already exists!");
	}

	public boolean orderExist(String id) {
		boolean ret = false;

		MongoCollection<Document> orders = db.getCollection("orders");

		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkOrder = new BasicDBObject();
		checkOrder.put("id", id);
		if (orders.count(checkOrder) > 0)
			ret = true;
		return ret;
	}

	public ProcessedOrder getOrder(String id) {

		MongoCollection<Document> orders = db.getCollection("orders");

		// Create Invoice-Element for return-statement
		ProcessedOrder order = null;

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
			Map<Product, Integer> items = (Map<Product, Integer>) doc.get("items");
			order = new ProcessedOrder(id, receiveDate, totalPrice, customerId, items);
		}
		return order;
	}

	public String getLastId() {
		Bson bson = Filters.eq("id", -1);
		Document doc = (Document) db.getCollection("orders").find().sort(bson).limit(1);
		return doc.getString("id");
	}
}
