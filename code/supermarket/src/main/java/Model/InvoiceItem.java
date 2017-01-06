package Model;

/**
 * @author Marius Koch
 *
 */
public class InvoiceItem extends Product {
	int amount;
	double totalPrice;

	public InvoiceItem(String id, String name, double price, int amount) {
		super(id, name, price);
		this.amount = amount;
		totalPrice = amount * price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
