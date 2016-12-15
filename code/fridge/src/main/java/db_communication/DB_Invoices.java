package db_communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Invoice;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import freezers.MongoProvider;

/* 
 * Die Klasse DB_Invoices schreibt Rechnungen inkl. aller Attribute (ID, Name, Datum,...) in die Datenbank
 * und kann sie auch wieder auslesen, sowie löschen.
 * 
 */

public class DB_Invoices {

	@EJB
	private MongoProvider mongoProvider;
	
	// Method to Insert an Invoice
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void insertInvoiceToDB (Invoice invoice) {
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		MongoCollection<Document> invoices = db.getCollection("invoices");
		
		if (!invoiceExists(invoice.getId())) {		
			Document doc = new Document ("id", invoice.getId())
				.append("name", invoice.getName())
				.append("billingDate", invoice.getBillingDate())
				.append("orderDate", invoice.getOrderDate())
				.append("totalPrice", invoice.getTotalPrice())
				.append("invoiceURL", invoice.getInvoiceURL());
			invoices.insertOne(doc);
		}
		else
			System.out.print("Invoice already exists!");
	}
	
	// Method to Get one Invoice
		public Invoice getInvoice (String id) {
			// create a client and get the database and invoicesCollection
			MongoClient mongoClient = this.mongoProvider.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("fridge");
			DBCollection invoices = (DBCollection) db.getCollection("invoices");
			
			// Create Invoice-Element for return-statement
			Invoice invoice = null;
			
			// create a query to search for the Invoice with the passed 'id'
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("id", id);
			DBCursor cursor = invoices.find(whereQuery);
			
			// get the attributes for invoice
			if (cursor.count() != 1) {
				System.out.print("Something went wrong! More than one invoice was found for the given id.");
			}
			else {
				BasicDBObject doc = (BasicDBObject) cursor.curr();
				String name = doc.getString("name");
				Date billingDate = doc.getDate("billingDate");
				Date orderDate = doc.getDate("orderDate");
				double totalPrice = doc.getDouble("totalPrice");
				String invoiceURL = doc.getString("invoiceURL");
				invoice = new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL);
			}
			return invoice;
		}
	
	// Method to Get all Invoices
	public List<Invoice> getAllInvoices () {
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection invoices = (DBCollection) db.getCollection("invoices");
		
		// Create a list for all invoices and get a cursor to go through the DBCollection
		List<Invoice> allInvoices = new ArrayList<Invoice> ();
		DBCursor cursor = invoices.find();
		
		while (cursor.hasNext()) {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			String id = doc.getString("id");
			String name = doc.getString("name");
			Date billingDate = doc.getDate("billingDate");
			Date orderDate = doc.getDate("orderDate");
			double totalPrice = doc.getDouble("totalPrice");
			String invoiceURL = doc.getString("invoiceURL");
			allInvoices.add(new Invoice(id, name, billingDate, orderDate, totalPrice, invoiceURL));
		}
		return allInvoices;
	}
	

	
	// Method to Check if a User exists
	public boolean invoiceExists (String id) {
		boolean ret = false;
		
		// create a client and get the database and invoicesCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection invoices = (DBCollection) db.getCollection("invoices");
				
		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkInvoice = new BasicDBObject();
		checkInvoice.put("id", id);
		if (invoices.getCount(checkInvoice) > 0)
			ret = true;
		return ret;
	}
	

}
