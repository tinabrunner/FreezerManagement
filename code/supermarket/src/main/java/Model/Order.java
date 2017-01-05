package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Frage: Warum wird die Summe vom "Besteller" übermittelt? Im Supermarkt läuft man auch nicht an die Kasse und sagt:
 * Hier, was im Einkaufswagen ist kostet 5 Euro.
 * 
 * Antwort: Im online Warenkorb aber schon. Dann kann man es auch direkt übermitteln...
 * 
 * Die Map muss durchiteriert  und mit den Produkten im Supermarkt abgeglichen werden. Was passiert, wenn ein Produkt gar 
 * nicht (mehr) im Supermakt vorhanden ist, weil es aus dem Sortiment gelöscht wurde
 * 
 * A: In der Bestellliste dürfen nur Produkte sein, die im Supermarkt noch vorhanden sind. Muss beim hinzufügen zum Warenkorb schon überprüft werden
 */
public class Order {
	private static String id; // wo gesetzt? -> noch garnicht
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
