package domain;

import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Christina Brunner
 */

@Interceptor
@Logged
public class LoginInterceptor {

	// @Inject
	// HttpServletRequest request;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();

		System.out.println("Interceptor!! - Token: " + session.getAttribute("token"));

		// if displaying the home page, make sure the user is reloaded.
		/*if (request.getRequestURI().endsWith("login.html")) {
			session.removeAttribute("isUserLoggedIn");
		}

		if (session.getAttribute("token") == null && !request.getRequestURI().endsWith("login")) {
			response.sendRedirect(request.getContextPath() + "/login.html");
			return false;
		}*/
		return true;
	}

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

}
