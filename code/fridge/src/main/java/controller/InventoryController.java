package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

@Stateless
@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryController {

	// @Inject
	// private SessionStore sessionStore;

	// EJB
	private DB_FridgeInventory dbFridgeInventory = new DB_FridgeInventory();

	@DELETE
	public boolean deleteInventoryProduct(String id) {
		// TODO: username noch mitgeben !!!
		boolean ret;
		try {
			dbFridgeInventory.deleteProductFromDBInventory(id);
			ret = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ret = false;
		}
		return ret;
	}

	@GET
	public List<InventoryProduct> getAllProducts() {
		List<InventoryProduct> inventory = dbFridgeInventory.getAllProducts();
		return inventory;
	}

	@PUT
	public boolean addInventoryProduct(InventoryProduct prod) {
		boolean ret;
		try {
			dbFridgeInventory.insertProductToDBInventory(prod);
			ret = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ret = false;
		}
		return ret;
	}

}
