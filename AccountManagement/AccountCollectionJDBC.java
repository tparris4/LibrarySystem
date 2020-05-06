package LibraryManagementSystem.AccountManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Allows for access to JDBC for reading and writing purposes
 * 
 * @author Kyle Coltellino
 *
 */

public class AccountCollectionJDBC 
{
	ArrayList<Staff> useraccountCollection = new ArrayList();
	ArrayList<Staff> staffaccountCollection = new ArrayList();
	static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?useSSL=false";
	static final String MYSQL_USERNAME ="root";
	static final String MYSQL_PASSWORD ="g2t2";
	Connection connection = null; // manages connection
    Statement statement = null; // query statement
    ResultSet resultSet = null; // manages results
 
	/**
	 * 
	 * @param s
	 *            The user that is to be added to the system
	 * @return True if the user was successfully added
	 * 
	 * @author Jason Arikupurathu
	 */

	public boolean add(Staff s) throws SQLException, ClassNotFoundException {
			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection( DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);

			statement = connection.createStatement();

	     //if staff
		    if(s instanceof User)
		    {
		    	useraccountCollection.add(s);
		    	String sqlID ="INSERT INTO accountid (accountId) VALUES ('"+s.getId()+"')";
		    	String sql ="INSERT INTO useraccount (UserID, FirstName, LastName, Email, " +
							"Username,Password, StreetNum, StreetName, City, State, Zip, Balance) " +
							"VALUES ('"+s.getId()+"','"+s.getfName()+"','"+s.getlName()+"','"+s.getEmail()+"','"+s.getUsername()+"','"+s.getPassword()+"','"+s.getAddress().getStreetNum()+"','"+s.getAddress().getStreetName()+
		                    "','"+s.getAddress().getCity()+"','"+s.getAddress().getState()+"','"+s.getAddress().getZipCode()+"','"+0.00+"')";



				statement.executeUpdate(sqlID);
				statement.executeUpdate(sql);
				return true;

		    }
		    
	     if(s instanceof Staff)
	     {
	    	 staffaccountCollection.add(s);
			 String sqlID ="INSERT INTO accountid (accountId) VALUES ('"+s.getId()+"')";
			 String sql ="INSERT INTO staffaccount (StaffID, FirstName, LastName, Email, " +
					 "Username,Password, StreetNum, StreetName, City, State, Zip) " +
					 "VALUES ('"+s.getId()+"','"+s.getfName()+"','"+s.getlName()+"','"+s.getEmail()+"','"+s.getUsername()+"','"+s.getPassword()+"','"+s.getAddress().getStreetNum()+"','"+s.getAddress().getStreetName()+
					 "','"+s.getAddress().getCity()+"','"+s.getAddress().getState()+"','"+s.getAddress().getZipCode()+"')";
		     
		     
		     

			statement.executeUpdate(sqlID);
			statement.executeUpdate(sql);
			return true;

	     }
	    
	     return false;	     
	}
	
	

