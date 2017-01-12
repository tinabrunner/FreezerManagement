package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import Model.Invoice;
import Model.Order;
import Model.ProcessedOrder;
import Model.Product;
import db_communication.DB_Invoice;
import db_communication.DB_Order;
import db_communication.DB_ProductList;

/**
 * @author Marius Koch
 *
 */
@Stateless(name = "orderController")
public class OrderController {

	@EJB
	private InvoiceController invoiceCtrl;

	@EJB
	private DB_Invoice dbInvoice;

	@EJB
	private DB_Order dborder;

	@EJB
	private DB_ProductList dbProductList;

	private void saveOrderToDB(ProcessedOrder order) {
		dborder.insertOrderToDB(order);
	}

	public void incomingOrder(Order order) throws IOException {
		/* process order */
		ProcessedOrder processedOrder = new ProcessedOrder(order);
		List<Product> PRODUCTS = dbProductList.getAllProducts();
		Map<Product, Integer> processedItems = new HashMap<>();
		double processedPrice = 0;
		for (Map.Entry<String, Integer> i : order.getItems().entrySet()) {
			String desiredProductId = i.getKey();
			int desiredAmount = i.getValue();
			// check if products contains desired id
			for (Product P : PRODUCTS) {
				if (P.getId().equals(desiredProductId)) {
					// vielfaches der verpackungsgröße
					int actualAmount = amountByVerpackungsgroesse(desiredAmount, P.getVerpackungsGroesse());
					if (actualAmount > 0) {
						// confirm item
						processedItems.put(P, desiredAmount);
						processedPrice += P.getPreis() * desiredAmount / P.getVerpackungsGroesse();
					}
				}
			}
		}
		processedOrder.setItemsProcessed(processedItems);
		processedOrder.setTotalPrice(processedPrice);
		/* ~ */
		this.saveOrderToDB(processedOrder);
		Invoice invoice = invoiceCtrl.createInvoice(processedOrder);
		invoice.setURL(invoiceCtrl.invoiceToHTML(invoice));
		dbInvoice.insertInvoiceToDB(invoice);
	}

	public String getLastId() {
		return dborder.getLastId();
	}

	/**
	 * Verringert Bestellmenge auf ein Vielfaches der Verpackungsgröße.
	 * 
	 * @param amount
	 *            Menge
	 * @param packSize
	 *            Packungsgröße
	 * @return Berechnete Menge oder amount falls packSize <= 1
	 */
	private static int amountByVerpackungsgroesse(int amount, int packSize) {
		if (packSize > 1) {
			// kaufmenge = maximal mögliches vielfaches der verpackungsgröße:
			int packs = amount / packSize; // abrunden
			return packs * packSize;
		} else {
			return amount;
		}
	}

}
