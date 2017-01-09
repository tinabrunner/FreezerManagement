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

@Stateless
public class OrderSender {
	public void sendInvoice(String order) {
		String message = order;

		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();

			// 2) create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("jms/invoiceQueue/invoices");

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
}
