package LibraryManagementSystem.ReservationManagement;

import LibraryManagementSystem.RentableManagement.IRentableInventory;
import LibraryManagementSystem.RentableManagement.RentableInventory;
import LibraryManagementSystem.RentableManagement.RentableInventoryJDBC;

import java.sql.*;
import java.time.Instant;

/**
 * Contains methods for manipulating the entire collection of reservations in the database (with JDBC).
 */
public class ReservationCollectionJDBC
{
    /**
     * JDBC Driver.
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    /**
     * Database URL.
     */
    private static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?&useSSL=false";
    /**
     * Database login username.
     */
    private static final String DATABASE_USER = "root";
    /**
     * Database login password.
     */
    private static final String DATABASE_PASSWORD = "g2t2";
    /**
     * Query statement.
     */
    private static Statement statement = null;
    /**
     * Manages results.
     */
    private static ResultSet resultSet = null;
    /**
     * Manages connection.
     */
    private static Connection connection = null;

    ReservationCollectionJDBC() throws ClassNotFoundException, SQLException
    {
        //load the driver class
        Class.forName(DRIVER);
        //establish connection to database
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        //create Statement for querying database
        statement = connection.createStatement();
        //enable reservationCollection methods
        new ReservationCollection();
    }


    /**
     * Takes reservation attributes, then creates a reservation
     * based on those attributes. Then adds that reservation
     * to the database.
     *
     * @param newUserId          ID of the user account that the reservation is under.
     * @param newUpc             ID of the upc that is reserved.
     * @param newReservationType Type of the upc that is reserved.
     * @return Created reservation.
     * @throws SQLException Executes SQL database query.
     */
    public static Reservation createReservation(String newUpc, String newUserId, String newReservationType) throws SQLException
    {
        Reservation newReservation = null;
        String roomNumber;
        PreparedStatement ps;

        if (ReservationCollection.isAvailable(newUpc) || newReservationType.equals("ROOM"))//if upc is available or if making room reservation
        {
            switch (newReservationType)
            {
                case "ITEM":
                    //instantiate new reservation
                    newReservation = new Reservation(newUpc, newUserId, newReservationType);
                    //add reservation to database
                    ps = connection.prepareStatement("INSERT INTO db_library.reservation " +
                            "(reservationId, upc, userId, roomNumber, reservationDate, " +
                            "reservationExpireDate, isActive, reservationType) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setObject(1, newReservation.getReservationId());
                    ps.setObject(2, newReservation.getUpc());
                    ps.setObject(3, newReservation.getUserId());
                    ps.setObject(4, newReservation.getRoomNumber());
                    ps.setObject(5, newReservation.getReservationDate());
                    ps.setObject(6, newReservation.getReservationExpireDate());
                    ps.setObject(7, newReservation.getActive());
                    ps.setObject(8, newReservation.getReservationType());
                    ps.execute();

                    ReservationCollection.setRentableAvailabilityByUpc(newReservation.getUpc(), false);
                    break;

                case "ROOM":
                    //check if user is already renting a room (one per account)
                    PreparedStatement psRoomCheck = connection.prepareStatement("SELECT * " +
                            "FROM db_library.reservation " +
                            "WHERE userId = ? " +
                            "AND reservationType LIKE 'ROOM' " +
                            "AND isActive = TRUE");
                    psRoomCheck.setObject(1, newUserId);
                    resultSet = psRoomCheck.executeQuery();

                    boolean hasRoomAlready = false;
                    if (resultSet.next())
                    {
                        hasRoomAlready = true;
                    }

                    if (!hasRoomAlready)
                    {
                        resultSet = statement.executeQuery("SELECT * FROM db_library.room " +
                                "WHERE isReserved = FALSE");
                        if (resultSet.next())
                        {
                            roomNumber = resultSet.getString("roomNumber");
                        } else
                        {
                            roomNumber = "";
                        }

                        ps = connection.prepareStatement("UPDATE db_library.room " +
                                "SET isReserved = TRUE " +
                                "WHERE roomNumber = ?");
                        ps.setObject(1, roomNumber);
                        ps.execute();

                        if (!roomNumber.equals(""))
                        {
                            newReservation = new Reservation(roomNumber, newUserId, newReservationType);
                            //add reservation to database
                            ps = connection.prepareStatement("INSERT INTO db_library.reservation " +
                                    "(reservationId, upc, userId, roomNumber, reservationDate, " +
                                    "reservationExpireDate, isActive, reservationType) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                            ps.setObject(1, newReservation.getReservationId());
                            ps.setObject(2, newReservation.getUpc());
                            ps.setObject(3, newReservation.getUserId());
                            ps.setObject(4, newReservation.getRoomNumber());
                            ps.setObject(5, newReservation.getReservationDate());
                            ps.setObject(6, newReservation.getReservationExpireDate());
                            ps.setObject(7, newReservation.getActive());
                            ps.setObject(8, newReservation.getReservationType());
                            ps.execute();
                        }
                    }
                    //else inform user that no room reservation was created.
                    break;
            }
        }
        return newReservation;
    }

    /**
     * Searches for a reservation ID in the database and, if found,
     * deletes the reservation with that reservation ID. Else, return false.
     *
     * @param idToDelete Reservation ID to search for and delete.
     * @return True if reservation was deleted; false if not
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean deleteReservation(String idToDelete) throws SQLException
    {
        if (searchByReservationId(idToDelete))
        {
            PreparedStatement ps1 = connection.prepareStatement("SELECT * " +
                    "FROM db_library.reservation " +
                    "WHERE reservationId = ?");
            ps1.setObject(1, idToDelete);
            resultSet = ps1.executeQuery();
            resultSet.next();

            if (resultSet.getString("reservationType").equals("ROOM"))//room
            {//update reserved status
                PreparedStatement ps2 = connection.prepareStatement("SELECT * " +
                        "FROM db_library.room " +
                        "WHERE roomNumber = ?");
                ps2.setObject(1, resultSet.getString("roomNumber"));
                resultSet = ps2.executeQuery();
                resultSet.next();

                PreparedStatement ps3 = connection.prepareStatement("UPDATE db_library.room " +
                        "SET isReserved = FALSE " +
                        "WHERE roomNumber = ?");
                ps3.setObject(1, resultSet.getString("roomNumber"));
                ps3.execute();
            } else//if item
            {//update availability status
                setRentableAvailabilityByUpc(resultSet.getString("upc"), true);
            }

            PreparedStatement ps5 = connection.prepareStatement("DELETE FROM db_library.reservation " +
                    "WHERE reservationId = ?");
            ps5.setObject(1, idToDelete);
            ps5.execute();

            return true;//true if deletion executed
        }
        return false;//false if no deletion executed
    }

    /**
     * Builds a formatted string containing all the data of reservations in the database.
     * Is able to show a more narrow viewing of reservations based on the given 'selection' and 'idToView'.
     *
     * @param reservationIdToView If selection is '1', '2' or '3', builds a string according to this given user/upc ID.
     * @param upcToView           If selection is '1', '2' or '3', builds a string according to this given user/upc ID.
     * @param userIdToView        If selection is '1', '2' or '3', builds a string according to this given user/upc ID.
     * @param selection           '2' views by user ID, '3' views by upc ID, and any
     *                            other number views all reservations regardless of ID.
     * @return String of all reservations made in association with the given ID.
     * @throws SQLException Executes SQL database query.
     */
    public static String viewReservations(String reservationIdToView, String upcToView, String userIdToView, String selection) throws SQLException
    {
        updateAllActiveStatus();

        StringBuilder reservationListConcat = new StringBuilder();

        int numberOfColumns = 0;
        ResultSetMetaData metaData = null;

        switch (selection)
        {
            case "1":
                resultSet = statement.executeQuery("SELECT * " +
                        "FROM db_library.reservation");
                metaData = resultSet.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;

            case "2":
                PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT * " +
                        "FROM db_library.reservation " +
                        "WHERE reservationId = ?");
                preparedStatement3.setObject(1, reservationIdToView);
                resultSet = preparedStatement3.executeQuery();

                metaData = preparedStatement3.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;

            case "3":
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * " +
                        "FROM db_library.reservation " +
                        "WHERE userId = ?");
                preparedStatement1.setObject(1, userIdToView);
                resultSet = preparedStatement1.executeQuery();

                metaData = preparedStatement1.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;

            case "4":
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * " +
                        "FROM db_library.reservation " +
                        "WHERE upc = ?");
                preparedStatement2.setObject(1, upcToView);
                resultSet = preparedStatement2.executeQuery();

                metaData = preparedStatement2.getMetaData();
                numberOfColumns = metaData.getColumnCount();
                break;
        }

