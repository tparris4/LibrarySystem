package LibraryManagementSystem.ReservationManagement;

import java.util.Scanner;

/**
 * Methods used for validating user/staff input from respective ReservationManagement.
 */
public class InputValidation
{
    /**
     * Takes an id number, validates the id number, and returns a valid id number.
     *
     * @param idNumber Id number to be validated.
     * @param idLength Length of id number to be validated.
     * @return Validated id number.
     */
    public static String validateNumericId(String idNumber, Integer idLength)
    {
        Scanner scanner = new Scanner(System.in);
        String stringValidatedId = idNumber;

        int validIdCharacters;
        boolean isValidId = false;

        while (!isValidId)
        {
            validIdCharacters = 0;
            if (stringValidatedId.length() == idLength)
            {
                for (int i = 0; i < idLength; i++)
                {
                    if (stringValidatedId.charAt(i) == '0' || stringValidatedId.charAt(i) == '1' ||
                            stringValidatedId.charAt(i) == '2' || stringValidatedId.charAt(i) == '3' ||
                            stringValidatedId.charAt(i) == '4' || stringValidatedId.charAt(i) == '5' ||
                            stringValidatedId.charAt(i) == '6' || stringValidatedId.charAt(i) == '7' ||
                            stringValidatedId.charAt(i) == '8' || stringValidatedId.charAt(i) == '9')
                    {
                        validIdCharacters++;
                    }
                }
            }

            if (validIdCharacters != idLength)
            {
                System.out.print("Enter a " + idLength + "-digit reservation ID\n> ");
                stringValidatedId = scanner.next();
            }

            isValidId = validIdCharacters == idLength;
        }

        return stringValidatedId;
    }

    /**
     * Takes an id number, validates the id number, and returns a valid id number.
     *
     * @param idNumber Id number to be validated.
     * @param idLength Length of id number to be validated.
     * @return Validated id number.
     */
    public static String validateRentable(String idNumber, Integer idLength)
    {
        Scanner scanner = new Scanner(System.in);
        String stringValidatedId = idNumber;

        int validIdCharacters;
        boolean isValidId = false;

        while (!isValidId)
        {
            validIdCharacters = 0;
            if (stringValidatedId.length() == idLength)
            {
                for (int i = 0; i < idLength; i++)
                {
                    if (stringValidatedId.charAt(i) == '0' || stringValidatedId.charAt(i) == '1' ||
                            stringValidatedId.charAt(i) == '2' || stringValidatedId.charAt(i) == '3' ||
                            stringValidatedId.charAt(i) == '4' || stringValidatedId.charAt(i) == '5' ||
                            stringValidatedId.charAt(i) == '6' || stringValidatedId.charAt(i) == '7' ||
                            stringValidatedId.charAt(i) == '8' || stringValidatedId.charAt(i) == '9' ||
                            (stringValidatedId.charAt(i) == 'R' && i == 0) ||
                            (stringValidatedId.charAt(i) == 'O' && (i == 1 || i == 2) ||
                                    (stringValidatedId.charAt(i) == 'M' && i == 3)))
                    {
                        validIdCharacters++;
                    }
                }
            }

            if (validIdCharacters != idLength)
            {
                System.out.print("Enter a " + idLength + "-digit ID\n> ");
                stringValidatedId = scanner.next();
            }

            isValidId = validIdCharacters == idLength;
        }

        return stringValidatedId;
    }

    /**
     * Takes a String user/staff id number, validates the id number, and returns a valid id number.
     *
     * @param idString Id number to be validated.
     * @return Validated id number in the format "S######" or "U######"
     */
    public static String validateAccountId(String idString)
    {
        Scanner stringScanner = new Scanner(System.in);
        String invalidId = "######";
        if (idString.length() == 7)
        {
            invalidId = idString.substring(1, 7);
        }

        String firstChar = "" + idString.charAt(0);
        if (!firstChar.equals("u") && !firstChar.equals("U") && !firstChar.equals("s") && !firstChar.equals("S"))
        {
            do
            {
                System.out.print("Invalid Entry." +
                        "\nEnter the letter 'S' for staff or 'U' for user\n> ");
                firstChar = stringScanner.next();
            }
            while (!firstChar.equals("u") && !firstChar.equals("U") && !firstChar.equals("s") && !firstChar.equals("S"));
        }
        firstChar = firstChar.toUpperCase();

        String validId = validateRentable(invalidId, 6);
        System.out.println("Valid user ID entered.");

        return "" + firstChar + validId;
    }

    /**
     * Takes a user selected number and the bounds that it must be within,
     * then validates the selection based on the provided boundary.
     *
     * @param userEntered     a numbered selection that the user entered.
     * @param totalSelections the total number of selections available to the user.
     * @return Valid selection within the specified bounds.
     */
    public static String validateSelection(String userEntered, int totalSelections)
    {
        Scanner scanner = new Scanner(System.in);

        String validatedUserEntered = validateRentable(userEntered, 1);
        int validatedUserEnteredToInteger = Integer.parseInt(validatedUserEntered);

        while (!(validatedUserEnteredToInteger > 0 && validatedUserEnteredToInteger < totalSelections + 1))
        {
            if (!(validatedUserEnteredToInteger > 0 && validatedUserEnteredToInteger < totalSelections + 1))
            {
                System.out.print("(1-" + totalSelections + ")\n>");
                validatedUserEntered = scanner.next();
                validatedUserEntered = validateRentable(validatedUserEntered, 1);
                validatedUserEnteredToInteger = Integer.parseInt(validatedUserEntered);
            }
        }
        return validatedUserEntered;
    }
}

