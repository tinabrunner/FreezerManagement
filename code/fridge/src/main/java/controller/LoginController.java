package controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeUser;
import model.FridgeUser;
import model.UserCredentials;

@Stateless
@Path("/login")
public class LoginController {

	// @Inject
	// private SessionStore sessionStore;

	@EJB
	private DB_FridgeUser dbFridgeUser;

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Login(UserCredentials credentials) {

		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (dbFridgeUser.userExists(username)) {
			FridgeUser user = dbFridgeUser.getUser(username);
			if (user.getPassword().equals(password)) {
				// sessionStore.setFridgeUser(fridgeUser); ADD USER TO
				// SESSIONSTORE
				return user.getUsername();
			} else {
				return "Username and Password does not fit!";
			}
		} else {
			return "User does not exists. Please register!";
		}
	}

}
