package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Brunner
 */

// TODO : kann gelöscht werden ??

public class Fridge {

	private static int count_id = 1;
	private int id;
	private List<ShoppingListItem> bestandsliste;

	public Fridge() { // keep
		this.id = count_id;
		count_id++;
		this.bestandsliste = new ArrayList<ShoppingListItem>();
	}

	public void addProduct(ShoppingListItem produkt) {
		this.bestandsliste.add(produkt);
	}

	public boolean deleteProduct() {
		boolean ret = false;

		return ret;
	}

}
