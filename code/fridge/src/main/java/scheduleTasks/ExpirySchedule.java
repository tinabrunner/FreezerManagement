package scheduleTasks;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import db_communication.DB_FridgeInventory;
import model.InventoryProduct;

/**
 * @author Marius Koch
 *
 */
@Singleton
@Stateless
public class ExpirySchedule {

	@EJB
	private DB_FridgeInventory db_fridgeInventory;

	@Schedule(hour = "8", dayOfWeek = "*")
	private void run() {

		LocalDate today = LocalDate.now();
		LocalDate inThreeDays = today.plusDays(3);

		Map<String, InventoryProduct> map = db_fridgeInventory.getAllProducts();

		List<InventoryProduct> inventoryProducts = new ArrayList<>();

		for (Map.Entry<String, InventoryProduct> entry : map.entrySet()) {
			inventoryProducts.add(entry.getValue());
		}

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
