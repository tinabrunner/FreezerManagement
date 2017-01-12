package service;

import db_communication.DB_FridgeInventory;
import db_communication.DB_Settings;
import db_communication.DB_ShoppingList;
import model.InventoryProduct;
import model.ShoppingCartItem;
import model.ShoppingListItem;
import queueConnection.OrderSender;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * User: phi
 * Date: 12.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
public class ShoppingCartService {
	
	@EJB
	private DB_FridgeInventory dbFridgeInventory;
	@EJB
	private DB_ShoppingList db_shoppingList;
	@EJB
	private OrderSender orderSender;
	@EJB
	private DB_Settings db_settings;
	
	public ShoppingCartService() { /* keep */ }
	
	/**
	 * Berechet Warenkorb anhand Mindest- / Höchstbestand, Regelmäßig, Amount in Inventory & gibt Warenkorb zurück
	 * @return Map<String,InventoryProduct>
	 */
	public Set<ShoppingCartItem> getShoppingCart()
	{
		/* current items in inventory: <categoryId, inventoryProduct> */
		Map<String, InventoryProduct> inventoryProducts = dbFridgeInventory.getAllProducts();
		
		/* current items in shopping list */
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList();
		
		/* to fill: warenkorb */
		Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();
		
		for( ShoppingListItem shoppingListItem : shoppingListProducts ) {
			int list_min = shoppingListItem.getMindestBestand();
			int list_max = shoppingListItem.getHoechstBestand();
			int verpackungsGroesse = shoppingListItem.getVerpackungsGroesse();
			
			if (!shoppingListItem.isRegelmaessig()) {
				// manuelle bestellung: sofort auf cart
				int to_buy_amount = list_max;
				to_buy_amount = amountByVerpackungsgroesse( to_buy_amount, verpackungsGroesse );
				shoppingCartItems.add(new ShoppingCartItem(
						shoppingListItem.getId(),
						shoppingListItem.getPreis(),
						shoppingListItem.getName(),
						verpackungsGroesse,
						to_buy_amount ));
			} else {
				// regelmäßige bestellung: auf cart, wenn min unterschritten
				
				// corresponding products of category
				/* MACHT EJB INJECTION KAPUTT WTF todo
				int inv_amount = inventoryProducts.values().stream()
						.filter( i -> i.getProdCategoryId().equals(shoppingListItem.getId()))
						.collect(Collectors.toSet())
						.size();
				*/
				Set<InventoryProduct> inventoryProductsInCategory = new HashSet<>();
				for(InventoryProduct i : inventoryProducts.values() ) {
					if( i.getProdCategoryId().equals( shoppingListItem.getId() )) {
						inventoryProductsInCategory.add( i );
					}
				}
				int inv_amount = inventoryProductsInCategory.size();
				
				if (inv_amount < list_min) {
					// mindestbestand unterschritten.
					
					int to_buy_amount = list_max - inv_amount;
					to_buy_amount = amountByVerpackungsgroesse( to_buy_amount, verpackungsGroesse );
					
					if (to_buy_amount > 0) {
						shoppingCartItems.add(new ShoppingCartItem(
								shoppingListItem.getId(),
								shoppingListItem.getPreis(),
								shoppingListItem.getName(),
								verpackungsGroesse,
								to_buy_amount));
					}
				}
			}
		}
		return shoppingCartItems;
	}
	
	/**
	 * Verringert Bestellmenge auf ein Vielfaches der Verpackungsgröße.
	 * @param amount Menge
	 * @param packSize Packungsgröße
	 * @return Berechnete Menge oder amount falls packSize <= 1
	 */
	private int amountByVerpackungsgroesse( int amount, int packSize) {
		if( packSize > 1 ) {
			// kaufmenge = maximal mögliches vielfaches der verpackungsgröße:
			int packs = amount / packSize; // abrunden
			return packs * packSize;
		} else {
			return amount;
		}
	}
	
	public void sendOrder() {
		
		Set<ShoppingCartItem> shoppingCartItems = this.getShoppingCart(); // new HashSet<>();
		
		int wantedDeliveryDay = db_settings.retrieveSettings().getDelivery_day();
		
		List<ShoppingCartItem> relevantData = new ArrayList<>();
		for (ShoppingCartItem i : shoppingCartItems) {
			relevantData.add(new ShoppingCartItem(
					i.getId(),
					i.getAmount()));
		}
		orderSender.sendInvoice(relevantData.toString()); // wantedDeliveryDay todo
		
			/*
	private String id = null;
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
		 */
	}
}
