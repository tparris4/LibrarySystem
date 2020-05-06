package LibraryManagementSystem.ReservationManagement;

import java.sql.SQLException;

/**
 * TestRMI method.
 */
public class TestRMI
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        //instantiate reservations management interface (StaffRMI/UserRMI)
        new StaffRMI();
        new UserRMI("U000000");
    }
}
