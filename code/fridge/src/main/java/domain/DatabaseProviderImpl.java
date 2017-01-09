package domain;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Created by JD on 07.12.2016.
 */
public class DatabaseProviderImpl implements DatabaseProvider {
    
    @EJB
    private MongoProvider mongoConnection;
    
    private String databaseName;
    private MongoDatabase database;
    
    public DatabaseProviderImpl(String databaseName) {
        this.databaseName = databaseName;
    }
    
    @Override
    public boolean connect() {
        try {
            this.database = this.mongoConnection
                    .getMongoClient() // singleton connection instance
                    .getDatabase(this.databaseName);
            return true;
        } catch (Exception e) {
            System.err.println("MongoConnection: " + e.getMessage());
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
    
    public void disconnect() {
        this.mongoConnection.getMongoClient().close();
    }
}
