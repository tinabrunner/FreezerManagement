package scheduleTasks;

import service.ShoppingCartService;

import javax.annotation.Resource;
import javax.ejb.*;

/**
 * User: phi
 * Date: 12.01.17
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@Singleton
@Stateless
public class OrderTimer {
	
	@EJB
	ShoppingCartService shoppingCartHelper;
	
	@Resource private TimerService timerService;
	private static final String TIMER_NAME = "TIMER_ORDER";
	private int week_counter = 0;
	private int every_nth_week = 0;
	
	/**
	 * Set a new Timer schedule for sending automatic orders. todo startup?
	 * @param day Day of week: 1=Monday, 7=Sunday (http://docs.oracle.com/javaee/6/api/javax/ejb/Schedule.html)
	 * @param weeks Execute every nth week (>1)
	 * @throws Exception on invalid input
	 */
	public void updateOrderTimer(int day, int weeks) throws Exception {
		stopOrderTimer();
		ScheduleExpression schedule = new ScheduleExpression();
		if(day < 1  || day > 7) {
			throw new Exception("Invalid order timer format for day "+day);
		}
		if(weeks < 1) {
			throw new Exception("Invalid order timer format for weeks "+weeks);
		}
		schedule.dayOfWeek(day);
		this.every_nth_week = weeks; // workaround
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(TIMER_NAME);
		timerService.createCalendarTimer(schedule, timerConfig);
	}
	
	/**
	 * Cancel the current timer for sending orders.
	 */
	public void stopOrderTimer() {
		for(Timer timer: timerService.getTimers()) {
			if (TIMER_NAME.equals(timer.getInfo())) {
				timer.cancel();
			}
		}
	}
	
	/**
	 * check week counter and maybe call sendOrder
	 */
	@Timeout
	private void timeout() {
		this.week_counter++;
		if (this.week_counter >= this.every_nth_week) {
			// execute timer
			this.week_counter = 0;
		} else {
			// wait another (every_nth - counter) weeks
			return;
		}
		shoppingCartHelper.sendOrder();
	}
}