        while (resultSet.next())
        {
            reservationListConcat.append('{');
            for (int i = 1; i <= numberOfColumns; i++)
            {
                reservationListConcat.append(metaData.getColumnName(i)).append('=').
                        append(resultSet.getObject(i));
                if (i == numberOfColumns)
                {
                    reservationListConcat.append('}');
                } else
                {
                    reservationListConcat.append(",\t");
                }
            }
            reservationListConcat.append("\n");
        }

        return reservationListConcat.toString();
    }

    /**
     * Takes a reservation ID of a reservation in the database that the logged in user has access to,
     * then cancels (calls deleteReservation on) the reservation.
     *
     * @param reservationIdToCancel Reservation ID to cancel.
     * @param userIdLoggedIn        User ID that is currently logged in.
     * @return True if reservation was cancelled; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean cancelReservation(String reservationIdToCancel, String userIdLoggedIn) throws SQLException
    {
        PreparedStatement ps1 = connection.prepareStatement("SELECT * " +
                "FROM db_library.reservation " +
                "WHERE reservationId = ?" +
                "AND userId = ?");
        ps1.setObject(1, reservationIdToCancel);
        ps1.setObject(2, userIdLoggedIn);
        resultSet = ps1.executeQuery();

        if (resultSet.next())
        {
            return deleteReservation(reservationIdToCancel);
        } else
        {
            return false;
        }
    }

    /**
     * Calls deleteReservation on all inactive reservations in the database.
     *
     * @return True if one or more expired reservations were deleted; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean deleteAllExpiredReservations() throws SQLException
    {
        updateAllActiveStatus();

        Boolean foundExpired = false;

        Statement statementSetAllExpired = connection.createStatement();
        ResultSet resultSetAllExpired = statementSetAllExpired.executeQuery("SELECT * " +
                "FROM db_library.reservation " +
                "WHERE isActive = FALSE");

        while (resultSetAllExpired.next())
        {
            foundExpired = deleteReservation(resultSetAllExpired.getString("reservationId"));
        }

        return foundExpired;
    }

    /**
     * Compares the current time to all reservation expiration dates in the database.
     * If the current time is after the expiration date, updates the active status of
     * that reservation to inactive.
     *
     * @throws SQLException Executes SQL database query.
     */
    private static void updateAllActiveStatus() throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM db_library.reservation");
        while (resultSet.next())
        {
            String cursorReservationId = resultSet.getString("reservationId");
            Timestamp cursorExpDate = resultSet.getTimestamp("reservationExpireDate");
            Timestamp currentDate = Timestamp.from(Instant.now());
            Boolean cursorIsActive = currentDate.before(cursorExpDate);
            String cursorRoomNumber = resultSet.getString("roomNumber");
            String cursorUpc = resultSet.getString("upc");

            if (!cursorIsActive)// if not active
            {
                //if room reservation, free up the room
                if (resultSet.getString("reservationType").equals("ROOM"))
                {
                    PreparedStatement ps1 = connection.prepareStatement("UPDATE db_library.room " +
                            "SET isReserved = FALSE " +
                            "WHERE roomNumber = ?");
                    ps1.setObject(1, cursorRoomNumber);
                    ps1.execute();
                }
                //update active status of reservation and rentables
                PreparedStatement ps2 = connection.prepareStatement("UPDATE db_library.reservation " +
                        "SET isActive = ? " +
                        "WHERE reservationId = ?");
                ps2.setObject(1, cursorIsActive);
                ps2.setObject(2, cursorReservationId);
                ps2.execute();

                setRentableAvailabilityByUpc(cursorUpc, true);
            }
        }
    }

    /**
     * Searches for a reservation in the database by reservation ID.
     *
     * @param idToSearch Reservation ID to search for.
     * @return True if the reservation with the given reservation ID is found; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByReservationId(String idToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  db_library.reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("reservationId").equals(idToSearch))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a reservation in the database by upc ID.
     *
     * @param upcToSearch upc ID to search for.
     * @return True if any reservation is found with the given upc ID; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByUpc(String upcToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  db_library.reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("upc").equals(upcToSearch))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for a reservation in the database by user ID.
     *
     * @param idToSearch User ID to search for.
     * @return True if any reservation is found under given user ID; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean searchByUserId(String idToSearch) throws SQLException
    {
        resultSet = statement.executeQuery("SELECT * FROM  db_library.reservation");
        while (resultSet.next())
        {
            if (resultSet.getString("userId").equals(idToSearch))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the reservations table of the database is empty; false if not.
     *
     * @return True if the reservations table of the database is empty; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean isEmpty() throws SQLException
    {
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM  db_library.reservation");
        resultSet.next();
        return resultSet.getInt(1) == 0;
    }

    /**
     * Takes a upc and returns its availability status.
     *
     * @param upcToSearch Upc to search and get availability.
     * @return true if available; false if not.
     * @throws SQLException executes SQL query.
     */
    public static Boolean isAvailable(String upcToSearch) throws SQLException
    {
        PreparedStatement ps = connection.prepareStatement("SELECT * " +
                "FROM db_library.rentable " +
                "WHERE upc = ? " +
                "AND " +
                "isAvailable = TRUE");
        ps.setObject(1, upcToSearch);
        resultSet = ps.executeQuery();

        return resultSet.next() && resultSet.getBoolean("isAvailable");
    }

    /**
     * Sets a a rentable with a given upc to the given availability status.
     *
     * @param upcToSet    upc to update availability of.
     * @param statusToSet availability status to set.
     */
    public static void setRentableAvailabilityByUpc(String upcToSet, Boolean statusToSet) throws SQLException
    {
        String rentableToSet = "";
        //get a rentable with that upc and the opposite status of the statusToSet
        PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM db_library.rentable WHERE isAvailable = ? AND upc = ?;");
        ps1.setObject(1, !statusToSet);
        ps1.setObject(2, upcToSet);
        resultSet = ps1.executeQuery();

        //if found, get its rentableId
        if (resultSet.next())
        {
            rentableToSet = resultSet.getString("rentableId");
        }
        //and switch its status
        PreparedStatement ps2 = connection.prepareStatement("UPDATE db_library.rentable SET isAvailable = ? WHERE rentableId = ?;");
        ps2.setObject(1, statusToSet);
        ps2.setObject(2, rentableToSet);
        ps2.execute();
    }
}
