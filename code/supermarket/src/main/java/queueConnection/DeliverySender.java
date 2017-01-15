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
public class DeliverySender {

	public boolean sendDelivery(String delivery) {

		String message = delivery;

		try {
			System.out.println("-----------send Delivery------");
			// Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/deliveryConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();
			// create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// get the Queue object
			Queue t = (Queue) ctx.lookup("jms/deliveryQueue");
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
			System.out.println("-----------send Delivery------");

			return true;

		} catch (

		Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
