package Model;

import java.util.Map;

/**
 * @author Marius Koch
 *
 */
public class Order {
	
	/** unchecked (unprocessed ) items */
	private Map<String,Integer> items;
	private String customerId;
	private int deliveryDay;
	
	public Order() { /* keep */}
	
	public Order(String customerId ) {
		this.customerId = customerId;
	}
	
	protected Order(Map<String, Integer> items, String customerId, int deliveryDay) {
		this.items = items;
		this.customerId = customerId;
		this.deliveryDay = deliveryDay;
	}
	
	public int getDeliveryDay() {
		return deliveryDay;
	}
	
	public void setDeliveryDay(int deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	
	/** unchecked (unprocessed ) items */
	public Map<String, Integer> getItems() {
		return items;
	}
	
	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
