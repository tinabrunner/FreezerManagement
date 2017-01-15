package db_communication;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import domain.MongoProvider;
import model.FridgeUser;

/**
 * @author Christina Brunner
 */

@Stateless
public class DB_FridgeUser {

	@EJB
	private MongoProvider mongoProvider = new MongoProvider();

	private static final String _fridge = "fridge";
	private static final String _users = "users";
	public static final String _username = "username";
	public static final String _password = "password";
	public static final String _firstName = "firstName";
	public static final String _lastName = "lastName";
	public static final String _email = "email";
	public static final String _role = "role";
	private MongoClient mongoClient;

	/*
	 * STANDARDMETHODS FOR RE-USE
	 */

	// create a client and get the database and usersCollection
	private MongoCollection<Document> getUsersCollection() {
		mongoClient = this.mongoProvider.getMongoClient();
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

	// Method to Search for an User
	private boolean userExists(String username, MongoCollection<Document> users) {
		Bson filter = Filters.eq(_username, username);
		if (users.count(filter) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * METHODS TO COMMUNICATE WITH DB
	 */

	// INSERT an User
	public boolean insertUserToDB(FridgeUser fridgeUser) {
		MongoCollection<Document> users = getUsersCollection();
		if (!userExists(fridgeUser.getUsername(), users)) {
			Document doc = convertUserToDocument(fridgeUser);
			users.insertOne(doc);
			return true;
		} else {
			return false;
		}
	}

	// GET an User
	public FridgeUser getUser(String username) {
		MongoCollection<Document> users = getUsersCollection();
		Bson filter = Filters.eq(_username, username);
		FindIterable<Document> result = users.find(filter);
		Document doc = result.first();
		if (doc != null) {
			return convertDocumentToUser(doc);
		} else {
			return null;
		}
	}

	// UPDATE an User
	public boolean updateUserFromDB(FridgeUser user) {
		MongoCollection<Document> users = getUsersCollection();
		if (userExists(user.getUsername(), users)) {
			Bson filter = Filters.eq(_username, user.getUsername());
			Document doc = convertUserToDocument(user);
			users.deleteOne(filter);
			users.insertOne(doc);
			return true;
		} else {
			return false;
		}
	}

	// DELETE an User
	public boolean deleteUserFromDB(String username) {
		MongoCollection<Document> users = getUsersCollection();
		if (userExists(username, users)) {
			Bson filter = Filters.eq(_username, username);
			users.deleteOne(filter);
			return true;
		} else {
			return false;
		}
	}

}
