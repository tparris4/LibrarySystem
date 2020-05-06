package LibraryManagementSystem.ReservationManagement;

import java.sql.*;

/**
 * Contains methods for manipulating the entire collection of reservations in the database.
 */
public class ReservationCollection
{
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
        return ReservationCollectionJDBC.createReservation(newUpc, newUserId, newReservationType);
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
        return ReservationCollectionJDBC.deleteReservation(idToDelete);
    }

    /**
     * Builds a formatted string containing all the data of reservations in the database.
     * Is able to show a more narrow viewing of reservations based on the given 'selection' and 'idToView'.
     *
     * @param upcToView    If selection is '2' or '3', builds a string according to this given user/upc ID.
     * @param userIdToView If selection is '2' or '3', builds a string according to this given user/upc ID.
     * @param selection    '2' views by user ID, '3' views by upc ID, and any
     *                     other number views all reservations regardless of ID.
     * @return String of all reservations made in association with the given ID.
     * @throws SQLException Executes SQL database query.
     */
    public static String viewReservations(String reservationIdToView, String upcToView, String userIdToView, String selection) throws SQLException
    {
        return ReservationCollectionJDBC.viewReservations(reservationIdToView, upcToView, userIdToView, selection);
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
        return ReservationCollectionJDBC.cancelReservation(reservationIdToCancel, userIdLoggedIn);
    }

    /**
     * Calls deleteReservation on all inactive reservations in the database.
     *
     * @return True if one or more expired reservations were deleted; false if none.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean deleteAllExpiredReservations() throws SQLException
    {
        return ReservationCollectionJDBC.deleteAllExpiredReservations();
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
        return ReservationCollectionJDBC.searchByReservationId(idToSearch);
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
        return ReservationCollectionJDBC.searchByUpc(upcToSearch);
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
        return ReservationCollectionJDBC.searchByUserId(idToSearch);
    }

    /**
     * Returns true if the reservations table of the database is empty; false if not.
     *
     * @return True if the reservations table of the database is empty; false if not.
     * @throws SQLException Executes SQL database query.
     */
    public static Boolean isEmpty() throws SQLException
    {
        return ReservationCollectionJDBC.isEmpty();
    }

    /**
     * Takes a upc and returns its availability status.
     *
     * @param upcToSearch Upc to search and get availability.
     * @return true if available; false if not.
     * @throws SQLException executes SQL query.
     */
    public static boolean isAvailable(String upcToSearch) throws SQLException
    {
        return ReservationCollectionJDBC.isAvailable(upcToSearch);
    }

    /**
     * Sets a a rentable with a given upc to the given availability status.
     *
     * @param upcToSet    upc to update availability of.
     * @param statusToSet availability status to set.
     */
    public static void setRentableAvailabilityByUpc(String upcToSet, boolean statusToSet) throws SQLException
    {
        ReservationCollectionJDBC.setRentableAvailabilityByUpc(upcToSet, statusToSet);
    }
}
