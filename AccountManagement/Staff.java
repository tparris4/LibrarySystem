package LibraryManagementSystem.AccountManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Scanner;
/**
 * This class contains Staff�s personal data. 
 * Since staff contains less information than user, this is our base class
 * 
 * @author Jason Arikupurathu
 *
 */
public class Staff implements Serializable 
{

 	private String id;
 	private String fName;
 	private String lName;
 	private String email;
 	private String username;
 	private String password;
 	private Address address;
 	private String issuedID;
 	private static int idNum = 0;
 		/**
	 * 
	 * @param id
	 *            Staff's I.D. that is unique from one another to be set
	 * @param fName
	 *            Staff's first name to be set
	 * @param lName
	 *            Staff's last name to be set
	 * @param email
	 *            Staff's email address to be set
	 * @param username
	 *            Staff's username to login to system to be set
	 * @param password
	 *            Staff's password to login to system to be set
	 * @param address
	 *            Address object that will contain Staff's address to be set
	 * 
	 * @author Jason Arikupurathu
	 */
	public Staff(String id,String fName, String lName, String email, String username, String password,
			Address address) {
		
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.address = address;
		//This is to make sure no ID's are repeated
	/*
		File idFile = new File("IDNum.txt");
		try {
			Scanner input = new Scanner(idFile);
			idNum = input.nextInt();

		} catch (FileNotFoundException e1) {

		}

		this.id = String.valueOf(idNum++);

		for (int j = id.length(); j < 8; j++) {
			if (j == 7) {
				id = "S" + id;
				break;
			}
			id = "0" + id;
		}

		PrintWriter pw;
		try {
			pw = new PrintWriter("IDNum.txt");
			pw.print(idNum);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	
	/**
	 * 
	 * @return The ID that was last used
	 * @author Kwesi Quallis
	 */
	public static int getIdNum() {
		return idNum;
	}

	/**
	 * 
	 * @param idCounter
	 *            Set the id counter if necessary
	 * @author Kwesi Quallis
	 */
	public static void setIdNum(int idNum) {
		Staff.idNum = idNum;
	}

	/**
	 * 
	 * @return I.D. of user
	 * @author Kwesi Quallis
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @return The first name of user
	 * @Kwesi Quallis
	 */
	public String getfName() {
		return fName;
	}

		/**
	 * 
	 * @param fName
	 *            User's first name to be set
	 * @return True if user name was set
	 * @author Kwesi Quallis
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

		/**
	 * 
	 * @return The last name of user
	 * @author Kyle Coltellino
	 */
	public String getlName() {
		return lName;
	}

		/**
	 * 
	 * @param lName
	 *            The last name of user to be set
	 * @return True if last name was set
	 * @author Kyle Coltellino
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

		/**
	 * 
	 * @return Email address of user
	 * @author Kwesi Quallis
	 */
	public String getEmail() {
		return email;
	}

		/**
	 * 
	 * @param email
	 *            Email address of user that is to be set
	 * @return True if email address was changed
	 * @author Kwesi Quallis
	 */
	public void setEmail(String email) {
		this.email = email;
	}

		/**
	 * 
	 * @return The username of the user
	 * @author Jason Arikupurathu
	 */
	public String getUsername() {
		return username;
	}

		/**
	 * 
	 * @param username
	 *            Username that is to be set
	 * @return True if username was changed
	 * @author Jason Arikupurathu
	 */
	public void setUsername(String username) {
		this.username = username;
	}

		/**
	 * 
	 * @return The password of user
	 * @author Bryan Cassinera
	 */
	public String getPassword() {
		return password;
	}

		/**
	 * 
	 * @param password
	 *            The password the user would like to set
	 * @return True if the password was set
	 * @author Bryan Cassinera
	 */
	public void setPassword(String password) {
		this.password = password;
	}

		/**
	 * 
	 * @return Address object of user
	 * 
	 * @author Kyle Coltellino
	 */
	public Address getAddress() {
		return address;
	}

		/**
	 * 
	 * @param address
	 *            An address object of user to be set
	 * @return True if address was set
	 * 
	 * @author Kyle Coltellino
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public String issueID(char c)
	{
		
		File idFile = new File("IDNum.txt");
		try {
			Scanner input = new Scanner(idFile);
			idNum = input.nextInt();

		} catch (FileNotFoundException e1) {

		}

		this.issuedID = String.valueOf(idNum++);
		if(c =='S')
		{
			for (int j = issuedID.length(); j < 7; j++) {
				if (j == 6) {
					issuedID = "S" + issuedID;
					break;
				}
				issuedID = "0" + issuedID;
			}

			PrintWriter pw;
			try {
				pw = new PrintWriter("IDNum.txt");
				pw.print(idNum);
				pw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(c =='U')
		{
			for (int j = issuedID.length(); j < 7; j++) {
				if (j == 6) {
					issuedID = "U" + issuedID;
					break;
				}
				issuedID = "0" + issuedID;
			}

			PrintWriter pw;
			try {
				pw = new PrintWriter("IDNum.txt");
				pw.print(idNum);
				pw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return issuedID;
		
	}
	
	@Override
	public String toString() {
		return "ID: "+id+"\nName: "+fName+ " " +lName+"\nEmail: "+email+"\nUsername: "+
				username+"\n"+getAddress().toString()+ "\n";
	}
	
	
}