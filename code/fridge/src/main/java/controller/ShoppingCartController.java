package controller;

import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import model.ShoppingCartItem;
import service.ShoppingCartService;

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
	private ShoppingCartService shoppingCartHelper;
	
	@GET
	/** Shopping carts (Warenkörbe): non-persistent.
	 * Generate and return shopping cart
	 */
	public Set<ShoppingCartItem> createShoopingCart() {
		return shoppingCartHelper.getShoppingCart();
	}
	
	@POST
	/**
	 * Submit order. todo: skip next delivery ?
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean submitOrder(List<ShoppingCartItem> items) {
		shoppingCartHelper.sendOrder();
		return true;
	}
}
