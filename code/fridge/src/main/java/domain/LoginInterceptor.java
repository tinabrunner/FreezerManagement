package domain;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_UserSessionStore;

/**
 * @author Christina Brunner
 */

// @Interceptor
// @Logged
@Stateless
@Path("/loginInterceptor")
public class LoginInterceptor {

	// @Inject
	// HttpServletRequest request;

	private DB_UserSessionStore db_UserSessionStore;

	// @AroundInvoke
	// public Object invokeInterceptorMethod(InvocationContext ctx) throws
	// Exception {
	/*
	 * HttpServletRequest request = null; String myToken =
	 * request.getHeader("username"); if (myToken == null) myToken =
	 * request.getAttribute("username").toString();
	 * 
	 * if (myToken == null) { System.out .println("You are not logged in: " +
	 * myToken + ", attribute: " + request.getAttribute("username")); return
	 * null; } else { String username =
	 * db_UserSessionStore.getUserSessionFromDB(myToken).getUsername(); if
	 * (username == null) return null; else {
	 * System.out.println("You are logged in: " + myToken + ", " + username);
	 * return ctx.proceed(); } }
	 */
	// return ctx.proceed();

	// System.out.println("Test interceptor");

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String isUserLoggedIn(@PathParam("token") String token) {
		if (db_UserSessionStore.getUserSessionFromDB(token).getUsername() != null)
			return "";
		else
			return "could not find token in DB";
	}

}
