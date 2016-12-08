package freezers;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/**
 * Created by phi on 08.12.16.
 */
@Stateless
@ApplicationPath("/api")
@Path("/")
@Singleton
public class ShoppingCart extends Application {
	
	@GET
	@Path("/shopping_cart")
	@Produces(MediaType.APPLICATION_JSON)
	public String getShoopingCart() {
		// TODO user-gebunden
		return "{ \"products\": [" +
				" { \"name\": \"prdoukt 1\", \"bla\":\"blub\" } ," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }," +
				" { \"name\": \"produkt 2\", \"bla\": \"blib\" }" +
				" ] }";
	}
}



