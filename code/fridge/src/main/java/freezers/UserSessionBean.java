package freezers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import model.FridgeUser;

@Stateless
@LocalBean
@Path("SessionBean/UserServiceBean")
public class UserSessionBean {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void create (FridgeUser frigeUser) {
	}
	
/*	@GET
    @Path("{id}")
    public FridgeUser read (@PathParam("id") long id) {
		
    }*/
 
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
