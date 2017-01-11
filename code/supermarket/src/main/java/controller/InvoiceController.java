package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import Model.Invoice;
import Model.InvoiceItem;
import Model.Order;
import Model.Product;
import db_communication.DB_Invoice;
import pdfcreator.ParseHtml;
import queueConnection.InvoiceSender;

/**
 * @author
 *
 */

@Stateless(name = "invoiceCon")
public class InvoiceController {

	@EJB
	private InvoiceSender invoiceSender;
	@EJB
	private DB_Invoice dbInvoice;

	ParseHtml htmlParser = new ParseHtml();

	public Invoice createInvoice(Order order) {
		String customerId = order.getCustomerId();
		Date orderDate = order.getRecieveDate();
		double totalPrice = order.getTotalPrice();
		Date billingDate = null;

		Invoice invoice = new Invoice(null, customerId, billingDate, orderDate, totalPrice, "");
		Map<Product, Integer> orders = order.getOrder();

		for (Map.Entry<Product, Integer> entry : orders.entrySet()) {
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

	public String InvoiceToHTML(Invoice invoice) throws IOException {
		String destination;
		File htmlTemplateFile = new File("src/main/webapp/app/invoice/invoice.html");
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

		File newHtmlFile = new File("src/main/webapp/app/invoice/new.html");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);

		try {
			destination = htmlParser.createPdf(htmlString);
			return destination;
		} catch (Exception e) {
			// TODO: handle exception
			// SO?
			// System.out.println(e.getMessage());
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
	
	private void sendInvoices() {
		List<Invoice> invoices = dbInvoice.getAllNotSendedInvoices();
		for (Invoice i : invoices) {
			String in = this.invoiceToString(i);
			invoiceSender.sendInvoice(in);
			dbInvoice.setInvoiceToSended(i.getId());
		}
	}

}
