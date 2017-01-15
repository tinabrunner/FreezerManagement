package queueConnection;

import javax.ejb.Stateless;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * @author Marius Koch
 *
 */
@Stateless
public class InvoiceSender {

	public boolean sendInvoice(String invoice) {

		String message = invoice;

		try {
			// Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/invoiceConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();
			// create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// get the Queue object
			Queue t = (Queue) ctx.lookup("jms/invoiceQueue");
			// create QueueSender object
			QueueSender sender = ses.createSender(t);
			// create TextMessage object
			TextMessage msg = ses.createTextMessage();
			// set message
			msg.setText(message);
			// send message
			sender.send(msg);
			// connection close
			con.close();

			return true;

		} catch (

		Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
