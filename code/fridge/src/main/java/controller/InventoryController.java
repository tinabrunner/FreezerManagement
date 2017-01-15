package controller;

import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

/**
 * @author Christina Brunner
 */

@Stateless
@Path("/inventory")
public class InventoryController {

	@EJB
	private DB_FridgeInventory dbFridgeInventory;

	private static final String _ObjectID = "ObjectID";

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{id}")
	public String deleteInventoryProduct(@PathParam("id") String id) {
		boolean success = dbFridgeInventory.deleteProductFromDBInventory(id);
		if (success)
			return "";
		else
			return "Could not delete the product with id: " + id;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllProducts() {
		Map<String, InventoryProduct> inventory = dbFridgeInventory.getAllProducts();

		JSONArray arr = new JSONArray();
		for (Map.Entry<String, InventoryProduct> entry : inventory.entrySet()) {
			String key = entry.getKey();
			InventoryProduct value = entry.getValue();

			JSONObject json = new JSONObject();
			json.put(_ObjectID, key);
			json.put(dbFridgeInventory._name, value.getName().toString());
			json.put(dbFridgeInventory._prodCategoryId, value.getProdCategoryId().toString());
			Date expiryDate = value.getExpiryDate();
			json.put(dbFridgeInventory._expiryDate, expiryDate.toString());
			arr.add(json);
		}

		return arr;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.TEXT_PLAIN)
	public boolean addInventoryProduct(InventoryProduct prod) {
		try {
			dbFridgeInventory.insertProductToDBInventory(prod);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

}
