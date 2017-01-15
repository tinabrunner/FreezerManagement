package queueConnection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.google.gson.Gson;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

@Stateless(name = "deliveryListener")
public class DeliveryListener {

	@EJB
	private DB_FridgeInventory dbFridgeInventory;

	public DeliveryListener() throws NamingException {

	}

	public void onMessage(Message m) {
		try {
			TextMessage msg = (TextMessage) m;
			List<InventoryProduct> products = messageToList(msg.getText());
			// Safe products to Inventory
			for (InventoryProduct prod : products) {
				dbFridgeInventory.insertProductToDBInventory(prod);
			}
			System.out.println("------------------------00000000 Delivery --------------------");
			System.out.println(msg.getText());

		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("Error Delivery");
		}
	}

	public List<InventoryProduct> messageToList(String m) {
		Gson gson = new Gson();
		String jsonInString = m;
		System.out.println(jsonInString);
		List<InventoryProduct> products = gson.fromJson(jsonInString, ArrayList.class);
		return products;
	}

}
