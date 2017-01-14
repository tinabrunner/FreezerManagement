package db_communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import Model.ProcessedOrder;
import Model.Product;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;

/**
 * @author Marius Koch
 *
 */
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
					.append("sent", false);

			BasicDBList list = new BasicDBList();
			for (Map.Entry<Product, Integer> item : order.getItemsProcessed().entrySet()) {
				BasicDBObject dbItem = new BasicDBObject();
				dbItem.append("amount", item.getValue());
				dbItem.append("id", item.getKey().getId());
				dbItem.append("preis", item.getKey().getPreis());
				dbItem.append("name", item.getKey().getName());
				list.add(dbItem);
			}
			doc.append("items", list);
			orders.insertOne(doc);
		} else
			System.out.println("Order already existing!");
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

			BasicDBList itemsDb = (BasicDBList) doc.get("items");
			Map<Product, Integer> items = new HashMap<>();
			for (BasicDBObject dbItem : itemsDb.toArray(new BasicDBObject[0])) {
				Product prod = new Product();
				prod.setId(dbItem.getString("id"));
				prod.setPreis(dbItem.getDouble("preis"));
				prod.setName(dbItem.getString("name"));
				items.put(prod, dbItem.getInt("amount"));
			}

			order = new ProcessedOrder(id, receiveDate, totalPrice, customerId, items);
		}
		return order;
	}

	public String getLastId() {
		Document doc = db.getCollection("orders").find().sort(new BasicDBObject("id", -1)).limit(1).first();
		if (doc == null || !doc.containsKey("id")) {
			return "0";
		} else {
			return doc.getString("id");
		}
	}

	public Document convertOrderToDocument(ProcessedOrder order) {
		Document doc = new Document("id", order.getId()).append("recieveDate", order.getRecieveDate())
				.append("totalPrice", order.getTotalPrice()).append("customerId", order.getCustomerId())
				.append("sent", false);

		BasicDBList list = new BasicDBList();
		for (Map.Entry<Product, Integer> item : order.getItemsProcessed().entrySet()) {
			BasicDBObject dbItem = new BasicDBObject();
			dbItem.append("amount", item.getValue());
			dbItem.append("id", item.getKey().getId());
			dbItem.append("preis", item.getKey().getPreis());
			dbItem.append("name", item.getKey().getName());
			list.add(dbItem);
		}
		doc.append("items", list);
		return doc;
	}

	public ProcessedOrder convertDocumentToOrder(Document doc) {
		ProcessedOrder order = null;
		String id = doc.getString("id");
		Date receiveDate = doc.getDate("receiveDate");
		double totalPrice = doc.getDouble("totalPrice");
		String customerId = doc.getString("customerId");

		BasicDBList itemsDb = (BasicDBList) doc.get("items");
		Map<Product, Integer> items = new HashMap<>();
		for (BasicDBObject dbItem : itemsDb.toArray(new BasicDBObject[0])) {
			Product prod = new Product();
			prod.setId(dbItem.getString("id"));
			prod.setPreis(dbItem.getDouble("preis"));
			prod.setName(dbItem.getString("name"));
			items.put(prod, dbItem.getInt("amount"));
		}

		return new ProcessedOrder(id, receiveDate, totalPrice, customerId, items);
	}

	public List<ProcessedOrder> getAllNotSentOrders() {

		MongoCollection<Document> orders = db.getCollection("orders");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection

		List<ProcessedOrder> orderResult = new ArrayList<>();

		for (Document doc : orders.find()) {
			if (!doc.getBoolean("sent")) {
				orderResult.add(this.convertDocumentToOrder(doc));
			}
		}
		return orderResult;
	}

	public void setOrderToSent(String idString) {
		ProcessedOrder order = this.getOrder(idString);
		Document doc = convertOrderToDocument(order);
		doc.append("sent", true);

		Bson bson = Filters.eq("id", idString);
		db.getCollection("invoices").updateOne(bson, new Document("$set", new Document("sent", true)));
		// db.getCollection("invoices").findOneAndUpdate(bson, doc);

	}
}
