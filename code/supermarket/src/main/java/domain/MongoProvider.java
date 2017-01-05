package domain;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoProvider implements DatabaseProvider {

	private String databaseName;
	private MongoClient client;
	private MongoDatabase database;

	public MongoProvider(String host, int port) {
		this.init(host, port);
	}

	@Override
	public boolean init(String host, int port) {
		try {
			this.client = new MongoClient(host, port);
			return true;
		} catch (Exception e) {
			System.out.println("MongoConnection: " + e.getMessage());
			return false;
		}
	}

	public MongoClient getMongoClient() {
		return client;
	}

	@Override
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	@Override
	public boolean connect() {
		try {
			this.database = this.client.getDatabase(this.databaseName);
			return true;
		} catch (Exception e) {
			System.out.println("MongoConnection: " + e.getMessage());
			return false;
		}
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public MongoCollection<Document> getCollection(String col) {
		return database.getCollection(col);
	}

	public void dropCollection(String col) {
		this.database.getCollection(col).drop();
	}

	public void dropDatabase() {
		this.database.drop();
	}
}
