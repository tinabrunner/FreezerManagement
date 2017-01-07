package utils;

import model.Product;

/**
 * Created by JD on 16.12.2016.
 */
public class ProductHelperTest {

    public static final String productDummyName = "Pinguin";
    public static final String productDummyId = "2312";

    public static Product getProductDummy(String name, String id){    	
        Product product = new Product(0, null, 0, 0, 0, 0, false, null);
        product.setId(id.trim().isEmpty() ? productDummyId : id);
        product.setName(name.trim().isEmpty() ? productDummyName : name);
        return product;
    }
}
