package Model;

import java.util.Map;

/**
 * @author Marius Koch
 *
 */
public class Order {
	
	private Map<String,Integer> itemsUnchecked;
	private String customerId;
	private int deliveryDay;
	
	public Order() { /* keep */}
	
	public Order(String customerId ) {
		this.customerId = customerId;
	}
	
	protected Order(Map<String, Integer> itemsUnchecked, String customerId, int deliveryDay) {
		this.itemsUnchecked = itemsUnchecked;
		this.customerId = customerId;
		this.deliveryDay = deliveryDay;
	}
	
	public int getDeliveryDay() {
		return deliveryDay;
	}
	
	public void setDeliveryDay(int deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	
	public Map<String, Integer> getItemsUnchecked() {
		return itemsUnchecked;
	}
	
	public void setItemsUnchecked(Map<String, Integer> itemsUnchecked) {
		this.itemsUnchecked = itemsUnchecked;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
