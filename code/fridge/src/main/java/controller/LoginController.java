package controller;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeUser;
import db_communication.DB_UserSessionStore;
import model.FridgeUser;
import model.UserCredentials;
import model.UserSessionData;

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
	public String Login(UserCredentials credentials) {

		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (dbFridgeUser.userExists(username)) {
			FridgeUser user = dbFridgeUser.getUser(username);
			if (user.getPassword().equals(password)) {

				String token = buildToken();
				UserSessionData data = new UserSessionData(token, username);
				dbUserSessionStore.insertUserSessionToDB(data);
				return token;
			} else {
				return "Username and Password does not fit!";
			}
		} else {
			return "User does not exists. Please register!";
		}
	}

	public String buildToken() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
