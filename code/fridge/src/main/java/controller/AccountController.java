package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import model.UserCredentials;

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

	@EJB
	private LoginController loginController;

	// Method to create an account and write the user to DB
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String createAccount(FridgeUser user) {
		boolean insertionTrue = db_fridgeUser.insertUserToDB(user);
		if (insertionTrue) {
			// Login
			UserCredentials credentials = new UserCredentials(user.getUsername(), user.getPassword());
			return loginController.login(credentials);
		} else {
			return "";
		}
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	// Method to edit an Account and write the changes to the DB
	public String editAccount(FridgeUser fridgeUser) {
		boolean success = db_fridgeUser.updateUserFromDB(fridgeUser);
		if (success)
			return "";
		else
			return "Userdata could not be updated!";
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{username}")
	// Method to delete an Account
	public String deleteAccount(@PathParam("username") String username) {
		boolean success = db_fridgeUser.deleteUserFromDB(username);
		if (success)
			return "";
		else
			return "User could not be deleted!";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{token}")
	// Method to CREATE an ACCOUNT and write the user to DB
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
