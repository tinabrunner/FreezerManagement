package controller;

import db_communication.DB_FridgeUser;
import model.FridgeUser;

public class AccountController {

	private DB_FridgeUser db_fridgeUser;
	
	// Method to create an account and write the user to DB
	public boolean createAccount (String name, String surname, String username, String password, String email, String userrole) {		
		// DB_FridgeUser checks, if the username already exists
		FridgeUser fridgeUser = new FridgeUser(name, surname, username, password, email, userrole);
		if (db_fridgeUser.insertUserToDB(fridgeUser))
			return true;
		else {
			System.out.print("Username already exists! Please enter another username!");
			return false;
		}
	}
	
	// Method to edit an Account and write the changes to the DB
	public boolean editAccount (String name, String surname, String username, String password, String email, String userrole) {
		
		// TODO: Checken ob überhaupt Attribut-Werte verändert wurde !?
		
		FridgeUser fridgeUser = new FridgeUser(name, surname, username, password, email, userrole);
		db_fridgeUser.updateUserFromDB(fridgeUser);
		return true;
	}
	

	// Method to delete an Account
	public boolean deleteAccount (String username) {
		if (db_fridgeUser.deleteUserFromDB(username))
			return true;
		else {
			System.out.println("The User with the Username: "+username+" could not be deleted!");
			return false;
		}
	}
	
}
