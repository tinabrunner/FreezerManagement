package domain;

import com.mongodb.MongoClient;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

@Singleton
public class MongoProvider {
	
	private MongoClient client;
	
	@PostConstruct
	public void init() {
		try {
			this.client = new MongoClient("localhost", 27017);
		} catch (Exception e) {
			System.err.println("MongoConnection: " + e.getMessage());
		}
	}
	@Lock(LockType.READ)
	public MongoClient getMongoClient() {
		return client;
	}
}
