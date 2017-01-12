package queueConnection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

/**
 * @author Marius Koch
 *
 */

@Stateless
public class OrderReceiver {
	
	@EJB
	OrderListener listener;

	public OrderReceiver() throws JMSException {
	}

	public void setupConnection() {
		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/orderConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();

			// 2) create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("jms/orderQueue");

			// 4)create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);
			
			// 5) register the listener object with receiver
			receiver.setMessageListener(listener);

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
