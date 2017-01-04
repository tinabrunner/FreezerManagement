package domain;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by JD on 08.12.2016.
 */
public class MongoProvider2 implements DatabaseProvider {


    private String databaseName;
    private MongoClient client;
    private MongoDatabase database;

    public MongoProvider2(String host, int port) {
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

    public MongoClient getClient() {
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
