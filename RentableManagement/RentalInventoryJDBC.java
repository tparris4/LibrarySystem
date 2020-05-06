package LibraryManagementSystem.RentableManagement;

import java.util.ArrayList;
import java.sql.*;

public class RentalInventoryJDBC implements IRentalInventory {
	private ArrayList<Rental> rentalList;
	private Connection conn = null;
	private Statement stmt = null;
	private final String URL = "jdbc:mysql://127.0.0.1:3306/db_library?useSSL=false&autoReconnect=true";
	private final String uName = "root";
	private final String uPass = "batman";
	private int nextSku;

	public RentalInventoryJDBC() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Checks a Rentable back into the library and deletes its Rental and checks if there are any reservations for the returned Rentable
	 * @param sku the SKU of the Rentable being checked in
	 * @return returns true if the Rentable is successfully checked in
	 */
	@Override
	public boolean checkIn(int sku) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Checks a Rentable out of the library and creates a Rental for it
	 * @param sku The SKU of the Rentable being checked out
	 * @param userId The userId of the user checking out the Rentable
	 * @return returns true if the Rentable is successfully checked out
	 */
	@Override
	public boolean checkOut(int sku, String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Extends the end date of a Rental
	 * @param r The rental to renew
	 * @return returns true if the rental is successfully renewed
	 */
	@Override
	public boolean renewRental(Rental r) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Used for filtering Rentals based on a specific input parameter
	 * @param searchType The attribute being used to filter results
	 * @param searchParameters The parameter being compared to the rentals in the database to determine what will be returned
	 * @return True if the search is successful and the desired Rentals are printed out
	 */
	@Override
	public boolean searchRentals(String searchType, String searchParameters) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Used for displaying all existing rentals
	 * 
	 * @return Returns an ArrayList of all existing Rentals
	 */
	@Override
	public boolean viewRentals() {
		String sql = "";
		boolean result = false;
		sql = "SELECT * FROM Rental;";
		Statement statement = null;
		ResultSet resultSet;
		try {
			conn = DriverManager.getConnection(URL, uName, uPass);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				System.out.println("SKU: " + resultSet.getString(1) + ", Start Date: " + resultSet.getString(2)
						+ ", End Date: " + resultSet.getString(3) + ", User Id: " + resultSet.getString(4)
						+ ", Times Renewed: " + resultSet.getString(5));
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}
}
