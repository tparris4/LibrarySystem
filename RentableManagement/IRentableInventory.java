package LibraryManagementSystem.RentableManagement;

import java.util.ArrayList;
import java.sql.*;


public interface IRentableInventory
{

    /**
     * Takes a Rentable as input and add it to the database
     *
     * @param r the Rentable to be added to the inventory
     * @return returns true if Rentable was successfully added
     */
    public boolean addRentable(Rentable r);

    /**
     * Deletes a Rentable with a specific SKU from the database
     *
     * @param rentableSKU The SKU of the Rentable that needs to be removed
     * @return returns the removed Rentable
     */
    public Rentable removeRentable(int rentableSKU);

    /**
     * Searches the database for a list of Rentables
     *
     * @param searchType      The specific attribute being used to filter results
     * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
     * @return True if the search is successful and the desired Rentables are printed out
     */
    public boolean searchRentables(String searchType, String searchParameter);

    /**
     * Displays all Rentables in the database
     *
     * @return True if all Rentables are successfully printed out
     */
    public boolean viewRentables();
}
