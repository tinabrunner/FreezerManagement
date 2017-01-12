package Model;

import java.util.Date;

/**
 * @author Marius Koch
 *
 */
public class ExpiryProduct extends Product {

	Date dateOfExpiry;

	public ExpiryProduct(String id, String name, int verpackungsgroesse, double price, int calories, Date dateOfExpiry) {
		super(id, name, verpackungsgroesse, price, calories);
		this.dateOfExpiry = dateOfExpiry;
	}

}
