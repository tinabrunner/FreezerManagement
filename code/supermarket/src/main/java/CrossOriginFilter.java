/**
 * User: phi
 * Date: 11.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */

import javax.ws.rs. container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CrossOriginFilter implements ContainerResponseFilter  {
	
	public CrossOriginFilter() { }
	
	@Override
	public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext cres) {
		cres.getHeaders().add("Access-Control-Allow-Origin", "*");
		cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
		cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		cres.getHeaders().add("Access-Control-Max-Age", "1209600");
	}
	
}
