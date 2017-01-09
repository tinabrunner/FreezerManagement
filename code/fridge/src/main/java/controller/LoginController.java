package controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import db_communication.DB_FridgeUser;
import model.FridgeUser;
import model.SessionStore;

@Stateless
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginController {

	@Inject
	private SessionStore sessionStore;

	private DB_FridgeUser dbFridgeUser;

	@POST
	public boolean Login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
		boolean ret = false;

		if (dbFridgeUser.getUser(username) != null) {
			if (dbFridgeUser.check_UsernameAndPassword(username, password)) {
				FridgeUser fridgeUser = dbFridgeUser.getUser(username);
				sessionStore.setFridgeUser(fridgeUser);
				ret = true;
			} else {
				System.out.print("Username and Password does not fit!");
			}
		} else {
			System.out.print("User does not exists. Please register!");
		}

		return ret;
	}

	/*
	 * BEISPIEL:
	 * 
	 * @Inject SessionStore store;
	 * 
	 * @Override protected void doGet(HttpServletRequest request,
	 * HttpServletResponse response) throws ServletException, IOException {
	 * String payload = store.getPayload(); final PrintWriter out =
	 * response.getWriter(); Enumeration headerNames = request.getHeaderNames();
	 * while (headerNames.hasMoreElements()) { String name =
	 * headerNames.nextElement(); String value = request.getHeader(name);
	 * out.println(name + " : " + value); }
	 * 
	 * String uri = request.getRequestURI(); out.println("Payload: " + payload);
	 * out.println("# of sessions : " + SessionStore.INSTANCE_COUNT.get());
	 * store.setPayload(uri); }
	 * 
	 * }
	 * 
	 * 
	 * @SessionScoped public class SessionStore implements Serializable{
	 * 
	 * public static AtomicLong INSTANCE_COUNT = new AtomicLong(0);
	 * 
	 * private String payload;
	 * 
	 * @PostConstruct public void onNewSession(){
	 * INSTANCE_COUNT.incrementAndGet(); }
	 * 
	 * public String getPayload() { return payload; }
	 * 
	 * public void setPayload(String payload) { this.payload = payload; }
	 * 
	 * @PreDestroy public void onSessionDestruction(){
	 * INSTANCE_COUNT.decrementAndGet(); }
	 * 
	 * }
	 * 
	 */

}
