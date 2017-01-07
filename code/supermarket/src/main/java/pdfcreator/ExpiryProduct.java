package pdfcreator;

import java.util.Date;

import Model.Product;

/**
 * @author Marius Koch
 *
 */
public class ExpiryProduct extends Product {

	Date dateOfExpiry;

	public ExpiryProduct(String id, String name, int verpackungsgroesse, double price, Date dateOfExpiry) {
		super(id, name, verpackungsgroesse, price);
		this.dateOfExpiry = dateOfExpiry;
	}

}
