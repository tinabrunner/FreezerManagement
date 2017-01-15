package util;

import model.ShoppingListItem;

/*
 * @author: JD, 13.01.2017
 */
public class ProductHelperTest {

	public static final String productDummyName = "Pinguin";
	public static final String productDummyId = "2312";

	public static ShoppingListItem getProductDummy(String name, String id) {
		ShoppingListItem product = new ShoppingListItem();
		product.setId(id.trim().isEmpty() ? productDummyId : id);
		product.setName(name.trim().isEmpty() ? productDummyName : name);
		return product;
	}

}
