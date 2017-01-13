package repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import Model.Product;
import domain.DatabaseProviderImpl;
import util.ProductListHelper;

/*
 * @author: JD, 13.01.2017
 */

public class ProductListRepositoryMongoImpl implements ProductListRepository {

	/**
	 * Internal usage of database provider
	 */
	private final DatabaseProviderImpl mongoProvider;

	/**
	 * Naming for "table" equivalent holding the data
	 */
	private static final String collectionName = ProductListHelper.collectionName;

	/**
	 * Constructor to set database for this implementation using MongoDB
	 * 
	 * @param provider
	 */
	public ProductListRepositoryMongoImpl(DatabaseProviderImpl provider) {
		this.mongoProvider = provider;
	}

	private MongoCollection<Document> getProductListCollection() {
		return mongoProvider.getCollection(collectionName);
	}

	private Document getFilterForId(String id) {
		return new Document(ProductListHelper.documentProductListProductId, id);
	}

	@Override
	public Boolean addProduct(Product product) {
		try {
			getProductListCollection().insertOne(ProductListHelper.convertProductToDatabaseItem(product));
			return true;
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(addProduct)" + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean deleteProduct(Product product) {
		try {
			DeleteResult dr = getProductListCollection().deleteOne(getFilterForId(product.getId()));
			return dr.wasAcknowledged() && dr.getDeletedCount() == 1;
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(deleteProduct): " + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean existsProduct(Product product) {
		try {
			return getProductListCollection().count(getFilterForId(product.getId())) == 1;
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(existsProduct): " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		try {
			getProductListCollection().find().sort(new Document(ProductListHelper.documentProductListProductName, 1))
					.forEach((Block<Document>) document -> {
						Product p = ProductListHelper.convertDatabaseItemToProduct(document);
						products.add(p);
					});
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(getAllProducts): " + e.getMessage());
			return new ArrayList<>();
		}

		return products;
	}

	@Override
	public long countProducts() {
		try {
			return getProductListCollection().count();
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(countProducts): " + e.getMessage());
			return 0;
		}
	}

	@Override
	public Boolean updateProduct(Product product) {
		try {
			UpdateResult ur = getProductListCollection().replaceOne(getFilterForId(product.getId()),
					ProductListHelper.convertProductToDatabaseItem(product));
			return ur.wasAcknowledged() && ur.getMatchedCount() == 1;
		} catch (Exception e) {
			System.out.println("MongoConnection.ProductList(updateProduct): " + e.getMessage());
			return false;
		}
	}

}
