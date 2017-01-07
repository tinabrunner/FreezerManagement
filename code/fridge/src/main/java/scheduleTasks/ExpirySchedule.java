package scheduleTasks;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 * @author Marius Koch
 *
 */

@Stateless
public class ExpirySchedule {

	@Schedule(minute = "*/1", hour = "*")
	private void run() {
		System.out.println("Tina und Phil sind die coolsten Boys auf der ganzen Welt");
	}

	// @Schedule(second = "*/1", minute = "*", hour = "*")
	// private void run1() {
	// System.out.println("Tina und Phil sind die coolsten Boys auf der ganzen
	// Welt");
	//
	// }

	// private void startScheduleTask() {
	// LocalDateTime localNow = LocalDateTime.now();
	// ZoneId currentZone = ZoneId.of("Europe/Berlin");
	// ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
	// ZonedDateTime zonedNext5;
	// zonedNext5 = zonedNow.withHour(5).withMinute(0).withSecond(0);
	// if (zonedNow.compareTo(zonedNext5) > 0)
	// zonedNext5 = zonedNext5.plusDays(1);
	//
	// Duration duration = Duration.between(zonedNow, zonedNext5);
	// long initalDelay = duration.getSeconds();
	//
	// ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	// scheduler.scheduleAtFixedRate(new DateOfExpiryNotification(),
	// initalDelay, 24 * 60 * 60, TimeUnit.SECONDS);
	// }

}
