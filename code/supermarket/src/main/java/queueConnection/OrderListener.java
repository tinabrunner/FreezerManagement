package queueConnection;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;

import Model.Order;
import controller.OrderController;

@Stateless
@Singleton
public class OrderListener implements MessageListener {

	@Resource(name = "orderController")
	private OrderController orderCtrl;

	public void onMessage(Message m) {
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
