package controller;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Invoice;
import model.InvoiceList;

@Stateless
@Path("/invoices")
public class InvoiceController {

	// private static List<Invoice> invoices;
	private static String filePath = new String("src/invoices/");
	private static String path = new String("localhost:8080/bla/invoices/");

	public static void addInvoice(Invoice invoice) {
		// invoices.add(invoice);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceList getInvoices() {
		ArrayList<Invoice> in = new ArrayList<Invoice>();
		InvoiceList invoiceList = new InvoiceList();
		Invoice invoice = new Invoice("123", "hallo", null, null, 12.99, "http://www.wurstbrot.de");
		in.add(invoice);
		invoiceList.setList(in);
		return invoiceList;
	}

	/*
	 * @GET
	 * 
	 * @Path("/{id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public boolean
	 * saveInvoiceAsPDF(@PathParam("") String id) throws IOException { String
	 * finalFilePath = filePath + id; String finalPath = path + id; URL website
	 * = new URL(finalPath); ReadableByteChannel rbc =
	 * Channels.newChannel(website.openStream()); FileOutputStream fos = new
	 * FileOutputStream(new File(finalFilePath));
	 * fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); fos.close();
	 * return true; }
	 */

}
