package controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeInventory;
import db_communication.DB_ShoppingList;
import model.InventoryProduct;
import model.ShoppingCartItem;
import model.ShoppingListItem;

/**
 * Created by phi on 08.12.16. .___. {o,o} /)___) --"-"--
 */
@Stateless
@Path("/shopping_cart")
@Produces(MediaType.APPLICATION_JSON)
public class ShoppingCartController {

	@EJB
	private DB_FridgeInventory dbFridgeInventory;

	@EJB
	private DB_ShoppingList db_shoppingList;

	/*
	 * Kurzes Intro in RESTful Webservices..
	 * 
	 * 1) Es sind pro Funktionalit�t entsprechende Controller vorhanden, welche
	 * HTTP-Kommunikation erlauben. 2) Um das Ganze zu entkoppeln kennen sich
	 * die Controller NICHT! 3) Der Ablauf, um den Warenkorb zu "f�llen" sieht
	 * dann so aus:
	 * 
	 * ShoppingList ------HTTP/PUT--------> ShoppingCart
	 * 
	 * Warenkorb anzeigen
	 * 
	 * GUI -------HTTP/GET -------> ShoppingCart
	 * 
	 * Element aus Warenkorb l�schen
	 * 
	 * GUI -------HTTP/DELETE ----> ShoppingCart
	 */

	@GET
	// GET "erzeugt" bwz. created nix, sondern liefert zur�ck.
	/** Shopping carts (Warenkörbe): non-persistent */
	public Set<ShoppingCartItem> createShoopingCart() {

		/* current items in inventory: <categoryId, inventoryProduct> */
		Map<String, InventoryProduct> inventoryProducts = dbFridgeInventory.getAllProducts();

		/* current items in shopping list */
		Set<ShoppingListItem> shoppingListProducts = db_shoppingList.getAllProductsFromShoppingList(); // miau

		/* to fill: warenkorb */
		Set<ShoppingCartItem> shoppingCartItems = this.fillShoppingCart(shoppingListProducts, inventoryProducts); // new
																													// HashSet<>();

		return shoppingCartItems;
	}

	/**
	 * Berechet Warenkorb anhand Mindest- / Höchstbestand, Regelmäßig, Amount
	 * in Inventory.
	 * 
	 * @param shoppingListProducts
	 * @param inventoryProducts
	 * @return Map<String,InventoryProduct>
	 */
	private Set<ShoppingCartItem> fillShoppingCart(Set<ShoppingListItem> shoppingListProducts,
			Map<String, InventoryProduct> inventoryProducts) {

		Set<ShoppingCartItem> shoppingCartItems = new HashSet<>();

		for (ShoppingListItem shoppingListItem : shoppingListProducts) {
			int list_min = shoppingListItem.getMindestBestand();
			int list_max = shoppingListItem.getHoechstBestand();
			if (!shoppingListItem.isRegelmaessig()) {
				// manuelle bestellung: auf cart
				shoppingCartItems.add(new ShoppingCartItem(shoppingListItem.getId(), shoppingListItem.getPreis(),
						shoppingListItem.getName(), shoppingListItem.getVerpackungsGroesse(),
						// amount
						list_max));
			} else {
				// regelmäßige bestellung

				// corresponding products of category
				/*
				 * MACHT EJB INJECTION KAPUTT WTF todo int inv_amount =
				 * inventoryProducts.values().stream() .filter( i ->
				 * i.getProdCategoryId().equals(shoppingListItem.getId()))
				 * .collect(Collectors.toSet()) .size();
				 */
				Set<InventoryProduct> inventoryProductsInCategory = new HashSet<>();
				for (InventoryProduct i : inventoryProducts.values()) {
					if (i.getProdCategoryId().equals(shoppingListItem.getId())) {
						inventoryProductsInCategory.add(i);
					}
				}
				int inv_amount = inventoryProductsInCategory.size();

				if (inv_amount < list_min) {
					// mindestbestand unterschritten.

					int to_buy_amount = list_max - inv_amount;

					int verpackungsGroesse = shoppingListItem.getVerpackungsGroesse();
					if (verpackungsGroesse > 0) {
						// kaufmenge = maximal mögliches vielfaches der
						// verpackungsgröße:
						int packs = to_buy_amount / verpackungsGroesse; // abrunden
						to_buy_amount = packs * verpackungsGroesse;
					}

					if (to_buy_amount > 0) {
						shoppingCartItems
								.add(new ShoppingCartItem(shoppingListItem.getId(), shoppingListItem.getPreis(),
										shoppingListItem.getName(), shoppingListItem.getVerpackungsGroesse(),
										// amount
										to_buy_amount));
					}
				}
			}
		}
		return shoppingCartItems;
	}
}