	/**
	 * 
	 * @param id
	 *            The id of the user to be removed
	 * @return The user that was removed
	 * 
	 * @author Bryan Cassinera
	 * @throws SQLException 
	 */
	public Staff removeByID(String id) throws SQLException 
	{
		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
     	ResultSet rstRemove = null;
     	char accountType = id.charAt(0);
		
		if(accountType == 'U') {
			String sqlSelect = "SELECT * FROM useraccount WHERE UserID = " + "'"+id+"'";
			String sqlDelete = "DELETE FROM useraccount WHERE UserID = " + "'"+id+"'";
			String sqlDeleteID = "DELETE FROM accountId WHERE accountId = " + "'"+id+"'";
			rstRemove = myState.executeQuery(sqlSelect);
			
			User user = null;
			while(rstRemove.next())
			{
				Address a1 = new Address(rstRemove.getString("StreetNum"),rstRemove.getString("StreetName"),rstRemove.getString("City"),rstRemove.getString("State"),rstRemove.getString("Zip"));
				user = new User(rstRemove.getString("UserID"),rstRemove.getString("FirstName"), rstRemove.getString("LastName"), rstRemove.getString("Email"), rstRemove.getString("Username"),rstRemove.getString("Password"), a1, rstRemove.getDouble("Balance") );
				useraccountCollection.remove(user);
			}
			myState.executeUpdate(sqlDelete);
			myState.executeUpdate(sqlDeleteID);
			return user;
		}
		
		if(accountType =='S') {
			String sqlSelect = "SELECT * FROM staffaccount WHERE StaffID = " + "'"+id+"'";
			String sqlDelete = "DELETE FROM staffaccount WHERE StaffID = " + "'"+id+"'";
			String sqlDeleteID = "DELETE FROM accountId WHERE accountId = " + "'"+id+"'";
			rstRemove = myState.executeQuery(sqlSelect);
			Staff staff = null;
			while(rstRemove.next())
			{
				Address a1 = new Address(rstRemove.getString("StreetNum"),rstRemove.getString("StreetName"),rstRemove.getString("City"),rstRemove.getString("State"),rstRemove.getString("Zip"));
				staff = new Staff(rstRemove.getString("StaffID"),rstRemove.getString("FirstName"), rstRemove.getString("LastName"), rstRemove.getString("Email"), rstRemove.getString("Username"),rstRemove.getString("Password"), a1);
				staffaccountCollection.remove(staff);
			}
			myState.executeUpdate(sqlDelete);
			myState.executeUpdate(sqlDeleteID);
			return staff;
		}
		
		return null;
	}
	

	/**
	 * 
	 * @param idNum
	 *            The ID that is to search for account
	 * @return The account that is associated with the id
	 * 
	 * @author Bryan Cassinera
	 * @throws SQLException 
	 */
	public Staff search(String idNum) throws SQLException 
	{
		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
     	ResultSet rstSearch = null;
     	char accountType = idNum.charAt(0);
		
		if(accountType == 'U') {
			String sql = "SELECT * FROM useraccount WHERE UserId = " +"'"+idNum+"'";
			rstSearch = myState.executeQuery(sql);
			User user = null;
			while(rstSearch.next())
			{
				Address a1 = new Address(rstSearch.getString("StreetNum"),rstSearch.getString("StreetName"),rstSearch.getString("City"),rstSearch.getString("State"),rstSearch.getString("Zip"));
				user = new User(rstSearch.getString("UserID"),rstSearch.getString("FirstName"), rstSearch.getString("LastName"), rstSearch.getString("Email"), rstSearch.getString("Username"),rstSearch.getString("Password"), a1, rstSearch.getDouble("Balance"));
				return user;
			}
			
		}
		
		if(accountType =='S') {
			String sql = "SELECT * FROM staffaccount WHERE StaffId = " +"'"+idNum+"'";
			Staff staff = null;
			rstSearch = myState.executeQuery(sql);
			while(rstSearch.next())
			{
				Address a1 = new Address(rstSearch.getString("StreetNum"),rstSearch.getString("StreetName"),rstSearch.getString("City"),rstSearch.getString("State"),rstSearch.getString("Zip"));
				staff = new Staff(rstSearch.getString("StaffID"),rstSearch.getString("FirstName"), rstSearch.getString("LastName"), rstSearch.getString("Email"), rstSearch.getString("Username"),rstSearch.getString("Password"), a1);
				return staff;
			}
		}

		return null;
	}

	/**
	 * @return All accounts in the system
	 * 
	 * @author Kwesi Quallis
	 * @throws SQLException 
	 */
	public String View() throws SQLException 
	{   
		userDataTOUserArrayList(); 
		staffDataTOStaffArrayList();
		return userDataTOUserArrayList().toString() + staffDataTOStaffArrayList().toString();
	}
	
