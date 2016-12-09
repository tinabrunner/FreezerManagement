package pdfcreator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Order {
	private static String id;
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
	private Map<String, Integer> order = new HashMap<String, Integer>();

	public Order(Map<String, Integer> order, double totalPrice, String customerId) {
		this.customerId = customerId;
		this.order = order;
		this.totalPrice = totalPrice;
		GregorianCalendar now = new GregorianCalendar();
		recieveDate = now.getTime();
	}

	public static String getId() {
		return id;
	}

	public Date getRecieveDate() {
		return recieveDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getCustomerId() {
		return customerId;
	}

	public Map<String, Integer> getOrder() {
		return order;
	}
}
