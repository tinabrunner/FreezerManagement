package controller;

import db_communication.DB_Settings;
import model.Settings;
import scheduleTasks.OrderTimer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: phi
 * Date: 11.01.17
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@Stateless
@Path("/settings")
@Produces(MediaType.APPLICATION_JSON)
public class SettingsController {
	@EJB
	DB_Settings db_settings;
	@EJB
	private OrderTimer orderTimer;
	
	@POST
	public boolean storeSettings( Settings settings ) throws Exception {
		db_settings.storeSettings(settings);
		if(settings.isAuto()) {
			orderTimer.updateOrderTimer(
					settings.getOrdering_interval_day(),
					settings.getOrdering_interval_week());
		} else {
			orderTimer.stopOrderTimer();
		}
		return true;
	}
	
	@GET
	public Settings retrieveSettings() {
		Settings settings = null;
		try {
			settings = db_settings.retrieveSettings();
		} catch(Exception e) {
			settings = new Settings();
		}
		return settings;
	}
}
