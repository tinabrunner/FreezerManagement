package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Invoice implements Serializable {
	private String id;
	private String name;
	private Date billingDate;
	private Date orderDate;
	private double totalPrice;
	private String invoiceURL;
	List<InvoiceItem> items;

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
