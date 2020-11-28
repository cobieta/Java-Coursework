import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// This class is used to store an array of the user accounts.
public class UserAccounts {
	
	private SystemUser[] userArray;
	private int loggedInUser;
	
	public UserAccounts() {
		this.userArray = new SystemUser[4];
		read_userAccountsFile();
		this.loggedInUser = -1;
	}
	
	public SystemUser[] get_userArray() {
		return this.userArray;
	}
	
	// Creates and returns an array of all the usernames in the userArray.
	public String[] create_usernameArray() {
		String[] allUsernames = new String[this.userArray.length];
		for (int i=0; i<allUsernames.length; i++) {
			allUsernames[i] = this.userArray[i].get_username();
		}
		return allUsernames;
	}
	
	// Get the current logged in user otherwise return -1 if no user is logged into the system currently. 
	public SystemUser get_loggedInUser() {
		if (this.loggedInUser == -1) {
			System.out.println("Error, No user is currently logged into the system.");
			return null;
		} else {
			return this.userArray[this.loggedInUser];
		}
	}
	
	// Set the value of the loggedInUser attribute to be the index in the user array of the user that is currently logged in.
	public void set_loggedInUser(int index) {
		this.loggedInUser = index;
	}
	
	// Private method to read the UserAccounts.txt and create SystemUser objects from the data.
	private void read_userAccountsFile() {
		Scanner fileScanner = null;
		try {
			File inputFile = new File("UserAccounts.txt");
			fileScanner = new Scanner(inputFile);
			int arrayPos = 0;
			while (fileScanner.hasNextLine()) {
				String[] lineArray = fileScanner.nextLine().split(",");
				SystemUser user = new SystemUser(Integer.parseInt(lineArray[0].trim()), lineArray[1].trim(), lineArray[2].trim(), Integer.parseInt(lineArray[3].trim()), lineArray[4].trim(), lineArray[5].trim(), lineArray[6].trim());
				this.userArray[arrayPos] = user;
				arrayPos++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			fileScanner.close();
		}
	}
	
}
