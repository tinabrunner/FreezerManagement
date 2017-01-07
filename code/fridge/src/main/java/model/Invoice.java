package model;

import java.util.Date;

public class Invoice {
	private String id;
	private String name;
	private Date billingDate;
	private Date orderDate;
	private double totalPrice;
	private String invoiceURL;
	
	public Invoice() {} // keep
	
	public Invoice(String id, String name, Date billingDate, Date orderDate, double totalPrice, String invoiceURL) {
		this.id = id;
		this.name = name;
		this.billingDate = billingDate;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.invoiceURL = invoiceURL;
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public Date getBillingDate() {
		return billingDate;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public String getInvoiceURL() {
		return invoiceURL;
	}
	
	
	
}
