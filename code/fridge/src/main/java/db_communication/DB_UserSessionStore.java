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
import model.UserSessionData;

/**
 * @author Christina Brunner
 */

@Stateless
public class DB_UserSessionStore {

	@EJB
	private MongoProvider mongoProvider = new MongoProvider();

	private static final String _fridge = "fridge";
	private static final String _userSessionStore = "userSessionStore";
	public static final String _token = "token";
	public static final String _username = "username";

	/*
	 * STANDARDMETHODS FOR RE-USE
	 */

	// create a client and get the database and _userSessionStoreCollection
	private MongoCollection<Document> getSessionStore() {
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase(_fridge);

		MongoCollection<Document> sessionStore = db.getCollection(_userSessionStore);

		return sessionStore;
	}

	private Document convertSessionDataToDocument(UserSessionData data) {
		Document doc = new Document(_token, data.getToken()).append(_username, data.getUsername());
		return doc;
	}

	private UserSessionData convertDocumentToSessionData(Document doc) {
		String token = doc.getString(_token);
		String username = doc.getString(_username);
		return new UserSessionData(token, username);
	}

	/*
	 * METHODS TO COMMUNICATE WITH DB
	 */

	// Method to Insert a UserSession
	public boolean insertUserSessionToDB(UserSessionData data) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Document doc = convertSessionDataToDocument(data);
		Bson filter = Filters.eq(_username, data.getUsername());
		if (usernameExists(data.getUsername())) {
			sessionStore.deleteOne(filter);
		}
		System.out.println(doc.toString());
		sessionStore.insertOne(doc);
		if (!usernameExists(data.getUsername()))
			return false;
		else
			return true;
	}

	// Method to Delete a UserSession
	public void deleteUserSessionFromDB(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		sessionStore.findOneAndDelete(filter);
	}

	// Method to Get a UserSession
	public UserSessionData getUserSessionFromDB(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		if (sessionStore.count(filter) > 0) {
			FindIterable<Document> result = sessionStore.find(filter);
			return convertDocumentToSessionData(result.first());
		} else
			return null;
	}

	// Check if Usernamealready exists
	public boolean usernameExists(String username) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_username, username);
		if (sessionStore.count(filter) > 0) {
			FindIterable<Document> result = sessionStore.find(filter);
			System.out.println("sessionstore count > 0  --> user existiert schon" + result.first().toString());
			return true;
		} else {
			System.out.println("sessionstore count ist 0 --> user existiert noch nicht");
			return false;
		}
	}

	// Check if UserSession already exists
	public boolean userSessionExists(UserSessionData data) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_username, data.getUsername());
		FindIterable<Document> result = sessionStore.find(filter);
		UserSessionData foundUser = convertDocumentToSessionData(result.first());
		if (foundUser.getToken().equals(data.getToken()))
			return true;
		else
			return false;
	}

	// Check if Token already exists
	public boolean tokenExists(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		if (sessionStore.count(filter) > 0)
			return true;
		else
			return false;
	}

}
