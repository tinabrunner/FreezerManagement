package db_communication;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import Model.Product;
import domain.DatabaseProviderImpl;
import domain.MongoProvider;
import repository.ProductListRepositoryMongoImpl;
import service.ProductListService;
import service.ProductListServiceImpl;

/**
 * 
 * @author JD, 13.01.2017
 *
 */
@Stateless
public class DB_ProductList {

	@EJB
	MongoProvider mongoProvider;

	DatabaseProviderImpl db;

	@PostConstruct
	public void init() {
		db = new DatabaseProviderImpl(this.mongoProvider);
		db.setDatabaseName("supermarket");
		db.connect();
	}

	private ProductListService getProductListService() {
		ProductListRepositoryMongoImpl mongoRepo = new ProductListRepositoryMongoImpl(db);
		ProductListServiceImpl productListService = new ProductListServiceImpl(mongoRepo);

		return productListService;
	}

	public List<Product> getAllProducts() {

		return this.getProductListService().getAllProducts();
	}

	public Boolean addSingleProduct(Product product) {
		return this.getProductListService().addProduct(product);
	}

	public Boolean deleteProduct(Product product) {
		return this.getProductListService().deleteProduct(product);
	}

	public Boolean existsProduct(Product product) {
		return this.getProductListService().existsProduct(product);
	}

	public void addProducts(List<Product> products) {

		MongoCollection<Document> col = db.getCollection("productlist");

		List<Document> docs = new ArrayList<>();
		for (Product prod : products) {
			Document doc = new Document("name", prod.getName()).append("id", prod.getId())
					.append("verpackungsgroesse", prod.getVerpackungsGroesse()).append("price", prod.getPreis())
					.append("calories", prod.getCalories());
			docs.add(doc);
		}
		;

		col.insertMany(docs);
	}
}
