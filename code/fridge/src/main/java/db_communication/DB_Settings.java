package db_communication;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import domain.MongoProvider;
import model.Settings;
import org.bson.Document;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import static com.mongodb.client.model.Filters.eq;

/**
 * User: phi
 * Date: 11.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
public class DB_Settings {
	
	private static final String databaseName = "fridge";
	private static final String collectionName = "settings";
	
	private static String doc_auto = "auto";
	private static String doc_ordering_interval_day = "ordering_interval_day";
	private static String doc_ordering_interval_week = "ordering_interval_week";
	private static String doc_delivery_day = "delivery_day";
	private static String doc_user = "user";
	
	/**
	 * Parse Settings object to bson Document
	 * @param settings
	 * @return
	 */
	public static Document toDocument(Settings settings) {
		return new Document()
				.append(doc_auto, settings.isAuto())
				.append(doc_ordering_interval_day, settings.getOrdering_interval_day())
				.append(doc_ordering_interval_week, settings.getOrdering_interval_week())
				.append(doc_delivery_day, settings.getDelivery_day());
	}
	
	/**
	 * Parse bson Document to Settings object
	 * @param doc
	 * @return
	 */
	public static Settings fromDocument(Document doc) {
		return new Settings(
				doc.getBoolean(doc_auto),
				doc.getInteger(doc_ordering_interval_day),
				doc.getInteger(doc_ordering_interval_week),
				doc.getInteger(doc_delivery_day));
	}
	
	@EJB
	private MongoProvider mongoProvider;
	
	private MongoCollection<Document> getCollection() {
		return this.mongoProvider
				.getMongoClient()
				.getDatabase(databaseName)
				.getCollection(collectionName);
	}
	
	/**
	 * Store settings (per user), todo
	 * @param settings
	 */
	public void storeSettings(Settings settings) {
		this.getCollection()
				.replaceOne(
						eq(doc_user, 1), // todo
						toDocument(settings)
							.append(doc_user, 1), // todo
						new UpdateOptions()
							.upsert(true));
	}
	
	/**
	 * Retrieve settings (per user), todo
	 */
	public Settings retrieveSettings() {
		return fromDocument(
				this.getCollection()
					.find(
							eq(doc_user, 1)) // todo
					.first());
	}
}
