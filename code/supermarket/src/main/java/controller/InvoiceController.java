package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import Model.Invoice;
import Model.InvoiceItem;
import Model.ProcessedOrder;
import Model.Product;
import db_communication.DB_Invoice;
import pdfcreator.ParseHtml;
import queueConnection.InvoiceSender;

/**
 * @author Marius Koch
 *
 */

@Stateless(name = "invoiceCon")
public class InvoiceController {

	@EJB
	private InvoiceSender invoiceSender;
	@EJB
	private DB_Invoice dbInvoice;

	public static final String INVOICEURLPATH = "http://localhost:8129/invoices/";
	public static final String INVOICEHTMLPATH = "/webapp/app/invoice/";

	ParseHtml htmlParser = new ParseHtml();

	public Invoice createInvoice(ProcessedOrder order) {
		String customerId = order.getCustomerId();
		Date orderDate = order.getRecieveDate();
		double totalPrice = order.getTotalPrice();
		Date billingDate = null;

		Invoice invoice = new Invoice(null, customerId, billingDate, orderDate, totalPrice, "");
		Map<Product, Integer> items = order.getItemsProcessed();

		for (Map.Entry<Product, Integer> entry : items.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
			Product p = entry.getKey();
			String id = p.getId();
			String name = p.getName();
			double price = p.getPreis();
			int amount = entry.getValue();
			int calories = p.getCalories();
			InvoiceItem i = new InvoiceItem(id, name, price, calories, amount);
			invoice.addItem(i);

		}
		return invoice;
	}

	public String invoiceToHTML(Invoice invoice) throws IOException {
		String destination;
		File htmlTemplateFile = new File("/webapp/app/invoice/invoice.html");
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);

		String[] splitfile = htmlString.split("<!--splitpoint-->");
		String firstString = splitfile[0];
		String secondString = splitfile[1];

		for (InvoiceItem i : invoice.getItems()) {
			String productName = i.getName();
			String price = Double.toString(i.getPreis());
			String amount = Integer.toString(i.getAmount());
			String totalItemPrice = Double.toString(i.getTotalPrice());

			String newItem = "<tr><td>" + productName + "</td><td class=\"text-center\">" + price
					+ "</td><td class=\"text-center\">" + amount + "</td><td class=\"text-right\">" + totalItemPrice
					+ "</td></tr>";
			firstString.concat(newItem);
		}

		htmlString.concat(firstString).concat(secondString);
		String fileDest = INVOICEHTMLPATH;
		fileDest.concat(invoice.getId()).concat(".html");

		String invoiceURL = INVOICEURLPATH;
		invoiceURL.concat(invoice.getId()).concat(".pdf");

		File newHtmlFile = new File(fileDest);
		FileUtils.writeStringToFile(newHtmlFile, htmlString);

		try {
			htmlParser.createPdf(fileDest, invoice.getId());
			return invoiceURL;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	// Rechnung wird in String umgewandelt
	public String invoiceToString(Invoice invoice) {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(invoice);
		return jsonInString;
	}

	// Jeden Tag um 8 werden REchnungen verschickt
	// @Schedule(hour = "8", dayOfWeek = "*")
	@Schedule(minute = "*", hour = "*")
	private void sendInvoices() {
		List<Invoice> invoices = dbInvoice.getAllNotSendedInvoices();
		for (Invoice i : invoices) {
			String in = this.invoiceToString(i);
			boolean sent = invoiceSender.sendInvoice(in);
			if (sent) {
				dbInvoice.setInvoiceToSent(i.getId());
			}
		}
	}

}
