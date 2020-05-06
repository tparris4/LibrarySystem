package LibraryManagementSystem.ReservationManagement;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * A reservation entity.
 */
public class Reservation
{
    /**
     * Unique id generated for each reservation.
     */
    private String reservationId;
    /**
     * upc associated with reservation.
     */
    private String upc;
    /**
     * User id associated with reservation.
     */
    private String userId;
    /**
     * Time that the reservation starts.
     */
    private Timestamp reservationDate;
    /**
     * Room number, if room reservation.
     */
    private String roomNumber;
    /**
     * Time that the reservation will expire.
     */
    private Timestamp reservationExpireDate;
    /**
     * Active status of a reservation.
     */
    private Boolean isActive;
    /**
     * The type of reservation; either "ITEM" or "ROOM".
     */
    private String reservationType;

    /**
     * Reservation overloaded constructor.
     * Instantiates a reservation with a unique ID and attributes.
     *
     * @param newUpc             upc id associated with reservation.
     * @param newUserId          User id associated with reservation.
     * @param newReservationType Type of reservation: item or room.
     */
    Reservation(String newUpc, String newUserId, String newReservationType)
    {
        //generate random reservation id
        Random random = new Random();
        int intReservationId = random.nextInt(8999999) + 1000000;
        reservationId = Integer.toString(intReservationId);

        upc = newUpc; //from Rentable class (Team 3)
        userId = newUserId; //from UserAccount class (Team 1)
        roomNumber = newUpc;
        reservationDate = Timestamp.from(Instant.now());//date of reservation is current time
        isActive = true;
        reservationType = newReservationType;

        switch (newReservationType)
        {
            case "ITEM":
                reservationExpireDate = Timestamp.from(Instant.now().plus(336, ChronoUnit.HOURS));
                roomNumber = "NO-ROOM";
                break;

            case "ROOM":
                reservationExpireDate = Timestamp.from(Instant.now().plus(12, ChronoUnit.HOURS));
                upc = "NO-ITEM";
                break;
        }
    }

    /**
     * Lists all the attributes of a reservation in string format.
     *
     * @return String version of a reservation.
     */
    @Override
    public String toString()
    {
        return '{' +
                "reservationId=" + reservationId +
                ", upc=" + upc +
                ", userId=" + userId +
                ", roomNumber=" + roomNumber +
                ", reservationDate=" + reservationDate +
                ", reservationExpireDate=" + reservationExpireDate +
                ", isActive=" + isActive +
                ", reservationType=" + reservationType +
                '}';
    }

    /**
     * Gets the reservation ID.
     *
     * @return Reservation ID.
     */
    public String getReservationId()
    {
        return reservationId;
    }

    /**
     * Gets the rentable ID.
     *
     * @return Rentable ID.
     */
    public String getUpc()
    {
        return upc;
    }

    /**
     * Gets the user ID.
     *
     * @return User ID.
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * Gets the room number.
     *
     * @return Room number
     */
    public String getRoomNumber()
    {
        return roomNumber;
    }

    /**
     * Gets the reservation date.
     *
     * @return Reservation date.
     */
    public Timestamp getReservationDate()
    {
        return reservationDate;
    }

    /**
     * Gets the reservation expiration date.
     *
     * @return Reservation expiration date.
     */
    public Timestamp getReservationExpireDate()
    {
        return reservationExpireDate;
    }

    /**
     * Gets the active status.
     *
     * @return Active status.
     */
    public Boolean getActive()
    {
        return isActive;
    }

    /**
     * Gets the reservation type.
     *
     * @return Reservation type.
     */
    public String getReservationType()
    {
        return reservationType;
    }
}
