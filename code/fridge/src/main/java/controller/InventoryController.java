package controller;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Stateless
@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryController {

	//@Inject
	//private SessionStore sessionStore;
	
	//private DB_FridgeInventory dbFridgeInventory;
	
	@POST
	public void DeleteInventoryProduct () {  //String username, String objectID --> Annotation hier nicht vergessen (?!)
		
	}
	
	
	// TODO: Add Inventory Product
	// TODO: Get all Inventory Products
	
}
