package Model;

import java.time.LocalDate;

/**
 * @author Marius Koch
 *
 */
public class ExpiryProduct extends Product {

	LocalDate dateOfExpiry;

	public ExpiryProduct(String id, String name, int verpackungsgroesse, double price, int calories,
			LocalDate expiryDate) {
		super(id, name, verpackungsgroesse, price, calories);
		this.dateOfExpiry = expiryDate;
	}

}
