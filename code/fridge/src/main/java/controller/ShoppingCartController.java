package controller;

import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeInventory;
import db_communication.DB_ShoppingList;
import model.InventoryProduct;
import model.ShoppingCartItem;
import model.ShoppingListItem;
import queueConnection.OrderSender;

/**
 * Created by phi on 08.12.16.
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
@Path("/shopping_cart")
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartController {
	
	@EJB
	private DB_FridgeInventory dbFridgeInventory;
	@EJB
	private DB_ShoppingList db_shoppingList;
	
	@EJB
	private OrderSender orderSender;
	
	@GET
	/** Shopping carts (Warenkörbe): non-persistent.
	 * Generate and return shopping cart
	 */
	public Set<ShoppingCartItem> createShoopingCart() {
		
		/* current items in inventory: <categoryId, inventoryProduct> */
		Map<String, InventoryProduct> inventoryProducts = dbFridgeInventory.getAllProducts();
		
		/* current items in shopping list */
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau
		
		/* to fill: warenkorb */
		Set<ShoppingCartItem> shoppingCartItems = this.fillShoppingCart( shoppingListProducts, inventoryProducts ); // new HashSet<>();
		
		return shoppingCartItems;
	}
	
	@POST
	/**
	 * Submit order. todo: skip next delivery ?
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ShoppingCartItem> submitOrder(List<ShoppingCartItem> items) {
		List<ShoppingCartItem> relevantData = new ArrayList<>();
		for(ShoppingCartItem i : items ) {
			relevantData.add(new ShoppingCartItem(
					i.getId(),
					i.getAmount()));
		}
		orderSender.sendInvoice(relevantData.toString()); // todo .. ?
		/*
	private String id = null;
	private Date recieveDate;
	private double totalPrice;
	private String customerId;
		 */
		return items;
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
	 * @param amount
	 * @param packSize
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
}
