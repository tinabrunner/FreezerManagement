package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import com.google.gson.Gson;

import Model.ExpiryProduct;
import Model.ProcessedOrder;
import Model.Product;
import db_communication.DB_Order;
import queueConnection.DeliverySender;

@Stateless(name = "deliveryCtrl")
public class DeliveryController {

	@EJB
	private DB_Order dbOrder;

	@EJB
	private DeliverySender deliverySender;

	private static int expiryTime = 10;

	@Schedule(minute = "*", hour = "*")
	private void sendProducts() {
		LocalDate expiryDate = LocalDate.now();
		expiryDate.plusDays(expiryTime);

		List<ProcessedOrder> orders = dbOrder.getAllNotSentOrders();
		List<ExpiryProduct> delivery = new ArrayList<ExpiryProduct>();

		for (ProcessedOrder or : orders) {

			ProcessedOrder o = or;
			Map<Product, Integer> items = o.getItemsProcessed();
			for (Map.Entry<Product, Integer> entry : items.entrySet()) {

				Product p = entry.getKey();
				ExpiryProduct e = new ExpiryProduct(p.getId(), p.getName(), p.getVerpackungsGroesse(), p.getPreis(),
						p.getCalories(), expiryDate);
				delivery.add(e);
			}
			boolean sent = deliverySender.sendDelivery(deliveryToString(delivery));
			if (sent) {
				dbOrder.setOrderToSent(o.getId());
			}
		}
	}

	public String deliveryToString(List<ExpiryProduct> delivery) {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(delivery);
		return jsonInString;
	}
}