	/**
	 * 
	 * @param s
	 *            The user object that contains updated field
	 * 
	 * @author Kwesi Quallis
	 */
	public static Staff update(Staff s) throws SQLException
	{
		char accountType = s.getId().charAt(0);
		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
		if(accountType == 'S')
		{
		String sql = "UPDATE staffaccount SET firstname = '"+s.getfName()+"',lastname = '"+s.getlName()+"', email = '"+s.getEmail()+"', username = '"+s.getUsername()+"', streetnum = '"+
		s.getAddress().getStreetNum()+"', streetname = '"+s.getAddress().getStreetName()+"', city = '"+s.getAddress().getCity()+"', state = '"+s.getAddress().getState()+
		"', zip = '"+s.getAddress().getZipCode()+"' WHERE staffid LIKE '"+s.getId()+"'";
		myState.execute(sql);
		}
		else if(accountType =='U')
		{
			String sql = "UPDATE useraccount SET firstname = '"+s.getfName()+"',lastname = '"+s.getlName()+"', email = '"+s.getEmail()+"', username = '"+s.getUsername()+"', streetnum = '"+
					s.getAddress().getStreetNum()+"', streetname = '"+s.getAddress().getStreetName()+"', city = '"+s.getAddress().getCity()+"', state = '"+s.getAddress().getState()+
					"', zip = '"+s.getAddress().getZipCode()+"' WHERE userid LIKE '"+s.getId()+"'";
					myState.execute(sql);
			
		}
		return s;
	}
	
	/**
	 * 
	 * @param newPassword
	 *            The updated password string
	 * @param s
	 *            The updated user object with the new password
	 * 
	 * @author Kyle Coltellino
	 * @throws SQLException 
	 */
	public boolean changePassword(String newPassword, Staff s) throws SQLException
	{
		
		char accountType = s.getId().charAt(0);
		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
		if(accountType == 'S')
		{
		String sql = "UPDATE staffaccount SET password = '"+newPassword+"' WHERE staffid LIKE '"+s.getId()+"'";
			myState.execute(sql);
			return true;
		}
		else if(accountType =='U')
		{
			String sql = "UPDATE userAccount SET password = '"+newPassword+"' WHERE userID LIKE '"+s.getId()+"'";
			myState.execute(sql);
			return true;
		}
		return false;
	}
	
	
	public ArrayList<Staff> staffDataTOStaffArrayList() throws SQLException
	  {
		connection = DriverManager.getConnection( DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD );
		statement = connection.createStatement();
	  	String sql = "Select * From StaffAccount"; 
	  	ResultSet rst;
	  	rst = statement.executeQuery(sql);
	  	ArrayList<Staff> staffList = new ArrayList<>(); 
	  	while (rst.next()) 
	  	{ 
		  Address a1 = new Address(rst.getString("StreetNum"),rst.getString("StreetName"),rst.getString("City"),rst.getString("State"),rst.getString("Zip"));
		  Staff staff = new Staff(rst.getString("StaffID"),rst.getString("FirstName"), rst.getString("LastName"), rst.getString("Email"), rst.getString("Username"),rst.getString("Password"), a1); 
		  staffList.add(staff);
		} 
	  	return staffList;
	  }
	
	public ArrayList<User> userDataTOUserArrayList() throws SQLException
	  {
		connection = DriverManager.getConnection( DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD );
		statement = connection.createStatement();
	  	String sql = "Select * From UserAccount"; 
	  	ResultSet rst;
	  	rst = statement.executeQuery(sql);
	  	ArrayList<User> userList = new ArrayList<>(); 
	  	while (rst.next()) 
	  	{ 
		  Address a1 = new Address(rst.getString("StreetNum"),rst.getString("StreetName"),rst.getString("City"),rst.getString("State"),rst.getString("Zip"));
		  User user = new User(rst.getString("UserID"),rst.getString("FirstName"), rst.getString("LastName"), rst.getString("Email"), rst.getString("Username"),rst.getString("Password"), a1,rst.getDouble("Balance")); 
		  userList.add(user); 
		} 
	  	return userList;
	  }


	/**
	 * 
	 * @param u User object to get ID as well as current balance
	 * @return The updated user object
	 * @throws SQLException
	 * @author Jason Arikupurathu
	 */
	public User updateBalance(User u) throws SQLException {
		Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		Statement myState = myConn.createStatement();
     	char accountType = u.getId().charAt(0);
     	
     	if(accountType =='U')
     	{
     		String sql = "UPDATE useraccount SET balance = '"+u.getBalance()+"' WHERE userid LIKE '"+u.getId()+"'";
     		myState.execute(sql);
     	}
     	return u;
	}
	
}