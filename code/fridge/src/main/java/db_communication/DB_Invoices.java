package db_communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import domain.MongoProvider;
import model.Invoice;

/* 
 * Die Klasse DB_Invoices schreibt Rechnungen inkl. aller Attribute (ID, Name, Datum,...) in die Datenbank
 * und kann sie auch wieder auslesen, sowie löschen.
 * 
 */

/**
 * @author Marius Koch
 *
 */
@Stateless(name = "dbinvoices")
public class DB_Invoices {

	@EJB
	private MongoProvider mongoProvider;

	public Document convertInvoiceToDocument(Invoice invoice) {
		Document doc = new Document("id", invoice.getId()).append("name", invoice.getName())
				.append("billingDate", invoice.getBillingDate()).append("orderDate", invoice.getOrderDate())
				.append("totalPrice", invoice.getTotalPrice()).append("invoiceURL", invoice.getInvoiceURL());
		return doc;
	}

	public Invoice convertDocumentToInvoice(Document doc) {
		String id = doc.getString("id");
		String name = doc.getString("name");
		Date billingDate = doc.getDate("billingDate");
		Date orderDate = doc.getDate("orderDate");
		double totalPrice = doc.getDouble("totalPrice");
		String invoiceURL = doc.getString("invoiceURL");
		return new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL);
	}

	// Method to Insert an Invoice
	public void insertInvoiceToDB(Invoice invoice) {
		System.out.println("insert");
		// create a client and get the database
		MongoClient mongoClient = mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		// and invoicesCollection
		MongoCollection<Document> invoices = db.getCollection("invoices");

		if (!invoiceExists(invoice.getId())) {
			Document doc = convertInvoiceToDocument(invoice);
			invoices.insertOne(doc);
		} else
			System.out.print("Invoice already exists!");
	}

	// Method to Get one Invoice
	public Invoice getInvoice(String id) {
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
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

		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
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
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		MongoCollection<Document> invoices = db.getCollection("invoices");

		Bson filter = Filters.eq("id", id);
		if (invoices.count(filter) > 0)
			return true;
		else
			return false;

	}

}
