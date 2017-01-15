package domain;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

public abstract class RestService {
	@Context
	private HttpServletRequest httpRequest;

	// Add here any other @Context fields & associated getters

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}
}
