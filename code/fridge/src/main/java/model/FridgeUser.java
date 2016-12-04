package model;

public class FridgeUser {
	
	private static int count_id = 1;
	private int id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String role;
	
	public FridgeUser (String name, String surname, String username, String password, String email, String role) {
		this.id = count_id;
		count_id ++;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	public FridgeUser (String name, String surname, String username, String password, String email) {
		this.id = count_id;
		count_id ++;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = "user";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getRole() {
		return role;
	}
	
	

}
