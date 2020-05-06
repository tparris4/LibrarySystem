package LibraryManagementSystem.ReservationManagement;

import java.util.Scanner;
import java.sql.*;

/**
 * Staff Reservation Management Interface; for use in TestRMI method.
 */
public class StaffRMI//Staff Reservation Management Interface
{
    /**
     * Instantiates the reservation management interface.
     *
     * @throws SQLException Initializes SQL statement variable.
     */
    public StaffRMI() throws SQLException, ClassNotFoundException
    {
        //enable reservationCollection methods
        new ReservationCollectionJDBC();
        //display reservation management main menu to staff
        manageReservationsStaff();
    }

    /**
     * Displays the reservations management main menu.
     * Allows the staff to manipulate the data in the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void manageReservationsStaff() throws SQLException
    {
        String selection = "0";
        while (!selection.equals("4"))
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Manage Reservations..." +
                    "\n1. Create Reservations" +
                    "\n2. Delete Reservations" +
                    "\n3. View Reservations" +
                    "\n4. Staff Main Menu" +
                    "\n> ");
            selection = scanner.next();
            selection = InputValidation.validateSelection(selection, 4);

            switch (selection)
            {
                case "1"://create reservations
                    createReservationsStaff();
                    break;

                case "2"://delete reservations
                    deleteReservations();
                    break;

                case "3"://view reservations
                    viewReservationsStaff();
                    break;

                case "4"://staff main menu
                    break;//exit menu
            }
        }
    }

    /**
     * Displays the create reservations menu for a staff member.
     * Allows the staff to insert data into the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void createReservationsStaff() throws SQLException
    {
        Scanner stringScanner = new Scanner(System.in);

        String selection = "0";
        while (!selection.equals("4"))
        {
            System.out.print("Create Reservations..." +
                    "\n1. Reserve Item" +
                    "\n2. Reserve Room" +
                    "\n3. View Reservations" +
                    "\n4. Manage Reservations" +
                    "\n> ");
            selection = stringScanner.next();
            selection = InputValidation.validateSelection(selection, 4);

            String userIdReserving;
            String upcToReserve;
            switch (selection)
            {
                case "1"://reserve item
                    System.out.print("Enter the User's ID:\n> ");
                    userIdReserving = stringScanner.next();
                    userIdReserving = InputValidation.validateAccountId(userIdReserving);

                    System.out.print("Enter the Item's UPC:\n> ");
                    upcToReserve = stringScanner.next();
                    upcToReserve = InputValidation.validateNumericId(upcToReserve, 7);

                    Reservation itemReservation = ReservationCollection.createReservation(upcToReserve, userIdReserving, "ITEM");

                    if (itemReservation == null)
                    {
                        System.out.println("No items with that UPC are available.");
                    } else
                    {
                        System.out.println("Created a new item reservation...\n" + itemReservation);
                    }
                    break;

                case "2"://reserve room
                    System.out.print("Enter the User's ID:\n> ");
                    userIdReserving = stringScanner.next();
                    userIdReserving = InputValidation.validateAccountId(userIdReserving);

                    Reservation roomReservation = ReservationCollection.createReservation("ROOM#", userIdReserving, "ROOM");

                    if (roomReservation == null)
                    {
                        System.out.println("Unable to create room reservation.");
                    } else
                    {
                        System.out.println("Created a new room reservation...\n" + roomReservation);
                    }
                    break;

                case "3"://view reservations
                    viewReservationsStaff();
                    break;

                case "4":
                    break;//back to manage reservations menu
            }
        }
    }

    /**
     * Displays the delete reservations menu to a staff member.
     * Allows the staff to remove data from the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void deleteReservations() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);

        String selection = "0";
        while (!selection.equals("4"))
        {
            System.out.print("Delete Reservations..." +
                    "\n1. Enter Reservation ID for Deletion" +
                    "\n2. Delete All Expired" +
                    "\n3. View Reservations" +
                    "\n4. Manage Reservations" +
                    "\n> ");
            selection = scanner.next();
            selection = InputValidation.validateSelection(selection, 4);

            switch (selection)
            {
                case "1"://enter reservation id for deletion
                    System.out.print("Reservation ID:\n> ");
                    String reservationIdEntered = scanner.next();
                    reservationIdEntered = InputValidation.validateNumericId(reservationIdEntered, 7);

                    Boolean isDeleted = ReservationCollection.deleteReservation(reservationIdEntered);
                    if (isDeleted)
                    {
                        System.out.println("Reservation with ID " +
                                reservationIdEntered +
                                " deleted.");
                    } else
                    {
                        System.out.println("Reservation with ID " +
                                reservationIdEntered + " not found." +
                                "\nNo action taken.");
                    }
                    break;

                case "2"://delete all expired
                    System.out.print("Delete all expired reservations?" +
                            "\n1. Yes" +
                            "\n2. No" +
                            "\n> ");
                    String deleteExpiredSelection = scanner.next();
                    deleteExpiredSelection = InputValidation.validateSelection(deleteExpiredSelection, 2);

                    switch (deleteExpiredSelection)
                    {
                        case "1"://yes, delete all expired
                            Boolean deletedExpired = ReservationCollection.deleteAllExpiredReservations();
                            if (deletedExpired)
                            {
                                System.out.println("Deleted all expired reservations.");
                            } else
                            {
                                System.out.println("No expired reservations to delete.");
                            }
                            break;
                        case "2"://no, don't delete all expired
                            System.out.println("No reservations deleted.");
                            break;
                    }
                    break;

                case "3"://view reservations
                    viewReservationsStaff();
                    break;

                case "4"://back to manage reservations menu
                    break;
            }
        }
    }

    /**
     * Displays the sort reservations menu.
     * Allows the staff to view the data in the database of reservations.
     *
     * @throws SQLException Executes SQL database query.
     */
    private void viewReservationsStaff() throws SQLException
    {
        Scanner stringScanner = new Scanner(System.in);

        String selection = "0";
        while (!selection.equals("5"))
        {
            System.out.print("Choose Viewing Method..." +
                    "\n1. View All Reservations" +
                    "\n2. View Reservation By Reservation ID" +
                    "\n3. View Reservation(s) By User ID" +
                    "\n4. View Reservation(s) By UPC" +
                    "\n5. Previous Menu" +
                    "\n> ");
            selection = stringScanner.next();
            selection = InputValidation.validateSelection(selection, 5);

            switch (selection)
            {
                case "1"://view all reservations
                    if (ReservationCollection.isEmpty())
                    {
                        System.out.println("No reservations found.");
                    } else
                    {
                        System.out.print("All Reservations..." + "\n" +
                                ReservationCollection.viewReservations("", "", "", selection));
                    }
                    break;

                case "2"://view reservations by reservation id
                    System.out.print("Enter the Reservation ID:\n> ");
                    String reservationIdToView = stringScanner.next();
                    reservationIdToView = InputValidation.validateNumericId(reservationIdToView, 7);

                    if (!ReservationCollection.searchByReservationId(reservationIdToView))
                    {
                        System.out.println("No reservations found with that reservation ID.");
                    } else
                    {
                        System.out.print("Reservation with reservation ID: " + reservationIdToView + "...\n" +
                                ReservationCollection.viewReservations(reservationIdToView, "", "", selection));
                    }
                    break;

                case "3"://view reservations by user id
                    System.out.print("Enter the User ID:\n> ");
                    String userIdToView = stringScanner.next();
                    userIdToView = InputValidation.validateAccountId(userIdToView);

                    if (!ReservationCollection.searchByUserId(userIdToView))
                    {
                        System.out.println("No reservations found with that user ID.");
                    } else
                    {
                        System.out.print("Reservations with user ID: " + userIdToView + "...\n" +
                                ReservationCollection.viewReservations("", "", userIdToView, selection));
                    }
                    break;

                case "4"://view reservations by upc
                    System.out.print("Enter the UPC:\n> ");
                    String upcToView = stringScanner.next();
                    upcToView = InputValidation.validateRentable(upcToView, 7);

                    if (!ReservationCollection.searchByUpc(upcToView))
                    {
                        System.out.println("No reservations found with that UPC.");
                    } else
                    {
                        System.out.print("Reservations with UPC: " + upcToView + "...\n" +
                                ReservationCollection.viewReservations("", upcToView, "", selection));
                    }
                    break;

                case "5":
                    break;// back to previous menu
            }
        }
    }
}
