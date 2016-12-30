package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import Model.Invoice;
import Model.InvoiceItem;
import pdfcreator.Order;
import pdfcreator.ParseHtml;
import pdfcreator.Product;

public class InvoiceController {

	ParseHtml htmlParser = new ParseHtml();

	public void createInvoice(Order order) {
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
			double price = p.getPrice();
			int amount = entry.getValue();
			InvoiceItem i = new InvoiceItem(id, name, price, amount);
			invoice.addItem(i);

		}

	}

	public void InvoiceToHTML(Invoice invoice) throws IOException {
		File htmlTemplateFile = new File("src/main/webapp/app/invoice/invoice.html");
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);

		String[] splitfile = htmlString.split("<!--splitpoint-->");
		String firstString = splitfile[0];
		String secondString = splitfile[1];

		for (InvoiceItem i : invoice.getItems()) {
			String productName = i.getName();
			String price = Double.toString(i.getPrice());
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
			htmlParser.createPdf(htmlString);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sendInvoice(Invoice invoice) {
		String message = invoiceToString(invoice);

		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();

			// 2) create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("myQueue");

			// 4)create QueueSender object
			QueueSender sender = ses.createSender(t);

			// 5) create TextMessage object
			TextMessage msg = ses.createTextMessage();

			// 6) write message
			msg.setText(message);

			// 7) send message
			sender.send(msg);
			System.out.println("Message successfully sent.");

			// 8) connection close
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String invoiceToString(Invoice invoice) {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(invoice);
		return jsonInString;
	}

}
