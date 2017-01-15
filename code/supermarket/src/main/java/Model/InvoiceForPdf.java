package Model;

import java.util.Date;
import java.util.List;

public class InvoiceForPdf extends Invoice {
	List<InvoiceItem> items;

	public InvoiceForPdf(String id, String name, Date billingDate, Date orderDate, double totalPrice, String invoiceURL,
			List<InvoiceItem> items) {
		super(id, name, billingDate, orderDate, totalPrice, invoiceURL);
		this.items = items;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}

}
