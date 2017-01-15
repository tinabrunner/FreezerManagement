package util;

import org.bson.Document;

import Model.Product;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListHelper {

	/**
	 * Name of the table
	 */
	public static final String collectionName = "productlist";

	/**
	 * Document identifiers
	 */
	public static final String documentProductListProductId = "id";
	public static final String documentProductListProductName = "name";
	private static final String documentProductListProductPackageSize = "verpackungsgroesse";
	private static final String documentProductListProductPrice = "price";
	private static final String documentProductListProductCalories = "calories";

	/**
	 * Converts a (Mongo)Document to a Product entity
	 * @param document to convert
	 * @return a valid Product entity
	 */
	public static Product convertDatabaseItemToProduct(Document document) {
		Product product = new Product();

		product.setId(document.getString(documentProductListProductId));
		product.setName(document.getString(documentProductListProductName));
		product.setPreis(document.getDouble(documentProductListProductPrice));
		product.setVerpackungsGroesse(document.getInteger(documentProductListProductPackageSize, 1));
		product.setCalories(document.getInteger(documentProductListProductCalories, 0));

		return product;
	}

	/**
	 * Converts a Product entity to a (Mongo)Document
	 * @param product entity to convert
	 * @return a (Mongo)Document
	 */
	public static Document convertProductToDatabaseItem(Product product) {
		return new Document(documentProductListProductId, product.getId())
				.append(documentProductListProductName, product.getName())
				.append(documentProductListProductPackageSize, product.getVerpackungsGroesse())
				.append(documentProductListProductPrice, product.getPreis())
				.append(documentProductListProductCalories, product.getCalories());

	}
}
