package util;

import Model.Product;

/*
 * @author: JD, 13.01.2017
 */
public class ProductListHelperTest {

	private final static String defaultProductName = "Pinguin";
	private final static String defaultProductId = "IdPinguin";

	public static Product getProductListDummy(String id, String name) {
		Product productDummy = new Product();
		productDummy.setId(id.length() > 0 ? id : defaultProductId);
		productDummy.setName(name.length() > 0 ? name : defaultProductName);
		return productDummy;
	}

}
