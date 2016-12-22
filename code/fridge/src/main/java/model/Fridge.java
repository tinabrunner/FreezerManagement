package model;

import java.util.ArrayList;
import java.util.List;

import domain.Product;

public class Fridge {
	
	private static int count_id = 1;
	private int id;
	private List<Product> bestandsliste;
	
	public Fridge () {
		this.id = count_id;
		count_id ++;
		this.bestandsliste = new ArrayList<Product>();
	}
	
	public void addProduct (Product produkt) {
		this.bestandsliste.add(produkt);
	}
	
	public boolean deleteProduct () {
		boolean ret = false;
		
		return ret;
	}

}
