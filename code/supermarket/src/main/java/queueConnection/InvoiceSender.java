package queueConnection;

import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
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

/**
 * @author Marius Koch
 *
 */
@Stateless
public class InvoiceSender {

	@Schedule(minute = "*", hour = "*")
	public void sendInvoice() {
		System.out.println("Supermarket: Send Invoice started");
		Gson gson = new Gson();
		Date date = new Date();
		Invoice invoice = new Invoice("FromServer", "Masdf", date, date, 19.99, "hasdfjhaksfh");
		System.out.println(invoice);
		String jsonInString = gson.toJson(invoice);
		String message = jsonInString;
		System.out.println("Nachricht: " + message);

		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/fridgeConnectionFactory");

			QueueConnection con = f.createQueueConnection();
			con.start();
			// 2) create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("jms/invoiceQueue");
			// 4)create QueueSender object
			QueueSender sender = ses.createSender(t);
			// 5) create TextMessage object
			TextMessage msg = ses.createTextMessage();

			msg.setText(message);
			// 7) send message
			sender.send(msg);
			System.out.println("Message successfully sent.");

			// 8) connection close
			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}
}
