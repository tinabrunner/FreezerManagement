package db_communication;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import domain.MongoProvider;
import model.FridgeUser;

/* 
 * Die Klasse DB_FridgeUser schreibt User inkl. aller Attribute (Name, Email,...) in die Datenbank,
 * kann sie auch wieder auslesen, sowie editieren und löschen.
 * 
 */

@Stateless
public class DB_FridgeUser {

	@EJB
	private MongoProvider mongoProvider = new MongoProvider();

	// Method to Insert an User
	public boolean insertUserToDB(FridgeUser fridgeUser) {
		boolean ret = false;

		// create a client and get the database and usersCollection
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		DBCollection users = (DBCollection) db.getCollection("users");

		if (!userExists(fridgeUser.getUsername())) {
			/*
			 * Document doc = new Document("username", fridgeUser.getUsername())
			 * .append("password", fridgeUser.getPassword()).append("firstName",
			 * fridgeUser.getName()) .append("lastName",
			 * fridgeUser.getSurname()).append("email", fridgeUser.getEmail())
			 * .append("role", fridgeUser.getRole());
			 */
			BasicDBObject doc = new BasicDBObject("username", fridgeUser.getUsername())
					.append("password", fridgeUser.getPassword()).append("firstName", fridgeUser.getName())
					.append("lastName", fridgeUser.getSurname()).append("email", fridgeUser.getEmail())
					.append("role", fridgeUser.getRole());
			users.save(doc);
			ret = true;
		}
		return ret;
	}

	// Method to Search for an User
	public FridgeUser getUserFromDB(String username) {

		// create FrigdUser variable for the return-statement
		FridgeUser fridgeUser = null;

		DBCollection users = getUsersCollection();

		// create a query to search for the FrigdeUser with the passed
		// 'username'
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("username", username);
		DBCursor cursor = users.find(whereQuery);

		// get the attributes for FridgeUser
		if (cursor.count() != 1) {
			System.out.print("Something went wrong! More than one user was found for the given username.");
		} else {
			BasicDBObject doc = (BasicDBObject) cursor.curr();
			String name = doc.getString("firstName");
			String surname = doc.getString("lastName");
			String password = doc.getString("password");
			String email = doc.getString("email");
			String role = doc.getString("role");
			fridgeUser = new FridgeUser(name, surname, username, password, email, role);
		}

		return fridgeUser;

	}

	// Method to Update an User
	public void updateUserFromDB(FridgeUser user) {

		DBCollection users = getUsersCollection();

		// create a query to search for the FrigdeUser that should be updated
		BasicDBObject updateUser = new BasicDBObject("username", user.getUsername())
				.append("password", user.getPassword()).append("firstName", user.getName())
				.append("lastName", user.getSurname()).append("email", user.getEmail()).append("role", user.getRole());
		BasicDBObject searchQuery = new BasicDBObject().append("username", user.getUsername());
		users.update(searchQuery, updateUser);
	}

	// Method to Delete an User
	public boolean deleteUserFromDB(String username) {
		boolean ret = false;

		DBCollection users = getUsersCollection();

		if (userExists(username)) {
			// create a query to get the user and delete it
			BasicDBObject deleteUser = new BasicDBObject();
			deleteUser.put("username", username);
			users.remove(deleteUser);
			ret = true;
		}
		return ret;
	}

	// Method to Check if a User exists
	public boolean userExists(String username) {
		boolean ret = false;

		DBCollection users = getUsersCollection();

		// create a query and check if there are more than 0 elements in the db
		BasicDBObject checkUser = new BasicDBObject();
		checkUser.put("username", username);
		if (users.getCount(checkUser) > 0)
			ret = true;
		return ret;
	}

	// Method to Check if username and password fits
	public boolean check_UsernameAndPassword(String username, String password) {
		boolean ret = false;
		FridgeUser user;

		DBCollection users = getUsersCollection();

		// create a query and check if the password matches
		BasicDBObject checkUser = new BasicDBObject();
		checkUser.put("username", username);
		if (users.getCount(checkUser) == 1) {
			user = getUserFromDB(username);
			String userPassword = user.getPassword();
			if (userPassword.equals(password))
				ret = true;
		}
		return ret;
	}

	// create a client and get the database and usersCollection
	public DBCollection getUsersCollection() {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("fridge");
		return (DBCollection) db.getCollection("users");
	}

}
