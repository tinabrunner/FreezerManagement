package Model;

/**
 * @author Marius Koch
 *
 */
public class Product {

	private String id;
	private String name;
	private int verpackungsgroesse;
	private double price;
	private int calories;

	public Product(String id, String name, int verpackungsgroesse, double price) {
		this.id = id;
		this.name = name;
		this.verpackungsgroesse = verpackungsgroesse;
		this.price = price;
	}

	public int getVerpackungsgroesse() {
		return verpackungsgroesse;
	}

	public void setVerpackungsgroesse(int verpackungsgroesse) {
		this.verpackungsgroesse = verpackungsgroesse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

}
