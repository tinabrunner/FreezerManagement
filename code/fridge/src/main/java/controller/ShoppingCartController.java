package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import domain.MongoProvider;
import model.FridgeUser;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by phi on 08.12.16.
 */
@Stateless
@Path("/shopping_cart")
@Singleton
public class ShoppingCartController {
	
	@EJB
	MongoProvider mongoProvider;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String postShoopingCart() {
		//MongoClient mongoClient = this.mongoProvider.getMongoClient();
		//MongoDatabase db = mongoClient.getDatabase("fridge"); //?
		return "";
	}
}



