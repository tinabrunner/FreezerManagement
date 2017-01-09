package controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeInventory;
import db_communication.DB_ShoppingList;
import domain.MongoProvider;
import model.InventoryProduct;
import model.ShoppingCartItem;
import model.ShoppingListItem;

/**
 * Created by phi on 08.12.16.
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
@Path("/shopping_cart")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartController {

	@EJB
	MongoProvider mongoProvider;

	@GET
	/** Shopping carts (Warenkörbe): non-persistent */
	public Set<ShoppingCartItem> createShoopingCart() {

		/* current items in shopping list */
		DB_ShoppingList db_shoppingList = new DB_ShoppingList();
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau
		
		/* current items in inventory: <categoryId, inventoryProduct> */
		DB_FridgeInventory db_fridgeInventory = new DB_FridgeInventory();
		Map<String, InventoryProduct> inventoryProducts = db_fridgeInventory.getAllProducts();
		
		/* to fill: warenkorb */
		Set<ShoppingCartItem> shoppingCartItems = this.fillShoppingCart( shoppingListProducts, inventoryProducts );
		
		return shoppingCartItems;
	}
	
	/**
	 * Berechet Warenkorb anhand Mindest- / Höchstbestand, Regelmäßig, Amount in Inventory.
	 * @param shoppingListProducts
	 * @param inventoryProducts
	 * @return Map<String,InventoryProduct>
	 */
	private Set<ShoppingCartItem> fillShoppingCart(
			Set<ShoppingListItem> shoppingListProducts,
			Map<String, InventoryProduct> inventoryProducts)
	{
		Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();
		
		for( ShoppingListItem shoppingListItem : shoppingListProducts ) {
			int list_min = shoppingListItem.getMindestBestand();
			if (!shoppingListItem.isRegelmaessig()) {
				// manuelle bestellung: auf cart
				shoppingCartItems.add(new ShoppingCartItem(
						shoppingListItem.getId(),
						shoppingListItem.getPreis(),
						shoppingListItem.getName(),
						shoppingListItem.getVerpackungsGroesse(),
						// amount
						list_min));
			} else {
				// regelmäßige bestellung
				int list_max = shoppingListItem.getHoechstBestand();
				int inv_amount;
				
				// corresponding products of category
					InventoryProduct inventoryProduct = inventoryProducts.get(shoppingListItem.getId());
					inv_amount = 1; // todo
				//Set<InventoryProduct> inventoryProducts = inventoryProducts.get( shoppingListItem.getId() );
				//inv_amount = inventoryProducts.size();
				
				if (inv_amount < list_min) {
					// mindestbestand unterschritten
					int to_buy_amount = list_min - inv_amount;
					
					int verpackungsGroesse = shoppingListItem.getVerpackungsGroesse();
					int packs = to_buy_amount / verpackungsGroesse; // abrunden
					// kaufmenge = maximal mögliches vielfaches der verpackungsgröße
					to_buy_amount = packs * verpackungsGroesse;
					if (to_buy_amount > 1) {
						shoppingCartItems.add(new ShoppingCartItem(
								shoppingListItem.getId(),
								shoppingListItem.getPreis(),
								shoppingListItem.getName(),
								shoppingListItem.getVerpackungsGroesse(),
								// amount
								to_buy_amount));
					}
				}
			}
		}
		return shoppingCartItems;
	}
}
