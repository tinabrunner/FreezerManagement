package Model;

import java.util.Date;
import java.util.Map;

/**
 * User: phi
 * Date: 12.01.17
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
public class ProcessedOrder extends Order {
	private String id = null;
	private Date recieveDate = new Date();
	private double totalPrice;
	private Map<Product, Integer> itemsProcessed;
	
	public ProcessedOrder() { /* keep */ }
	
	public ProcessedOrder( Order order ) {
		this.setCustomerId(order.getCustomerId());
		this.setDeliveryDay(order.getDeliveryDay());
	}
	
	public ProcessedOrder(String id, Date receiveDate, double totalPrice, String customerId, Map<Product,Integer> items) {
		super(customerId);
		
		this.id = id;
		this.recieveDate = receiveDate;
		this.totalPrice = totalPrice;
		this.itemsProcessed = items;
	}
	
	public Map<Product, Integer> getItemsProcessed() {
		return itemsProcessed;
	}
	
	public void setItemsProcessed(Map<Product, Integer> itemsProcessed) {
		this.itemsProcessed = itemsProcessed;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getRecieveDate() {
		return recieveDate;
	}
	
	public void setRecieveDate(Date recieveDate) {
		this.recieveDate = recieveDate;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
