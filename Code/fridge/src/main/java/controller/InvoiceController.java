package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Invoice;

@Stateless
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceController {

	private List<Invoice> invoices;

	public static void addInvoice(Invoice invoice) {
		// invoices.add(invoice);
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public static void saveInvoiceAsPDF(String url, String file) throws IOException {
		URL website = new URL(url);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(new File(file));
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

}
