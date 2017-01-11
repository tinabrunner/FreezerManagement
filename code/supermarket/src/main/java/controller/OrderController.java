package controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import Model.Invoice;
import Model.Order;
import db_communication.DB_Invoice;
import db_communication.DB_Order;

/**
 * @author Marius Koch
 *
 */
@Stateless(name = "orderController")
public class OrderController {

	@Resource(name = "invoiceController")
	private InvoiceController invoiceCtrl;

	@Resource(name = "dbInvoice")
	private DB_Invoice dbInvoice;

	@EJB
	private DB_Order dborder;

	public void createOrder() {
		// TODO
	}

	public void saveOrderToDB(Order order) {
		dborder.insertOrderToDB(order);
	}

	public void incomingOrder(Order order) throws IOException {
		// TODO Auto-generated method stub
		this.saveOrderToDB(order);
		Invoice invoice = invoiceCtrl.createInvoice(order);
		invoice.setURL(invoiceCtrl.InvoiceToHTML(invoice));
		dbInvoice.insertInvoiceToDB(invoice);
	}

}
