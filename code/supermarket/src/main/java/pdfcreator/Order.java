package pdfcreator;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/*
 * Frage: Warum wird die Summe vom "Besteller" �bermittelt? Im Supermarkt l�uft man auch nicht an die Kasse und sagt:
 * Hier, was im Einkaufswagen ist kostet 5 Euro.
 * 
 * Die Map muss durchiteriert  und mit den Produkten im Supermarkt abgeglichen werden. Was passiert, wenn ein Produkt gar 
 * nicht (mehr) im Supermakt vorhanden ist, weil es aus dem Sortiment gel�scht wurde
 */
public class Order {
	private static String id; // wo gesetzt?
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
	private Map<Product, Integer> order = new HashMap<Product, Integer>();

	public Order(Map<Product, Integer> order, double totalPrice, String customerId) {
		this.customerId = customerId;
		this.order = order;
		this.totalPrice = totalPrice;
		GregorianCalendar now = new GregorianCalendar();
		recieveDate = now.getTime(); // new Date(); ist einfacher
	}

	public static String getId() {
		return id;
	}

	public Date getRecieveDate() {
		return recieveDate;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getCustomerId() {
		return customerId;
	}

	public Map<Product, Integer> getOrder() {
		return order;
	}
}
