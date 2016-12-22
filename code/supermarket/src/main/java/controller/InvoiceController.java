package controller;

import java.util.Map;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import com.google.gson.Gson;

import Model.Invoice;
import pdfcreator.Order;
import pdfcreator.Product;

public class InvoiceController {

	public void createInvoice(Order order) {
		Map<Product, Integer> orders = order.getOrder();

		for (Map.Entry<Product, Integer> entry : orders.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
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
