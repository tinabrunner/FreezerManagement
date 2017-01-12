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

	public Document convertInvoiceToDocument(Invoice invoice) {
		Document doc = new Document("id", invoice.getId()).append("name", invoice.getName())
				.append("billingDate", invoice.getBillingDate()).append("orderDate", invoice.getOrderDate())
				.append("totalPrice", invoice.getTotalPrice()).append("invoiceURL", invoice.getInvoiceURL())
				.append("items", invoice.getItems()).append("sent", false);
		return doc;
	}

	public Invoice convertDocumentToInvoice(Document doc) {
		String id = doc.getString("id");
		String name = doc.getString("name");
		Date billingDate = doc.getDate("billingDate");
		Date orderDate = doc.getDate("orderDate");
		double totalPrice = doc.getDouble("totalPrice");
		String invoiceURL = doc.getString("invoiceURL");
		List<InvoiceItem> items = (List<InvoiceItem>) doc.get("items");
		return new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL, items);
	}

	// Method to Insert an Invoice

	public void insertInvoiceToDB(Invoice invoice) {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		if (!invoiceExists(invoice.getId())) {
			Document doc = convertInvoiceToDocument(invoice);
			invoices.insertOne(doc);
		} else
			System.out.print("Invoice already exists!");
	}

	// Method to Get one Invoice
	public Invoice getInvoice(String id) {

		MongoCollection<Document> invoices = db.getCollection("invoices");
		Bson filter = Filters.eq("id", id);
		FindIterable<Document> result = invoices.find(filter);
		Invoice invoice = null;

		for (Document current : result) {
			invoice = convertDocumentToInvoice(current);
		}

		return invoice;
	}

	// Method to Get all Invoices
	public List<Invoice> getAllInvoices() {

		MongoCollection<Document> invoices = db.getCollection("invoices");

		// Create a list for all invoices and get a cursor to go through the
		// DBCollection

		List<Invoice> invoicesResult = new ArrayList<>();

		for (Document doc : invoices.find()) {
			invoicesResult.add(this.convertDocumentToInvoice(doc));
		}
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

		for (Document doc : invoices.find()) {
			if (!doc.getBoolean("sent")) {
				invoicesResult.add(this.convertDocumentToInvoice(doc));
			}
		}
		return invoicesResult;
	}

	public void setInvoiceToSended(String idString) {
		Invoice invoice = this.getInvoice(idString);
		Document doc = convertInvoiceToDocument(invoice);
		doc.append("sent", true);

		Bson bson = Filters.eq("id", idString);
		db.getCollection("invoices").updateOne(bson, new Document("$set", new Document("sent", true)));
		// db.getCollection("invoices").findOneAndUpdate(bson, doc);

	}

}
