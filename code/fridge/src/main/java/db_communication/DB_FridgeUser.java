package db_communication;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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

	private static final String _fridge = "fridge";
	private static final String _users = "users";
	private static final String _username = "username";
	private static final String _password = "password";
	private static final String _firstName = "firstName";
	private static final String _lastName = "lastName";
	private static final String _email = "email";
	private static final String _role = "role";

	/*
	 * STANDARDMETHODS FOR RE-USE
	 */

	// create a client and get the database and usersCollection
	private MongoCollection<Document> getUsersCollection() {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(_fridge);
		MongoCollection<Document> users = db.getCollection(_users);
		return users;
	}

	public Document convertUserToDocument(FridgeUser fridgeUser) {
		Document doc = new Document(_username, fridgeUser.getUsername()).append(_password, fridgeUser.getPassword())
				.append(_firstName, fridgeUser.getFirstName()).append(_lastName, fridgeUser.getLastName())
				.append(_email, fridgeUser.getEmail()).append(_role, fridgeUser.getRole());
		return doc;
	}

	public FridgeUser convertDocumentToUser(Document doc) {
		String username = doc.getString(_username);
		String password = doc.getString(_password);
		String firstName = doc.getString(_firstName);
		String lastName = doc.getString(_lastName);
		String email = doc.getString(_email);
		String role = doc.getString(_role);
		return new FridgeUser(username, password, firstName, lastName, email, role);
	}

	/*
	 * METHODS TO COMMUNICATE WITH DB
	 */

	// Method to Insert an User
	public boolean insertUserToDB(FridgeUser fridgeUser) {
		MongoCollection<Document> users = getUsersCollection();

		if (!userExists(fridgeUser.getUsername())) {
			Document doc = convertUserToDocument(fridgeUser);
			users.insertOne(doc);
			return true;
		} else
			return false;
	}

	// Method to Search for an User
	public boolean userExists(String username) {
		MongoCollection<Document> users = getUsersCollection();
		Bson filter = Filters.eq(_username, username);
		if (users.count(filter) > 0)
			return true;
		else
			return false;
	}

	// Method to Search for an User
	public FridgeUser getUser(String username) {
		MongoCollection<Document> users = getUsersCollection();
		FridgeUser user = null;
		Bson filter = Filters.eq(_username, username);
		FindIterable<Document> result = users.find(filter);
		for (Document current : result)
			user = convertDocumentToUser(current);

		return user;
	}

	// Method to Update an User
	public boolean updateUserFromDB(FridgeUser user) {
		MongoCollection<Document> users = getUsersCollection();

		Document doc = convertUserToDocument(user);
		Bson filter = Filters.eq(_username, user.getUsername());
		if (getUser(user.getUsername()) != null) {
			users.findOneAndUpdate(filter, doc);
			return true;
		} else
			return false;
	}

	// Method to Delete an User
	public boolean deleteUserFromDB(String username) {
		MongoCollection<Document> users = getUsersCollection();

		if (getUser(username) != null) {
			Bson filter = Filters.eq(_username, username);
			users.findOneAndDelete(filter);
			return true;
		} else
			return false;
	}

	// TODO: kann GELÖSCHT werden, wenn user via username ausgegeben wird ???
	// Method to Check if username and password fits
	public boolean check_UsernameAndPassword(String username, String password) {
		boolean ret = false;
		FridgeUser user;

		MongoCollection<Document> users = getUsersCollection();

		// create a query and check if the password matches
		BasicDBObject checkUser = new BasicDBObject();
		checkUser.put("username", username);
		/*
		 * if (users.getCount(checkUser) == 1) { user = getUserFromDB(username);
		 * String userPassword = user.getPassword(); if
		 * (userPassword.equals(password)) ret = true; }
		 */
		return ret;
	}

}
