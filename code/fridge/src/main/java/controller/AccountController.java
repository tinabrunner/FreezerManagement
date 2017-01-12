package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import db_communication.DB_FridgeUser;
import db_communication.DB_UserSessionStore;
import model.FridgeUser;

/**
 * @author Christina Brunner
 */

@Stateless
@Path("/account")
public class AccountController {

	@EJB
	private DB_FridgeUser db_fridgeUser;

	@EJB
	private DB_UserSessionStore db_UserSessionStore;

	// Method to create an account and write the user to DB
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createAccount(FridgeUser user) {
		boolean insertionTrue = db_fridgeUser.insertUserToDB(user);
		if (insertionTrue)
			return "";
		else {
			return "Username already exists - Please enter another username!";
		}
	}

	@PUT
	// Method to edit an Account and write the changes to the DB
	public boolean editAccount(FridgeUser fridgeUser) {

		// TODO: Checken ob überhaupt Attribut-Werte verändert wurde !?

		db_fridgeUser.updateUserFromDB(fridgeUser);
		return true;
	}

	@DELETE
	// Method to delete an Account
	public boolean deleteAccount(@FormParam("username") String username) {
		if (db_fridgeUser.deleteUserFromDB(username))
			return true;
		else {
			System.out.println("The User with the Username: " + username + " could not be deleted!");
			return false;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{token}")
	// Method to create an account and write the user to DB
	public JSONArray getAccountDetails(@PathParam("token") String token) {
		String username = db_UserSessionStore.getUserSessionFromDB(token).getUsername();

		JSONArray array = new JSONArray();
		if (db_fridgeUser.getUser(username) != null) {
			FridgeUser user = db_fridgeUser.getUser(username);

			JSONObject json = new JSONObject();
			json.put(db_fridgeUser._username, user.getUsername());
			json.put(db_fridgeUser._password, user.getPassword());
			json.put(db_fridgeUser._firstName, user.getFirstName());
			json.put(db_fridgeUser._lastName, user.getLastName());
			json.put(db_fridgeUser._email, user.getEmail());
			array.add(json);
		}
		return array;
	}

}
