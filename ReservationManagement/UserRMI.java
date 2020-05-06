package LibraryManagementSystem.ReservationManagement;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * User Reservation Management Interface; for use in TestRMI method.
 */
public class UserRMI//User Reservation Management Interface
{
    /**
     * Logged in user account id.
     */
    private String userIdLoggedIn;

    /**
     * Instantiates the reservation management interface.
     *
     * @throws SQLException Initializes SQL statement variable.
     */
    public UserRMI(String userId) throws SQLException, ClassNotFoundException
    {
        //enable reservationCollection methods
        new ReservationCollectionJDBC();
        //set logged in user account id
        userIdLoggedIn = userId;
        //display reservation management main menu to user
        manageReservationsUser();
    }

    /**
     * Displays the reservations management main menu.
     * Allows the staff to manipulate the data in the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void manageReservationsUser() throws SQLException
    {
        String selection = "0";
        while (!selection.equals("4"))
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Manage Reservations..." +
                    "\n1. Create Reservations" +
                    "\n2. Cancel Reservations" +
                    "\n3. View Reservations" +
                    "\n4. User Main Menu" +
                    "\n> ");
            selection = scanner.next();
            selection = InputValidation.validateSelection(selection, 4);

            switch (selection)
            {
                case "1"://create reservations
                    createReservationUser();
                    break;

                case "2"://cancel reservations
                    cancelReservations();
                    break;

                case "3"://view reservations
                    viewReservationsUser();
                    break;

                case "4"://user main menu
                    break;//exit menu
            }
        }
    }

    /**
     * Displays the create reservations menu for a user member.
     * Allows the user to insert data into the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void createReservationUser() throws SQLException
    {
        Scanner stringScanner = new Scanner(System.in);

        String selection = "0";
        while (!selection.equals("4"))
        {
            System.out.print("Create Reservation for: " +
                    "\n1. Reserve Item" +
                    "\n2. Reserve Room" +
                    "\n3. View Reservations" +
                    "\n4. Previous Menu" +
                    "\n> ");
            selection = stringScanner.next();
            selection = InputValidation.validateSelection(selection, 4);

            String upcToReserve;
            switch (selection)
            {
                case "1": //to reserve item
                    System.out.print("Enter the Item's UPC:\n> ");
                    upcToReserve = stringScanner.next();
                    upcToReserve = InputValidation.validateNumericId(upcToReserve, 7);
                    Reservation itemReservation = ReservationCollection.createReservation(upcToReserve, userIdLoggedIn, "ITEM");

                    if (itemReservation == null)
                    {
                        System.out.println("No items with that UPC are available.");
                    } else
                    {
                        System.out.println("Created a new item reservation...\n" + itemReservation);
                    }
                    break;

                case "2": //to reserve Activity Room
                    Reservation roomReservation = ReservationCollection.createReservation("Room#", userIdLoggedIn, "ROOM");
                    if (roomReservation == null)
                    {
                        System.out.println("Unable to create room reservation.");
                    } else
                    {
                        System.out.println("Your room reservation is...\n" + roomReservation);
                    }
                    break;

                case "3": //viewReservations
                    viewReservationsUser();
                    break;

                case "4": //back to manage reservations menu
                    break;
            }
        }
    }

    /**
     * Displays the cancel reservations menu to a user member.
     * Allows the user to remove data from the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void cancelReservations() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);

        String selection = "0";
        while (!selection.equals("3"))
        {
            System.out.print("Cancel Reservations... " +
                    "\n1. Enter Reservation ID for Cancellation" +
                    "\n2. View Reservations" +
                    "\n3. Previous Menu" +
                    "\n> ");
            selection = scanner.next();
            selection = InputValidation.validateSelection(selection, 3);

            switch (selection)
            {
                case "1": //enter reservation id for cancellation
                    System.out.print("Reservation ID:\n> ");
                    String reservationIdEntered = scanner.next();
                    reservationIdEntered = InputValidation.validateNumericId(reservationIdEntered, 7);

                    Boolean isCanceled = ReservationCollection.cancelReservation(reservationIdEntered, userIdLoggedIn);
                    if (isCanceled)
                    {
                        System.out.println("Reservation " + reservationIdEntered +
                                " is canceled.");
                    } else
                    {
                        System.out.println("Reservation with ID " +
                                reservationIdEntered + " not found."
                                + "\nNo action taken.");
                    }
                    break;

                case "2": //view your reservations
                    viewReservationsUser();
                    break;

                case "3": // back to manage reservations menu
                    break;
            }
        }
    }

    /**
     * Displays the reservations under the logged in users account to the user.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void viewReservationsUser() throws SQLException
    {
        if (!ReservationCollection.searchByUserId(userIdLoggedIn))
        {
            System.out.println("No reservations found.");
        } else
        {
            System.out.print("Reservations under your account...\n" +
                    ReservationCollection.viewReservations("", "", userIdLoggedIn, "3"));
        }
    }
}
