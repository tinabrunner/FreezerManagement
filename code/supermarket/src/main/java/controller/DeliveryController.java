package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

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
	@Resource
	private TimerService timerService;

	private static int expiryTime = 10;

	// Zur Demo wird direkt die Bestellung übermittelt
	public void sendDeliveryImmediatly(String id) {
		LocalDate expiryDate = LocalDate.now();
		expiryDate.plusDays(expiryTime);

		ProcessedOrder order = dbOrder.getOrder(id);
		List<ExpiryProduct> delivery = new ArrayList<ExpiryProduct>();

		Map<Product, Integer> items = order.getItemsProcessed();
		for (Map.Entry<Product, Integer> entry : items.entrySet()) {
			Product p = entry.getKey();
			ExpiryProduct e = new ExpiryProduct(p.getId(), p.getName(), p.getVerpackungsGroesse(), p.getPreis(),
					p.getCalories(), expiryDate);
			delivery.add(e);
		}

		boolean sent = deliverySender.sendDelivery(deliveryToString(delivery));
		if (sent) {
			dbOrder.setOrderToSent(id);
		}
	}

	// Zur übermittlung mit Timer
	public void sendDelivery(List<ExpiryProduct> delivery, String id) {

		boolean sent = deliverySender.sendDelivery(deliveryToString(delivery));
		if (sent) {
			dbOrder.setOrderToSent(id);
		}
	}

	// @Timeout
	// public void sendDeliveryAfterTimeout(Timer timer, ProcessedOrder
	// processedOrder) {
	// LocalDate expiryDate = LocalDate.now();
	// expiryDate.plusDays(expiryTime);
	//
	// List<ExpiryProduct> delivery = new ArrayList<ExpiryProduct>();
	//
	// ProcessedOrder o = processedOrder;
	// Map<Product, Integer> items = o.getItemsProcessed();
	// for (Map.Entry<Product, Integer> entry : items.entrySet()) {
	//
	// Product p = entry.getKey();
	// ExpiryProduct e = new ExpiryProduct(p.getId(), p.getName(),
	// p.getVerpackungsGroesse(), p.getPreis(),
	// p.getCalories(), expiryDate);
	// delivery.add(e);
	// }
	// String orderId = o.getId();
	// this.sendDelivery(delivery, orderId);
	//
	// }

	public Timer createDeliveryTimer(ProcessedOrder processedOrder) {
		ScheduleExpression schedule = new ScheduleExpression();
		LocalDate date = LocalDate.now();
		date.plusDays(processedOrder.getDeliveryDay());
		Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		schedule.start(d);
		TimerConfig timerConfig = new TimerConfig();
		return timerService.createCalendarTimer(schedule, timerConfig);

	}

	public String deliveryToString(List<ExpiryProduct> delivery) {
		Gson gson = new Gson();
		String jsonInString = gson.toJson(delivery);
		return jsonInString;
	}
}
