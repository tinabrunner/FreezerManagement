package queueConnection;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;

import Model.Order;
import controller.OrderController;

@Stateless(name = "OrderListener")
public class OrderListener implements MessageListener {

	@EJB
	private OrderController orderCtrl;

	public void onMessage(Message m) {
		System.out.println("OrderListener: Incoming Order");
		try {
			TextMessage msg = (TextMessage) m;

			Gson gson = new Gson();

			// 2. JSON to Java object, read it from a Json String.
			String jsonInString = msg.getText();
			Order order = gson.fromJson(jsonInString, Order.class);
			orderCtrl.incomingOrder(order);

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
