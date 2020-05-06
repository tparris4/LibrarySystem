package LibraryManagementSystem.RentableManagement;

import java.util.ArrayList;

public class RentalInventory implements IRentalInventory {
	
	private RentalInventoryJDBC inventory;
	
	
	public RentalInventory() {
		inventory = new RentalInventoryJDBC();
	}

	/**
	 * Checks a Rentable back into the library and deletes its Rental and checks if there are any reservations for the returned Rentable
	 * @param sku the SKU of the Rentable being checked in
	 * @return returns true if the Rentable is successfully checked in
	 */
	@Override
	public boolean checkIn(int sku) {
		return inventory.checkIn(sku);
	}

	/**
	 * Checks a Rentable out of the library and creates a Rental for it
	 * @param sku The SKU of the Rentable being checked out
	 * @param userId The userId of the user checking out the Rentable
	 * @return returns true if the Rentable is successfully checked out
	 */
	@Override
	public boolean checkOut(int sku, String userId) {
		return inventory.checkOut(sku, userId);
	}

	/**
	 * Extends the end date of a Rental
	 * @param r The rental to renew
	 * @return returns true if the rental is successfully renewed
	 */
	@Override
	public boolean renewRental(Rental r) {
		return inventory.renewRental(r);
	}

	/**
	 * Used for filtering Rentals based on a specific input parameter
	 * @param searchType The attribute being used to filter results
	 * @param searchParameters The parameter being compared to the rentals in the database to determine what will be returned
	 * @return True if the search is successful and the desired Rentals are printed out
	 */
	@Override
	public boolean searchRentals(String searchType, String searchParameters) {
		return inventory.searchRentals(searchType, searchParameters);
	}
	
	/**
	 * Used for displaying all existing rentals
	 * 
	 * @return Returns an ArrayList of all existing Rentals
	 */
	@Override
	public boolean viewRentals() {
		return inventory.viewRentals();
	}

}
