package model;

import java.util.Map;

/**
 * User: phi
 * Date: 12.01.17
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
public class Order {
	private Map<String,Integer> items;
	private String customerId;
	private int deliveryDay;
	
	public Order() { /* keep */}
	
	public int getDeliveryDay() {
		return deliveryDay;
	}
	
	public void setDeliveryDay(int deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	
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
