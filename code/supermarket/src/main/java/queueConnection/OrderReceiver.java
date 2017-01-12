package queueConnection;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.InvocationContext;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Invocation;

/**
 * @author Marius Koch
 *
 */

@Path("/order")
@Stateless
public class OrderReceiver {
	
	@EJB(name = "OrderListener")
	MessageListener listener;

	public OrderReceiver() { /* keep */ }
	
	@Path("/start")
	@PostConstruct // GEHT  NICHT FIXME
	@GET
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
			
			System.out.println("OrderReceiver: listening for Messages.");

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
