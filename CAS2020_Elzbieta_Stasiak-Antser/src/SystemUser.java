// Class used to store user account details from the UserAccounts.txt
public class SystemUser {
	
	private int userID;
	private String username;
	private String surname;
	private int houseNumber;
	private String postcode;
	private String city;
	private String role;
	
	public SystemUser(int userID, String username, String surname, int houseNumber, String postcode, String city, String role) {
		this.userID = userID;
		this.username = username;
		this.surname = surname;
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
		this.role = role;
	}
	
	public int get_userID() {
		return this.userID;
	}
	
	public String get_username() {
		return this.username;
	}
	
	public String get_postcode() {
		return this.postcode;
	}
	
	public String get_role() {
		return this.role;
	}

}

