package queueConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jms.MessageListener;
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
@Startup
@Singleton
@Stateless
public class InvoiceReceiver {

	@EJB(name = "invoiceListener")
	private MessageListener listener;

	private QueueConnection con;

	@PostConstruct
	public void run() {
		System.out.println("Invoice Receiver started");
		try {
			// Create and start connection

			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/fridgeConnectionFactory");
			con = f.createQueueConnection();
			con.start();

			// create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// get the Queue object
			Queue t = (Queue) ctx.lookup("jms/invoiceQueue");
			// create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);
			// register the listener object with receiver
			receiver.setMessageListener(listener);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@PreDestroy
	public void stopConnection() {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
