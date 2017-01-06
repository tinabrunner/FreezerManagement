package pdfcreator;

import java.util.Date;

import Model.Product;

/**
 * @author Marius Koch
 *
 */
public class ExpiryProduct extends Product {

	Date dateOfExpiry;

	public ExpiryProduct(String id, String name, double price, Date dateOfExpiry) {
		super(id, name, price);
		this.dateOfExpiry = dateOfExpiry;
	}

}
