package model;

/**
 * User: phi
 * Date: 09.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */
public class ShoppingCartItem extends ProductCategory {
	private int amount;
	
	public ShoppingCartItem() { /* keep */ }
	
	public ShoppingCartItem(int amount) {
		this.amount = amount;
	}
	
	public ShoppingCartItem(String id, int amount) {
		super(id);
		this.amount = amount;
	}
	
	public ShoppingCartItem(String id, double preis, String name, int verpackungsGroesse, int amount) {
		super(id, preis, name, verpackungsGroesse);
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
