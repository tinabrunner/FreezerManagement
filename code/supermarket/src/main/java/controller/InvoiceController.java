package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

	public static final String INVOICEURLPATH = "http://localhost:8161/invoices/";
	public static final String INVOICEHTMLPATH = "../docroot/htmlInvoice/";

	ParseHtml htmlParser = new ParseHtml();

	public Invoice createInvoice(ProcessedOrder order) {
		String customerId = order.getCustomerId();
		Date orderDate = order.getRecieveDate();
		double totalPrice = order.getTotalPrice();
		Date billingDate = null;

		Invoice invoice = new Invoice(null, customerId, billingDate, orderDate, totalPrice, "",
				new ArrayList<InvoiceItem>());
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

		if (invoice.getId() == null) {
			int id = Integer.parseInt(dbInvoice.getLastId());
			id = id + 1;
			String newId = Integer.toString(id);

			invoice.setId(newId);
		}
		return invoice;
	}

	public String invoiceToHTML(Invoice invoice) throws IOException {
		String destination;
		// ClassLoader classLoader = getClass().getClassLoader();
		// File htmlTemplateFile = new File(
		// classLoader.getResource("/src/main/webapp/app/invoice/invoice.html").getFile());
		// File htmlTemplateFile = new
		// File("supermarket/src/main/webapp/app/invoice/invoice.html");
		// String s = Files.toString();
		String htmlString = htmlToString();

		String[] splitfile = htmlString.split("splitpoint");
		String firstString = splitfile[0];
		String secondString = splitfile[1];

		for (InvoiceItem i : invoice.getItems()) {
			String productName = i.getName();
			String price = Double.toString(i.getPreis());
			String amount = Integer.toString(i.getAmount());
			String totalItemPrice = Double.toString(i.getTotalPrice());

			String newItem = "<tr>" + "<td>" + productName + "</td>" + "<td class=\"text-center\">" + price + "</td>"
					+ "<td class=\"text-center\">" + amount + "</td>" + "<td class=\"text-right\">" + totalItemPrice
					+ "</td>" + "</tr>";
			firstString = firstString.concat(newItem);
		}

		htmlString = "";
		htmlString = htmlString.concat((firstString).concat(secondString));
		String fileDest = INVOICEHTMLPATH;
		fileDest = fileDest.concat(invoice.getId().concat(".html"));

		String invoiceURL = INVOICEURLPATH;
		invoiceURL = invoiceURL.concat(invoice.getId().concat(".pdf"));

		File newHtmlFile = new File(fileDest);
		FileUtils.writeStringToFile(newHtmlFile, htmlString);

		try {
			htmlParser.createPdf(newHtmlFile, htmlString, fileDest, invoice.getId());
			return invoiceURL;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	private String htmlToString() {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader("../docroot/invoiceTemplate/invoice.html"));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("error hmltostring");
			e.printStackTrace();
		}
		return contentBuilder.toString();
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
		List<Invoice> invoices = dbInvoice.getAllNotSentInvoices();
		for (Invoice i : invoices) {
			String in = this.invoiceToString(i);
			boolean sent = invoiceSender.sendInvoice(in);
			if (sent) {
				dbInvoice.setInvoiceToSent(i.getId());
			}
		}
	}

}
