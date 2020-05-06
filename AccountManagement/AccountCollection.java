package LibraryManagementSystem.AccountManagement;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Allows application to be bridged with JDBC class
 * 
 * @author Bryan Cassinera
 */

public class AccountCollection 
{

	static AccountCollectionJDBC jdbc = new AccountCollectionJDBC();
	
	/**
	 * 
	 * @param s
	 *            The user that is to be added to the system
	 * @return True if the user was successfully added
	 * 
	 * @author Jason Arikupurathu
	 */

	public boolean add(Staff s) throws SQLException, ClassNotFoundException {
		return jdbc.add(s);
		
	}
	
		/**
	 * 
	 * @param id
	 *            The id of the user to be removed
	 * @return The user that was removed
	 * 
	 * @author Bryan Cassinera
	 */

	public Staff removeByID(String id) 
	{
		try {
			return jdbc.removeByID(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 */

	public Staff search(String idNum) 
	{
		try {
			return jdbc.search(idNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	


	/**
	 * 
	 * @return All accounts in the system
	 * 
	 * @author Kwesi Quallis
	 * @throws SQLException 
	 */

	public String View() throws SQLException 
	{
		return jdbc.View();
	}

	/**
	 * 
	 * @param user
	 *            The user object that contains updated fields
	 * @param id
	 *            The ID of the user that wants to update something
	 * 
	 * @author Kwesi Quallis
	 */
	public static Staff update(Staff s) throws SQLException
	{
	return jdbc.update(s);
	}
	
	/**
	 * 
	 * @param password
	 *            The updated password string
	 * @param user
	 *            The updated user object with the new password
	 * @param id
	 *            The ID of the user that is to be used to update the account
	 * @author Kyle Coltellino
	 * @throws SQLException 
	 */	
	public boolean changePassword(String password, Staff staff) throws SQLException
	{
		return jdbc.changePassword(password, staff);
	}

	/**
	 * 
	 * @param User object to get ID as well as current balance
	 * @return The updated user object
	 * @throws SQLException
	 * @author Jason Arikupurathu
	 */
	public static User updateBalance(User u) throws SQLException {
		return jdbc.updateBalance(u);
	}
}