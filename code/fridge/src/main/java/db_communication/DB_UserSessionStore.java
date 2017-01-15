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

	// INSERT a UserSession
	public boolean insertUserSessionToDB(UserSessionData data) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Document doc = convertSessionDataToDocument(data);
		Bson filter = Filters.eq(_username, data.getUsername());
		// if the user already exists in sessionStore, delete the old
		// usersessiondata
		if (usernameExists(data.getUsername(), sessionStore)) {
			sessionStore.deleteOne(filter);
		}
		sessionStore.insertOne(doc);

		// Check if usersessiondata got inserted
		if (!usernameExists(data.getUsername(), sessionStore))
			return false;
		else
			return true;
	}

	// GET a UserSession
	public UserSessionData getUserSessionFromDB(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		if (sessionStore.count(filter) > 0) {
			FindIterable<Document> result = sessionStore.find(filter);
			return convertDocumentToSessionData(result.first());
		} else
			return null;
	}

	// DELETE a UserSession
	public void deleteUserSessionFromDB(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		sessionStore.deleteOne(filter);
	}

	/*
	 * METHODS TO CHECK USERNAME / TOKEN
	 */

	// Check if USERNAME already exists
	public boolean usernameExists(String username, MongoCollection<Document> sessionStore) {
		Bson filter = Filters.eq(_username, username);
		if (sessionStore.count(filter) > 0)
			return true;
		else
			return false;

	}

	// Check if TOKEN already exists
	public boolean tokenExists(String token) {
		MongoCollection<Document> sessionStore = getSessionStore();
		Bson filter = Filters.eq(_token, token);
		if (sessionStore.count(filter) > 0)
			return true;
		else
			return false;
	}

}
