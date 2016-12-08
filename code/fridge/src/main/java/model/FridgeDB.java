package model;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.bson.Document;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import freezers.MongoProvider;

public class FridgeDB {
	
	@EJB
	MongoProvider mongoProvider;
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public void InsertUserToDB (FridgeUser fridgeUser) {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		MongoCollection<Document> users = db.getCollection("users");
		
		// TODO: Abfrage, ob User schon existiert
		
		Document doc = new Document ("username", fridgeUser.getUsername())
			.append("password", fridgeUser.getPassword())
			.append("name", fridgeUser.getName())
			.append("surname", fridgeUser.getSurname())
			.append("email", fridgeUser.getEmail())
			.append("role", fridgeUser.getRole());
		users.insertOne(doc);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public FridgeUser GetUserFromDB (String username) {
		// create FrigdUser variable for the return-statement
		FridgeUser fridgeUser = null;
		
		// create a client and get the database and usersCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection users = (DBCollection) db.getCollection("users");
		
		// create a query to search for the FrigdeUser with the passed 'username'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		DBCursor cursor = users.find(whereQuery);
		
		// get the attributes for FridgeUser
		BasicDBObject doc;
		if (cursor.count() != 1) {
			System.out.print("Something went wrong! More than one user was found for the given username.");
		}
		else if (cursor.count()==1) {
			doc = (BasicDBObject) cursor.curr();
			String name = doc.getString("name");
			String surname = doc.getString("surname");
			String password = doc.getString("password");
			String email = doc.getString("email");
			String role = doc.getString("role");
			fridgeUser = new FridgeUser (name, surname, username, password, email, role);
		}
		
		return fridgeUser;
		
	}

	
	// TODO: UpdateUser
	
	// TODO: DeleteUser
	
}
