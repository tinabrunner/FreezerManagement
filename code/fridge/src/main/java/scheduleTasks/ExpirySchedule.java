package scheduleTasks;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import model.InventoryProduct;

/**
 * @author Marius Koch
 *
 */
@Singleton
@Stateless
public class ExpirySchedule {

	// DB_FridgeInventory db_fridgeInventory;

	// dayOfWeek="*";
	@Schedule(minute = "*", hour = "*")
	private void run() {
		System.out.println("Tina und Phil sind die coolsten Boys auf der ganzen Welt");

		// LocalDate today = LocalDate.now();
		// LocalDate inThreeDays = today.plusDays(3);
		//
		// List<InventoryProduct> inventoryProducts =
		// db_fridgeInventory.getAllProducts();
		// List<InventoryProduct> soonExpiredProducts = new ArrayList<>();
		// List<InventoryProduct> expiredProducts = new ArrayList<>();
		//
		// for (InventoryProduct product : inventoryProducts) {
		// Date input = product.getExpiryDate();
		// LocalDate expiryDate =
		// input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//
		// if (today.compareTo(expiryDate) > 0) {
		// expiredProducts.add(product);
		// } else if (inThreeDays.compareTo(expiryDate) > 0 &&
		// today.compareTo(expiryDate) < 0) {
		// soonExpiredProducts.add(product);
		// }
		// }
		//
		// if (!soonExpiredProducts.isEmpty() || !expiredProducts.isEmpty()) {
		// sendMail(soonExpiredProducts, expiredProducts);
		// }

	}

	private void sendMail(List<InventoryProduct> soonExpiredProducts, List<InventoryProduct> expiredProducts) {
		// TODO
	}
}
