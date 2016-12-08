package freezers;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.FridgeDB;
import model.FridgeUser;

@Stateless
@Path("SessionBean/UserServiceBean")
public class UserSessionBean {
	
	FridgeDB fridgeDB;
	EJBContainer ejbContainer = EJBContainer.createEJBContainer();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void create (FridgeUser frigeUser) {
		fridgeDB.InsertUserToDB(frigeUser);
	}
	
	@GET
    @Path("{/getFridgeUser")
    public void getFridgeUser (String username) {
		FridgeUser result = fridgeDB.GetUserFromDB(username);
		
		// if result is null --> tell the user that no assigned FrigdeUser was found
		if (result == null) {
			System.out.println("No user found for the given username");
		}
		// else --> log in
		else {
			
		}
    }
 
    @PUT
    public void update (FridgeUser frigeUser) {

    }
 
    @DELETE
    @Path("{id}")
    public void delete (@PathParam("id") long id) {
    	FridgeUser fridgeUser;
//        if(null != fridgeUser) {
//        }
    }

	
}
