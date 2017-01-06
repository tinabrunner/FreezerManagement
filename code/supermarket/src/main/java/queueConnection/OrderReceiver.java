package queueConnection;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import com.google.gson.Gson;

import Model.Order;
import controller.OrderController;

/**
 * @author Marius Koch
 *
 */
public class OrderReceiver implements MessageListener {

	public OrderReceiver() throws JMSException {
		this.setupConnection();
	}

	public void setupConnection() {
		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();

			// 2) create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("myQueue");

			// 4)create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);

			// 5) register the listener object with receiver
			receiver.setMessageListener(this);

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void onMessage(Message m) {
		try {
			TextMessage msg = (TextMessage) m;

			Gson gson = new Gson();

			// 2. JSON to Java object, read it from a Json String.
			String jsonInString = msg.getText();
			Order order = gson.fromJson(jsonInString, Order.class);
			OrderController.addOrder(order);
		} catch (JMSException e) {
			System.out.println(e);
		}
	}

}
