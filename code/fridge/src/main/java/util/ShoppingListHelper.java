package util;

import model.ShoppingListItem;
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
    private static final String documentShoppingListProductHoechstBestand = "hoechstBestand";
    /**
     * Identifiert for database column holding the amount
     */
    private static final String documentShoppingListProductMindestBestand = "mindestBestand";
    /**
     * Identifiert for database column holding the amount
     */
    private static final String documentShoppingListProductRegelmaessig = "regelmaessig";
    /**
     * Identifiert for database column holding the amount
     */
    private static final String documentShoppingListProductPreis = "preis";
    /**
     * Identifiert for database column holding the amount
     */
    private static final String documentShoppingListProductVerpackungsGroesse = "verpackungsGroesse";

    /**
     * Converts a Product to a Document for saving in the database
     *
     * @param product
     * @return Document
     */
    public static Document convertProductToDatabaseItem(ShoppingListItem product){
        return new Document(documentShoppingListProductId, product.getId())
                .append(documentShoppingListProductName, product.getName())
                .append(documentShoppingListProductPreis, product.getPreis())
                .append(documentShoppingListProductVerpackungsGroesse, product.getVerpackungsGroesse())
                
                .append(documentShoppingListProductMindestBestand, product.getMindestBestand())
                .append(documentShoppingListProductHoechstBestand, product.getHoechstBestand())
                .append(documentShoppingListProductRegelmaessig, product.isRegelmaessig());
    }


    /**
     * Converts a Document stored in the database to a real world product
     *
     * @param document
     * @return
     */
    public static ShoppingListItem convertDatabaseItemToProduct(Document document){
        ShoppingListItem freezerProduct = new ShoppingListItem();
        freezerProduct.setId(document.getString(documentShoppingListProductId));
        freezerProduct.setName(document.getString(documentShoppingListProductName));
        freezerProduct.setPreis(document.getDouble(documentShoppingListProductPreis));
        freezerProduct.setVerpackungsGroesse(document.getInteger(documentShoppingListProductVerpackungsGroesse));
        
        freezerProduct.setHoechstBestand(document.getInteger(documentShoppingListProductHoechstBestand));
        freezerProduct.setMindestBestand(document.getInteger(documentShoppingListProductMindestBestand));
        freezerProduct.setRegelmaessig(document.getBoolean(documentShoppingListProductRegelmaessig));

        return freezerProduct;
    }

}
