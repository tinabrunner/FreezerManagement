package queueConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.naming.InitialContext;

/**
 * @author Marius Koch
 *
 */
@Singleton
@Startup
@Stateless
public class OrderReceiver {
	
	@EJB(name = "OrderListener")
	MessageListener listener;
	
	private QueueConnection con;

	public OrderReceiver() { /* keep */ }
	
	@PostConstruct
	public void setupConnection() {
		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/orderConnectionFactory");
			con = f.createQueueConnection();
			con.start();

			// 2) create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("jms/orderQueue");

			// 4)create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);
			
			// 5) register the listener object with receiver
			receiver.setMessageListener(listener);
			
			System.out.println("OrderReceiver: listening for Messages.");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
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
