package controller;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeUser;
import db_communication.DB_UserSessionStore;
import model.FridgeUser;
import model.UserCredentials;
import model.UserSessionData;

/**
 * @author Christina Brunner
 */

@Stateless
@Path("/login")
public class LoginController {

	@EJB
	private DB_FridgeUser dbFridgeUser;

	@EJB
	private DB_UserSessionStore dbUserSessionStore;

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(UserCredentials credentials) {

		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (dbFridgeUser.userExists(username)) {
			FridgeUser user = dbFridgeUser.getUser(username);
			if (user.getPassword().equals(password)) {

				String token = buildToken();
				UserSessionData data = new UserSessionData(token, username);
				boolean success = dbUserSessionStore.insertUserSessionToDB(data);
				if (success)
					return token;
				else
					return null;
			} else
				return null;
		} else
			return null;
	}

	public String buildToken() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{token}")
	public String logout(@PathParam("token") String token) {
		if (dbUserSessionStore.tokenExists(token)) {
			dbUserSessionStore.deleteUserSessionFromDB(token);
			return "";
		} else
			return "Userdata could not be deleted";
	}

}
