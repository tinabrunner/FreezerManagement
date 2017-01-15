package Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Marius Koch
 *
 */
public class ExpiryProduct {

	private Date expiryDate;
	private String prodCategoryId;
	private String name;

	public ExpiryProduct(String id, String name, LocalDate expiryDate) {
		this.name = name;
		this.prodCategoryId = id;
		this.expiryDate = Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
