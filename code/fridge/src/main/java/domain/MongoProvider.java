package domain;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.mongodb.MongoClient;

@Singleton
public class MongoProvider {
	
	private MongoClient mongoClient = null;
	
	@Lock(LockType.READ)
	public MongoClient getMongoClient() {
		return this.mongoClient;
	}
	
	@PostConstruct
	public void init() {
		this.mongoClient = new MongoClient("localhost", 27017);
	}

}
