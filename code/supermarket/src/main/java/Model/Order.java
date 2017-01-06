package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Frage: Warum wird die Summe vom "Besteller" �bermittelt? Im Supermarkt l�uft man auch nicht an die Kasse und sagt:
 * Hier, was im Einkaufswagen ist kostet 5 Euro.
 * 
 * Antwort: Im online Warenkorb aber schon. Dann kann man es auch direkt �bermitteln...
 * 
 * Die Map muss durchiteriert  und mit den Produkten im Supermarkt abgeglichen werden. Was passiert, wenn ein Produkt gar 
 * nicht (mehr) im Supermakt vorhanden ist, weil es aus dem Sortiment gel�scht wurde
 * 
 * A: In der Bestellliste d�rfen nur Produkte sein, die im Supermarkt noch vorhanden sind. Muss beim hinzuf�gen zum Warenkorb schon �berpr�ft werden
 */

/**
 * @author Marius Koch
 *
 */
public class Order {
	private String id; // wo gesetzt? -> noch garnicht
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
	private Map<Product, Integer> order = new HashMap<Product, Integer>();

	public Order(Map<Product, Integer> order, double totalPrice, String customerId) {
		this.customerId = customerId;
		this.order = order;
		this.totalPrice = totalPrice;
		recieveDate = new Date();
	}

	public Order(String id, Date recieveDate, double totalPrice, String customerId, Map<Product, Integer> order) {
		this(order, totalPrice, customerId);
		this.id = id;
		this.recieveDate = recieveDate;
	}

	public String getId() {
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
