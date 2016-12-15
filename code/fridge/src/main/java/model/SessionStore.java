package model;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import controller.HttpSession;


@SessionScoped
public class SessionStore {

	private Map<String, String> userlist;
	private HttpSession httpSession;
	
	public SessionStore () {
		this.userlist = new HashMap<String, String> ();
	}
	
	public Map<String, String> findAll () {
		return this.userlist;
	}
	
	public void saveUser (String username, String password) { 
		// TODO: nur begrenzte Anzahl an User zulassen ??? 
		userlist.put(username, password);
	}
	
	public void removeUser (String username) {
		userlist.remove(username);
	}
	
	public void removeAll () {
		userlist.clear();
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
