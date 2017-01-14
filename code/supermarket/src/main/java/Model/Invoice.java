package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Marius Koch
 *
 */
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

	public Invoice(String id, String name, Date billingDate, Date orderDate, double totalPrice, String invoiceURL,
			List<InvoiceItem> items) {
		this(id, name, billingDate, orderDate, totalPrice, invoiceURL);
		this.items = items;
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

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

	public void setURL(String url) {
		this.invoiceURL = url;
	}

}
