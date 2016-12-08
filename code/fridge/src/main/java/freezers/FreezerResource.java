package freezers;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ejb.*;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Stateless
@ApplicationPath("/api/freezer")
@Path("/")
@Singleton
public class FreezerResource extends Application {
	
	private int testvariable;
	
	@EJB
	MongoProvider mongoProvider;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getBestellungen() {
		// test mongo //////
		// http://mongodb.github.io/mongo-java-driver/3.4/javadoc/
		// http://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
		MongoClient mongoClient = this.mongoProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("freezer");
		MongoCollection<Document> testCol = db.getCollection("testCol");
		Document doc = new Document("testKey", "testValue")
			.append("testKey2", "testValue2");
		testCol.insertOne(doc);
		String c = ""+testCol.count();
		////////////////////
		
		return "{ \"test\": \"testCol enthält "+c+" Dokumente\" }";
	}
}
