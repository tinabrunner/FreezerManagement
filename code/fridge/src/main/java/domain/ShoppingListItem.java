package domain;

import java.util.Date;

/**
 * Created by JD on 08.12.2016.
 */
public class ShoppingListItem extends Product {

    public ShoppingListItem(double preis, String name, int verpackungsGroesse, int mindestbestand, int hoechstbestand,
			int aktuellerbestand, boolean regelmaessig, Date ablaufdatum) {
		super(preis, name, verpackungsGroesse, mindestbestand, hoechstbestand, aktuellerbestand, regelmaessig, ablaufdatum);
		// TODO Auto-generated constructor stub
	}

	private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
