package model;

/**
 * User: phi
 * Date: 11.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */
public class Settings {
	
	public Settings() { /*keep*/ }
	
	public Settings(boolean auto, int ordering_interval_day, int ordering_interval_week, int delivery_day) {
		this.auto = auto;
		this.ordering_interval_day = ordering_interval_day;
		this.ordering_interval_week = ordering_interval_week;
		this.delivery_day = delivery_day;
	}
	
	private boolean auto = true;
	private int ordering_interval_day = 2;
	private int ordering_interval_week = 2;
	private int delivery_day = 5;
	
	public boolean isAuto() {
		return auto;
	}
	
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
	public int getOrdering_interval_day() {
		return ordering_interval_day;
	}
	
	public void setOrdering_interval_day(int ordering_interval_day) {
		this.ordering_interval_day = ordering_interval_day;
	}
	
	public int getOrdering_interval_week() {
		return ordering_interval_week;
	}
	
	public void setOrdering_interval_week(int ordering_interval_week) {
		this.ordering_interval_week = ordering_interval_week;
	}
	
	public int getDelivery_day() {
		return delivery_day;
	}
	
	public void setDelivery_day(int delivery_day) {
		this.delivery_day = delivery_day;
	}
}
