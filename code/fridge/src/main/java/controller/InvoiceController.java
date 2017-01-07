package controller;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_Invoices;
import model.Invoice;
import model.InvoiceList;

@Stateless
@Path("/invoices")
public class InvoiceController {

	DB_Invoices db_invoices = new DB_Invoices();

	// private static List<Invoice> invoices;
	private static String filePath = new String("src/invoices/");
	private static String path = new String("localhost:8080/bla/invoices/");

	public static void addInvoice(Invoice invoice) {
		// invoices.add(invoice);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceList getInvoices() {
		ArrayList<Invoice> list = new ArrayList<>();
		InvoiceList invoiceList = new InvoiceList();
		list = (ArrayList<Invoice>) db_invoices.getAllInvoices();
		for (Invoice in : list) {
			System.out.println(in.getName());
		}
		invoiceList.setList(list);
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
