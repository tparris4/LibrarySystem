package LibraryManagementSystem.AccountManagement;
/**
 * This class User�s personal data including User�s name, User�s account name,
 * password, address. Phone, account ID 
 * 
 * 
 * @author Jason Arikupurathu
 */
public class User extends Staff
{

	private double balance = 0;
	
		/**
	 * 
	 * @param id
	 *            User's I.D. that is unique from one another to be set
	 * @param fName
	 *            User's first name to be set
	 * @param lName
	 *            User's last name to be set
	 * @param email
	 *            User's email address to be set
	 * @param username
	 *            User's username to login to system to be set
	 * @param password
	 *            User's password to login to system to be set
	 * @param address
	 *            Address object that will contain User's address to be set
	 * @param balance
	 *            User's balance of what they may owe to the library to be set
	 * 
	 * @author Jason Arikupurathu
	 */
	public User(String id, String fName, String lName, String email, String username, String password,
			Address address, double balance) {
		super( id,fName, lName, email, username, password, address);
		this.balance = balance;
		// TODO Auto-generated constructor stub
		String oldID = getId();
		String newID = oldID.substring(0,0)+'U'+oldID.substring(1);
		setId(newID);
	}

	/**
	 * 
	 * @return The balance of the user
	 * @author Jason Arikupurathu
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * 
	 * @param balance
	 *            Amount of balance that is to be added
	 * @return True if balance was added
	 * @author Jason Arikupurathu
	 */
	public void increaseBalance(double adjustbalance) {
		double newBalance = (balance +adjustbalance);
		this.balance = newBalance;
	}
	
		/**
	 * 
	 * @param balance
	 *            Amount of balance that is to be subtracted
	 * @return True if balance was subtracted
	 * @author Jason Arikupurathu
	 */
	public void decreaseBalance(double adjustbalance) {
		double newBalance = (balance - adjustbalance);
		this.balance = newBalance;
	}

	@Override
	public String toString() {
		return super.toString()+ "[balance=" + balance + "]\n";
	}

}