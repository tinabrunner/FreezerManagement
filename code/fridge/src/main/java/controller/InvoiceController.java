package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;

import db_communication.DB_Invoices;
import model.Invoice;

@Stateless
@Path("/invoices")
public class InvoiceController {

	@EJB
	private DB_Invoices db_invoices;

	// private static List<Invoice> invoices;
	private static String filePath = new String("src/invoices/");
	private static String path = new String("localhost:8080/bla/invoices/");

	public void saveInvoiceToDB(Invoice invoice) {
		db_invoices.insertInvoiceToDB(invoice);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getInvoices() {

		JSONArray arr = new JSONArray();
		for (Invoice i : db_invoices.getAllInvoices()) {
			arr.add(i);
		}
		return arr;
	}

	// @GET
	// @Path("/{id}")
	// public boolean saveInvoiceAsPDF(@PathParam("") String id) throws
	// IOException {
	// String finalFilePath = filePath + id;
	// String finalPath = path + id;
	// URL website = new URL(finalPath);
	// ReadableByteChannel rbc = Channels.newChannel(website.openStream());
	// FileOutputStream fos = new FileOutputStream(new File(finalFilePath));
	// fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	// fos.close();
	// return true;
	// }

}
