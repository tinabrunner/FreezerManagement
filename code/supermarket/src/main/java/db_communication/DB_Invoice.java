package db_communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import Model.Invoice;
import Model.InvoiceItem;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;

@Stateless(name = "dbInvoice")
public class DB_Invoice {

	@EJB
	MongoProvider mongoProvider;

	DatabaseProviderImpl db;

	@PostConstruct
	public void init() {
		db = new DatabaseProviderImpl(this.mongoProvider);
		db.setDatabaseName("supermarket");
		db.connect();

	}

	// Method to Insert an Invoice

	public void insertInvoiceToDB(Invoice invoice) {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		if (!invoiceExists(invoice.getId())) {
			Document doc = new Document("id", invoice.getId()).append("name", invoice.getName())
					.append("billingDate", invoice.getBillingDate()).append("orderDate", invoice.getOrderDate())
					.append("totalPrice", invoice.getTotalPrice()).append("invoiceURL", invoice.getInvoiceURL())
					.append("items", invoice.getItems()).append("sended", false);
			invoices.insertOne(doc);
		} else
			System.out.print("Invoice already exists!");
	}

	// Method to Get one Invoice
	public Invoice getInvoice(String id) {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		// Create Invoice-Element for return-statement
		Invoice invoice = null;

		// create a query to search for the Invoice with the passed 'id'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("id", id);

		FindIterable<Document> cursor = invoices.find(whereQuery);

		// get the attributes for invoice
		if (((DBCollection) cursor).count() != 1) {
			System.out.print("Something went wrong! More than one invoice was found for the given id.");
		} else {
			Document doc = (Document) invoices.find(whereQuery);
			String name = doc.getString("name");
			Date billingDate = doc.getDate("billingDate");
			Date orderDate = doc.getDate("orderDate");
			double totalPrice = doc.getDouble("totalPrice");
			String invoiceURL = doc.getString("invoiceURL");
			List<InvoiceItem> items = (List<InvoiceItem>) doc.get("items");
			invoice = new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL, items);
		}
		return invoice;
	}

	// Method to Get all Invoices
	public List<Invoice> getAllInvoices() {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection

		List<Invoice> invoicesResult = new ArrayList<>();
		invoices.find().forEach((Block<Document>) document -> {
			String id = document.getString("id");
			String name = document.getString("name");
			Date billingDate = document.getDate("billingDate");
			Date orderDate = document.getDate("orderDate");
			double totalPrice = document.getDouble("totalPrice");
			String invoiceURL = document.getString("invoiceURL");
			invoicesResult.add(new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL));
		});
		return invoicesResult;
	}

	// Method to Check if a User exists
	public boolean invoiceExists(String id) {
		boolean ret = false;

		MongoCollection<Document> invoices = db.getCollection("invoices");

		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkInvoice = new BasicDBObject();
		checkInvoice.put("id", id);
		if (invoices.count(checkInvoice) > 0)
			ret = true;
		return ret;
	}

	public List<Invoice> getAllNotSendedInvoices() {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection

		List<Invoice> invoicesResult = new ArrayList<>();
		invoices.find().forEach((Block<Document>) document -> {
			if (!document.getBoolean("sended")) {
				String id = document.getString("id");
				String name = document.getString("name");
				Date billingDate = document.getDate("billingDate");
				Date orderDate = document.getDate("orderDate");
				double totalPrice = document.getDouble("totalPrice");
				String invoiceURL = document.getString("invoiceURL");
				invoicesResult.add(new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL));
			}
		});
		return invoicesResult;
	}

	public void setInvoiceToSended(String idString) {
		Bson bson = Filters.eq("id", idString);
		db.getCollection("invoices").updateOne(bson, new Document("sended", true));

	}

}
