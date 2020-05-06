package LibraryManagementSystem.RentableManagement;

import java.util.ArrayList;
import java.sql.*;


public class RentableInventoryJDBC implements IRentableInventory
{
    private ArrayList<Rentable> rentableList;
    private static final String URL = "jdbc:mysql://localhost/db_library?useSSL=false&autoReconnect=true";
    private static final String uName = "root";
    private static final String uPass = "g2t2";
    private static Connection conn = null;
    private static Statement statement = null;
    private int nextSku;

    /**
     * Initiates an new RentableInventory
     */

    public RentableInventoryJDBC() throws SQLException
    {
        conn = DriverManager.getConnection(URL, uName, uPass);
        statement = conn.createStatement();
    }

    /**
     * Takes a Rentable as input and add it to the database
     *
     * @param r the Rentable to be added to the inventory
     * @return returns true if Rentable was successfully added
     */
    @Override
    public boolean addRentable(Rentable r)
    {
        String query = r.getType();

        if (r.getType().toLowerCase().equals("room"))
        {
            query = "INSERT INTO Rentable VALUES( null, " + "null" + ", "
                    + "null" + ", " + "null" + ", " + "null" + ", "
                    + "null" + ", '" + r.getRoomNumber() + "');";
        } else if (r.getType().toLowerCase().equals("dvd"))
        {
            query = "INSERT INTO Rentable VALUES( null, '" + r.getTitle() + "', "
                    + "null" + ", '" + r.getCondition() + "', '" + r.getGenre() + "', '"
                    + r.getType() + "', null);";
        } else if (r.getType().toLowerCase().equals("book") || r.getType().toLowerCase().equals("ebook"))
        {
            query = "INSERT INTO Rentable VALUES( null, '" + r.getTitle() + "', '" + r.getIsbn() + "', '"
                    + r.getCondition() + "', '" + r.getGenre() + "', '" + r.getType() + "', null);";
        }
        Statement statement = null;
        int test = 0;
        try
        {
            statement = conn.createStatement();
            test = statement.executeUpdate(query);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
            try
            {
                conn.close();
                return true;
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Deletes a Rentable with a specific SKU from the database
     *
     * @param rentableSKU The SKU of the Rentable that needs to be removed
     * @return returns the removed Rentable
     */
    @Override
    public Rentable removeRentable(int rentableSKU)
    {
        // TODO
        return null;
    }

    /**
     * Searches the database for a list of Rentables
     *
     * @param searchType      The specific attribute being used to filter results
     * @param searchParameter The parameter that is being compared to the Rentables in the database to determine what will be returned
     * @return True if the search is successful and the desired Rentables are printed out
     */
    @Override
    public boolean searchRentables(String searchType, String searchParameter)
    {
        String sql = "";
        boolean result = false;
        sql = "SELECT * FROM Rentable WHERE rentable." + searchType + " = '" + searchParameter + "';";


        Statement statement = null;
        ResultSet resultSet;
        try
        {
            resultSet = statement.executeQuery(sql);

            if (!resultSet.next())
                System.out.println("No Results Found");
            while (resultSet.next())
            {
                String type = resultSet.getString(6);

                if (type.toLowerCase().equals("book") || type.toLowerCase().equals("ebook"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Title: " + resultSet.getString(2)
                            + ", ISBN: " + resultSet.getString(3)
                            + ", Condition: " + resultSet.getString(4)
                            + ", Genre: " + resultSet.getString(5)
                            + ", Type: " + resultSet.getString(6));
                } else if (type.toLowerCase().equals("dvd"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Title: " + resultSet.getString(2)
                            + ", Condition: " + resultSet.getString(4)
                            + ", Genre: " + resultSet.getString(5)
                            + ", Type: " + resultSet.getString(6));
                } else if (type.toLowerCase().equals("room"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Room Number: " + resultSet.getString(7));
                }

            }
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Displays all Rentables in the database
     *
     * @return The arraylist of all Rentables in the database
     */
    @Override
    public boolean viewRentables()
    {
        String sql = "";
        boolean result = false;
        sql = "SELECT * FROM Rentable;";

        ResultSet resultSet;
        try
        {
            resultSet = statement.executeQuery(sql);

            if (!resultSet.next())
                System.out.println("No Results Found");
            while (resultSet.next())
            {
                String type = resultSet.getString(6);

                if (type.toLowerCase().equals("book") || type.toLowerCase().equals("ebook"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Title: " + resultSet.getString(2)
                            + ", ISBN: " + resultSet.getString(3)
                            + ", Condition: " + resultSet.getString(4)
                            + ", Genre: " + resultSet.getString(5)
                            + ", Type: " + resultSet.getString(6));
                } else if (type.toLowerCase().equals("dvd"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Title: " + resultSet.getString(2)
                            + ", Condition: " + resultSet.getString(4)
                            + ", Genre: " + resultSet.getString(5)
                            + ", Type: " + resultSet.getString(6));
                } else if (type.toLowerCase().equals("room"))
                {
                    System.out.println("SKU: " + resultSet.getString(1)
                            + ", Room Number: " + resultSet.getString(7));
                }

            }
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                statement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return result;
    }
}
