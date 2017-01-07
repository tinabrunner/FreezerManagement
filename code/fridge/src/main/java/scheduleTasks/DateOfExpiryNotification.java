package scheduleTasks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

/**
 * @author Marius Koch
 *
 */
public class DateOfExpiryNotification implements Runnable {

	DB_FridgeInventory db_fridgeInventory;

	@Override
	public void run() {
		LocalDate today = LocalDate.now();
		LocalDate inThreeDays = today.plusDays(3);

		List<InventoryProduct> inventoryProducts = db_fridgeInventory.getAllProducts();
		List<InventoryProduct> soonExpiredProducts = new ArrayList<>();
		List<InventoryProduct> expiredProducts = new ArrayList<>();

		for (InventoryProduct product : inventoryProducts) {
			Date input = product.getExpiryDate();
			LocalDate expiryDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (today.compareTo(expiryDate) > 0) {
				expiredProducts.add(product);
			} else if (inThreeDays.compareTo(expiryDate) > 0 && today.compareTo(expiryDate) < 0) {
				soonExpiredProducts.add(product);
			}
		}

		if (!soonExpiredProducts.isEmpty() || !expiredProducts.isEmpty()) {
			sendMail(soonExpiredProducts, expiredProducts);
		}

	}

	private void sendMail(List<InventoryProduct> soonExpiredProducts, List<InventoryProduct> expiredProducts) {
		// TODO
	}

}
