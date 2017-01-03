package controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeInventory;
import db_communication.DB_FridgeUser;
import model.FridgeUser;
import model.SessionStore;


@Stateless
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class InventoryController {

	@Inject
	private SessionStore sessionStore;
	
	private DB_FridgeInventory dbFridgeInventory;
	
	@POST
	public boolean DeleteInventoryProduct (String username, String objectID) {
		boolean ret = false;
		
		
		
		return ret;
	}
	
	
}
