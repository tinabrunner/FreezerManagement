package model;

/**
 * Created by JD on 08.12.2016.
 */
public class ShoppingListItem extends ProductCategory { // todo id foreignkey ?

	public ShoppingListItem() {
	} // keep

	public ShoppingListItem(String id, double preis, String name, int verpackungsGroesse, int mindestBestand,
			int hoechstBestand, boolean regelmaessig) {

		super(id, preis, name, verpackungsGroesse);

		this.mindestBestand = mindestBestand;
		this.hoechstBestand = hoechstBestand;
		this.regelmaessig = regelmaessig;
	}

	// todo categoryId ? s.a. inventoryProduct

	private int mindestBestand;
	private int hoechstBestand;
	private boolean regelmaessig;

	public int getMindestBestand() {
		return mindestBestand;
	}

	public void setMindestBestand(int mindestBestand) {
		this.mindestBestand = mindestBestand;
	}

	public int getHoechstBestand() {
		return hoechstBestand;
	}

	public void setHoechstBestand(int hoechstBestand) {
		this.hoechstBestand = hoechstBestand;
	}

	public boolean isRegelmaessig() {
		return regelmaessig;
	}

	public void setRegelmaessig(boolean regelmaessig) {
		this.regelmaessig = regelmaessig;
	}
}
