package util;

import domain.Product;
import domain.ShoppingListItem;
import org.bson.Document;

/**
 * Created by JD on 15.12.2016.
 */
public class ShoppingListHelper {

    public static final String collectionName = "shoppingList";

    /**
     * Identifier for database column holding the id
     */
    public static final String documentShoppingListProductId = "id";

    /**
     * Identifier for database column holding the name
     */
    private static final String documentShoppingListProductName = "name";

    /**
     * Identifiert for database column holding the amount
     */
    private static final String documentShoppingListProductAmount = "amount";

    /**
     * Converts a Product to a Document for saving in the database
     *
     * @param product
     * @param amount
     * @return Document
     */
    public static Document convertProductToDatabaseItem(Product product, int amount){
        return new Document(documentShoppingListProductId, product.getId())
                .append(documentShoppingListProductName, product.getName())
                .append(documentShoppingListProductAmount, amount);
    }


    /**
     * Converts a Document stored in the database to a real world product
     *
     * @param document
     * @return
     */
    public static ShoppingListItem convertDatabaseItemToProduct(Document document){
    	
        ShoppingListItem freezerProduct = new ShoppingListItem(0, null, 0, 0, 0, 0, false, null);
        freezerProduct.setId(document.getString(documentShoppingListProductId));
        freezerProduct.setName(document.getString(documentShoppingListProductName));
        freezerProduct.setAmount(document.getInteger(documentShoppingListProductAmount));

        return freezerProduct;
    }

}
