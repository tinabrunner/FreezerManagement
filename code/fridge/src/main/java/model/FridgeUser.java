package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class FridgeUser implements Serializable {

	/**
	 * autogenerated ; needed for using serializable
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String role;

	public enum Roles {
		ADMIN, USER;
	};

	// default constructor
	public FridgeUser() {
	}

	public FridgeUser(String username, String password, String firstName, String lastName, String email, String role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;

		if (role.equals(Roles.ADMIN))
			this.role = Roles.ADMIN.toString();
		else
			this.role = Roles.USER.toString();

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "User [FirstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", username="
				+ this.getUsername() + ", email=" + this.getEmail() + ", role=" + this.getRole() + "]";
	}

}
