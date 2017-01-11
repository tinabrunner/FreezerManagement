package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marius Koch
 *
 */
public class Order {
	private String id = null;
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
	private Map<Product, Integer> order = new HashMap<Product, Integer>();

	public Order(Map<Product, Integer> order, double totalPrice, String customerId) {
		this.customerId = customerId;
		this.order = order;
		this.totalPrice = totalPrice;
		recieveDate = new Date();
	}

	public Order(String id, Date recieveDate, double totalPrice, String customerId, Map<Product, Integer> order) {
		this(order, totalPrice, customerId);
		this.id = id;
		this.recieveDate = recieveDate;
	}

	public String getId() {
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

	public Map<Product, Integer> getOrder() {
		return order;
	}

	public void setId(String id) {
		this.id = id;
	}
}
