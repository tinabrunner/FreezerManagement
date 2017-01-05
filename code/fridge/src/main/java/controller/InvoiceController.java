package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

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

	private static List<Invoice> invoices;

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

	public static void saveInvoiceAsPDF(String url, String file) throws IOException {
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(new File(file));
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

}
