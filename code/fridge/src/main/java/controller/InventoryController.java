package controller;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

@Stateless
@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryController {

	// @Inject
	// private SessionStore sessionStore;

	@EJB
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
	public JSONArray getAllProducts() { // Map<String, InventoryProduct>

		Map<String, InventoryProduct> inventory = dbFridgeInventory.getAllProducts();

		JSONArray arr = new JSONArray();

		for (Map.Entry<String, InventoryProduct> entry : inventory.entrySet()) {
			String key = entry.getKey();
			InventoryProduct value = entry.getValue();

			JSONObject json = new JSONObject();

			json.put("ObjectID", key);
			json.put("name", value.getName().toString());
			json.put("prodCategoryID", value.getProdCategoryId().toString());

			String date = value.getExpiryDate().getYear() + "-" + value.getExpiryDate().getMonth() + "-"
					+ value.getExpiryDate().getDay();
			json.put("expiryDate", date);

			arr.add(json);
		}

		return arr;
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
