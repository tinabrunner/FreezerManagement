package supermarket;

import java.util.List;

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

import pdfcreator.Order;

public class SupermarketManager {
	private List<Order> orders;

	public void receiveOrder(Order order) {
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

			// 5) create listener object
			OrderListener listener = new OrderListener();

			// 6) register the listener object with receiver
			receiver.setMessageListener(listener);

			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public List<Order> getInvoices() {
		return orders;
	}

	private class OrderListener implements MessageListener {

		public void onMessage(Message m) {
			try {
				TextMessage msg = (TextMessage) m;

				Gson gson = new Gson();

				// 2. JSON to Java object, read it from a Json String.
				String jsonInString = msg.getText();
				Order order = gson.fromJson(jsonInString, Order.class);
				addOrder(order);

			} catch (JMSException e) {
				System.out.println(e);
			}
		}
	}

}
