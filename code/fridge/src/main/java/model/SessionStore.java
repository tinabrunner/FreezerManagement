package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import controller.HttpSession;


@SessionScoped
public class SessionStore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FridgeUser fridgeUser;
	private HttpSession httpSession;

	public SessionStore() {} // keep
	
	public FridgeUser getFridgeUser() {
		return fridgeUser;
	}

	public void setFridgeUser(FridgeUser fridgeUser) {
		this.fridgeUser = fridgeUser;
	}
	
	/*public static Context getCurrentContext(final Class<? extends Annotation> scopeType) {
	    Context ret = null;

	    if (scopeType.equals(RequestScoped.class)) {
	        ret = getRequestContext();
	    } else if (scopeType.equals(SessionScoped.class)) {
	        ret = getSessionContext();
	    } else if (scopeType.equals(ApplicationScoped.class)) {
	        ret = getApplicationContext();
	    }

	    return ret;
	}*/
	
}
