package Model;

import java.util.Date;

/**
 * @author Marius Koch
 *
 */
public class Invoice {
	private String id;
	private String name;
	private Date billingDate;
	private Date orderDate;
	private double totalPrice;
	private String invoiceURL;

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

	public void setId(String newId) {
		this.id = newId;
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

	public void setURL(String url) {
		this.invoiceURL = url;
	}

	public void setBillingDate(Date date) {
		this.billingDate = date;
	}

}
