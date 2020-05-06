package LibraryManagementSystem.AccountManagement;
import LibraryManagementSystem.RentableManagement.Rentable;
import LibraryManagementSystem.RentableManagement.RentableInventory;
import LibraryManagementSystem.RentableManagement.RentalInventory;
import LibraryManagementSystem.ReservationManagement.StaffRMI;

import java.util.Scanner;
import java.sql.*;

public class StaffMenu {

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?useSSL=false";
    static final String MYSQL_USERNAME ="root";
    static final String MYSQL_PASSWORD ="g2t2";
    public static String staffID ="";
    private static boolean isLoggedIn = false;
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        homeMenu(sc);

    }

    private static void homeMenu(Scanner sc) throws SQLException, ClassNotFoundException {
        String loginMenu = "Welcome to the Library Management System for Staff \n"
                + "Please choose an option: \n"
                + "1) Login\n"
                + "2) Create a Staff Account\n";
        
        boolean run = true;
        System.out.println(loginMenu);
        int userChoice = sc.nextInt();
        
        while(run)
        {
            switch(userChoice)
            {
            case 1: login();
                    break;//login option
            case 2: // Create user account
                    createStaff();
                    isLoggedIn=(false);
                    break;
            default:
                    System.out.println("Invalid option, returning to menu..");
                    break;
            }    
            
            if(run)
            {
                System.out.println(loginMenu);
                userChoice = sc.nextInt();
            }
        }
        
    }

    private static void createStaff() throws SQLException, ClassNotFoundException {
        Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
        Statement myState = myConn.createStatement();
         ResultSet rstSet = null;
         
        String StaffID = new String(),
                FirstName = new String(),
                LastName = new String(),
                Email = new String(),
                Username = new String(),
                Password = new String(),
                StreetNum = new String(),
                StreetName = new String(),
                City = new String(),
                State = new String(),
                Zip = new String();

        System.out.println("Enter ID (Must Start with S): ");
        Scanner sc = new Scanner(System.in);
        StaffID = sc.nextLine();
        while(StaffID.charAt(0)!='S')
        {
            System.out.println("Enter ID (Must Start with S): ");
            StaffID = sc.nextLine();
        }
        String sqlID = "SELECT * FROM staffaccount WHERE staffid = '"+StaffID+"'";
        rstSet = myState.executeQuery(sqlID);
        while(rstSet.next())
        {
            System.out.println("Staffid is already taken");
            System.out.println("Enter staffid: ");
            StaffID = sc.nextLine();
            sqlID = "SELECT * FROM staffaccount WHERE staffid = '"+StaffID+"'";
            rstSet = myState.executeQuery(sqlID);
        }
        rstSet.close();
        System.out.println("Enter First Name: ");
        FirstName= sc.nextLine();
        System.out.println("Enter LastName: ");
        LastName = sc.nextLine();
        System.out.println("Enter Username: ");
        Username = sc.nextLine();
        String sqlUsername = "SELECT * FROM staffaccount WHERE username = '"+Username+"'";
        rstSet = myState.executeQuery(sqlUsername);
        while(rstSet.next())
        {
            System.out.println("Username is already taken");
            System.out.println("Enter Username: ");
            Username = sc.nextLine();
            sqlUsername = "SELECT * FROM useraccount WHERE username = '"+Username+"'";
            rstSet = myState.executeQuery(sqlUsername);
        }
        rstSet.close();
        System.out.println("Enter Password: ");
        Password = sc.nextLine();
       
        System.out.println("Enter Email: ");
        Email = sc.nextLine();
        System.out.println("Enter Street Number: ");
        StreetNum = sc.nextLine();
        System.out.println("Enter Street Name: ");
        StreetName = sc.nextLine();
        System.out.println("Enter City: ");
        City = sc.nextLine();
        System.out.println("Enter State: ");
        State = sc.nextLine();
        System.out.println("Enter Zip: ");
        Zip = sc.nextLine();
        
        Staff temp = new Staff (StaffID,FirstName,LastName,Email,Username,Password, new Address(StreetNum, StreetName,
                City,State,Zip));
        AccountCollection ac = new AccountCollection();
        System.out.println("Account Created: Returning to Login");
        ac.add(temp);
        
    }

    private static void login() throws SQLException, ClassNotFoundException {
        Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
         ResultSet rs = null;
         PreparedStatement prepStatement = null;
        
         String staffName = new String();
         String staffPass = new String();
         isLoggedIn = false;
        
         while (!isLoggedIn)
            {
                //Prompt user for input
                System.out.println("Enter username: ");
                Scanner sc = new Scanner(System.in);
                staffName = sc.nextLine();
                System.out.println("Enter Password: ");
                staffPass = sc.nextLine();
               
               
                //Use for prepared statement
                String sql = "select staffID from staffaccount where username = ? and password = ?";
               
                //Fill in preparedstatement with value of username and user password
                prepStatement = myConn.prepareStatement(sql);
                prepStatement.setString(1, staffName);
                prepStatement.setString(2, staffPass);
                rs = prepStatement.executeQuery();
               
                //If RS has been intialized, then we are logged in
                if(rs.next())
                {
                        System.out.println("You are logged in!");
                        isLoggedIn = true;
                        staffID = rs.getString("staffID");
                        System.out.println(staffID);
                        staffMenu(isLoggedIn);
                        break;
                }
                //RS has no values in it so we didn't find a matching username/password
                else
                {
                       System.out.println("Staff Account not found");
                       break;
                }
            }
        
    }

    private static void staffMenu(boolean b) throws ClassNotFoundException, SQLException {
        while(isLoggedIn)
        {
            System.out.println("Main Menu\n"
                    + "Please choose an option:\n"
                    + "1) Account Management\n"
                    + "2) Reservation Management\n"
                    + "3) Rentables Management\n"
                    + "4) Rental Management\n"
                    + "5) Logout\n");
            Scanner sc = new Scanner(System.in);
            int amChoice = sc.nextInt();
            
            switch(amChoice)
            {
                case 1: accountManagement();
                        break;
                case 2: reservationManagement();
                        break;
                case 3: rentableManagement();
                        break;
                case 4: rentalManagement();
                        break;
                case 5: logout();
                        break;
            }
            
        }
        
    }

    private static void logout() {
        staffID="";
        isLoggedIn = false;
        System.out.println("You have successfully logged out!");        
    }

    private static void viewAccount() {
        AccountCollection ac = new AccountCollection();
        System.out.println(ac.search(staffID));
        
    }

    private static void editMyAccount() {
         String StaffID = new String(),
                 FirstName = new String(),
                 LastName = new String(),
                 Email = new String(),
                 Username = new String(),
                 Password = new String(),
                 StreetNum = new String(),
                 StreetName = new String(),
                City = new String(),
                State = new String(),
                Zip = new String();
         AccountCollection ac = new AccountCollection();
        
        System.out.println("Enter ID (Must Start with S): ");
        Scanner sc = new Scanner(System.in);
        StaffID = sc.nextLine();
        while(!StaffID.equals(staffID))
        {
            System.out.println("Incorrect ID, Try Again: ");
            StaffID = sc.nextLine();
        }
        System.out.println("Enter First Name: ");
        FirstName= sc.nextLine();
        System.out.println("Enter LastName: ");
        LastName = sc.nextLine();
        System.out.println("Enter Email: ");
        Email = sc.nextLine();
        System.out.println("Enter Street Number: ");
        StreetNum = sc.nextLine();
        System.out.println("Enter Street Name: ");
        StreetName = sc.nextLine();
        System.out.println("Enter City: ");
        City = sc.nextLine();
        System.out.println("Enter State: ");
        State = sc.nextLine();
        System.out.println("Enter Zip: ");
        Zip = sc.nextLine();
       
        Staff currentUser = (Staff) ac.search(staffID);
        Address newAddress = new Address(StreetNum,StreetName,City,State,Zip);
        currentUser.setfName(FirstName);
        currentUser.setlName(LastName);
        currentUser.setEmail(Email);
        currentUser.getAddress().setStreetNum(StreetNum);
        currentUser.getAddress().setStreetName(StreetName);
        currentUser.getAddress().setCity(City);
        currentUser.getAddress().setState(State);
        currentUser.getAddress().setZipCode(Zip);
       
        try {
            ac.update(currentUser);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       
        System.out.println("Staff has been updated");
    }

    private static void changePassword() throws SQLException {
        AccountCollection ac = new AccountCollection();
        Scanner sc = new Scanner(System.in);
        String newPassword;
       
        System.out.println("Enter New Password: ");
        newPassword = sc.next();
        Staff tempStaff = ac.search(staffID);
       
        if(ac.changePassword(newPassword, tempStaff))
        {
            System.out.println("Your password has been changed");
        }
       
        else
        {
            System.out.println("Your password has not been changed");
        }
        
    }

    private static void issueAnID() {
        System.out.println("Enter U for UserID and S for Staff ID");
        Scanner sc = new Scanner(System.in);
        String accountTypeStr = sc.next();
        AccountCollection ac = new AccountCollection();
        Staff myAcount = ac.search(staffID);
        
        while(!accountTypeStr.equals("S")&&!accountTypeStr.equals("U"))
        {
            System.out.println("Enter U for UserID and S for Staff ID");
            accountTypeStr = sc.next();
        }
        
        char accountType = accountTypeStr.charAt(0);
        String issuedID = myAcount.issueID(accountType);
        System.out.println("The ID is: " + issuedID);
        
    }

    private static void createUserAccount() throws SQLException, ClassNotFoundException {
        Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
        Statement myState = myConn.createStatement();
         ResultSet rstSet = null;
         
        String UserID = new String(),
                FirstName = new String(),
                LastName = new String(),
                Email = new String(),
                Username = new String(),
                Password = new String(),
                StreetNum = new String(),
                StreetName = new String(),
                   City = new String(),
                   State = new String(),
                   Zip = new String();

        AccountCollection ac = new AccountCollection();
            Staff staffAccount = ac.search(staffID);
            
            Scanner sc = new Scanner(System.in);
         UserID = staffAccount.issueID('U');
        System.out.println("Enter First Name: ");
        FirstName= sc.nextLine();
        System.out.println("Enter LastName: ");
        LastName = sc.nextLine();
        System.out.println("Enter Username: ");
        Username = sc.nextLine();
        String sqlUsername = "SELECT * FROM useraccount WHERE username = '"+Username+"'";
        rstSet = myState.executeQuery(sqlUsername);
        while(rstSet.next())
        {
            System.out.println("Username is already taken");
            System.out.println("Enter Username: ");
            Username = sc.nextLine();
            sqlUsername = "SELECT * FROM useraccount WHERE username = '"+Username+"'";
            rstSet = myState.executeQuery(sqlUsername);
        }
        rstSet.close();
           System.out.println("Enter Password: ");
           Password = sc.nextLine();
           System.out.println("Enter Email: ");
           Email = sc.nextLine();
           System.out.println("Enter Street Number: ");
           StreetNum = sc.nextLine();
           System.out.println("Enter Street Name: ");
           StreetName = sc.nextLine();
           System.out.println("Enter City: ");
           City = sc.nextLine();
           System.out.println("Enter State: ");
           State = sc.nextLine();
           System.out.println("Enter Zip: ");
           Zip = sc.nextLine();
          
           User temp = new User (UserID,FirstName,LastName,Email,Username,Password, new Address(StreetNum, StreetName,
                   City,State,Zip), 0.0);     
           ac.add(temp);
           System.out.println("User account has been created");
        
    }

    private static void editUserAccount() {
        String UserID = new String(),
                 FirstName = new String(),
                 LastName = new String(),
                 Email = new String(),
                 Username = new String(),
                 Password = new String(),
                 StreetNum = new String(),
                 StreetName = new String(),
                City = new String(),
                State = new String(),
                Zip = new String();
         AccountCollection ac = new AccountCollection();
         
        System.out.println("Enter ID (Must Start with U): ");
        Scanner sc = new Scanner(System.in);
        UserID = sc.nextLine();
        User currentUser = (User) ac.search(UserID);
        while(currentUser == null)
        {
            System.out.println("Enter a valid ID (Must Start with U):");
            UserID = sc.nextLine();
            currentUser = (User) ac.search(UserID);
        }
        System.out.println("Enter First Name: ");
        FirstName= sc.nextLine();
        System.out.println("Enter LastName: ");
        LastName = sc.nextLine();
        System.out.println("Enter Email: ");
        Email = sc.nextLine();
        System.out.println("Enter Street Number: ");
        StreetNum = sc.nextLine();
        System.out.println("Enter Street Name: ");
        StreetName = sc.nextLine();
        System.out.println("Enter City: ");
        City = sc.nextLine();
        System.out.println("Enter State: ");
        State = sc.nextLine();
        System.out.println("Enter Zip: ");
        Zip = sc.nextLine();
        
        
        Address newAddress = new Address(StreetNum,StreetName,City,State,Zip);
        currentUser.setfName(FirstName);
        currentUser.setlName(LastName);
        currentUser.setUsername(Username);
        currentUser.setEmail(Email);
        currentUser.getAddress().setStreetNum(StreetNum);
        currentUser.getAddress().setStreetName(StreetName);
        currentUser.getAddress().setCity(City);
        currentUser.getAddress().setState(State);
        currentUser.getAddress().setZipCode(Zip);
        
        try {
            ac.update(currentUser);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        System.out.println("User has been updated");        
    }

    private static void removeByID() {
        System.out.println("Enter the I.D. to be removed");
        Scanner sc = new Scanner(System.in);
        String idToBeRemoved = sc.next();
        if(idToBeRemoved.equals(staffID)) {
            System.out.println("You cannot delete your account");
            return;
        }
            
            
        AccountCollection ac = new AccountCollection();
        Staff staff = ac.removeByID(idToBeRemoved);
        if(staff != null)
        {
            System.out.println(idToBeRemoved+ " was removed");
        }
        else
        {
            System.out.println("Account wasn't removed");
        }    
        
    }

    private static void searchForAnAccount() {
        System.out.println("Enter ID of account to be searched: ");
        Scanner sc = new Scanner(System.in);
        String searchID = sc.next();
        AccountCollection ac = new AccountCollection();
        System.out.println(ac.search(searchID));
        
    }

    private static void viewAllAccounts() throws SQLException {
        AccountCollection ac = new AccountCollection();
        System.out.println(ac.View());
        
    }

    private static void rentableManagement() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        int method;
        
        //Print UI choices
        System.out.println("\nPlease select an operation: \n"
                + "1) View all rentables\n"
                + "2) Search Rentables\n"
                + "3) Add a new rentable\n"
                + "4) Exit Rentable Management");
        
        method = scanner.nextInt();
        switch (method) {
        case 1:
            viewRentableUI();
            rentableManagement();
            break;
        case 2:
            searchRentableUI();
            rentableManagement();
            break;
        case 3:
            addRentableUI();
            rentableManagement();
            break;
        case 4:
            break;
        }
    }
    
    private static void addRentableUI() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        RentableInventory inventory = new RentableInventory();
        String title = "";
        String isbn = "";
        String condition = "";
        String genre = "";
        String roomNum = "";
        int sku = 100;
        
        System.out.println("Please select the type of rentable that you wish to add.\n1) Book\n2) DVD\n3) E-Book\n"
        		//+ "4) Room"
        		+ "");
        
        int type = scanner.nextInt();
        
        switch (type) {    
            case 1:
                //Asks for input information about Rentable
                System.out.print("Please input the sku: ");
                sku = scanner.nextInt();
                System.out.print("Please input the title: ");
                title = scanner.next();
                System.out.print("Please input the ISBN: ");
                isbn = scanner.next();
                System.out.print("Please input the condition: ");
                condition = scanner.next();
                System.out.print("Please input the genre: ");
                genre = scanner.next();
                
                //Creates Rentable and calls the method to add it to the db
                inventory.addRentable(new Rentable(sku, title, isbn, condition, genre, "Book"));
                break;
            case 2:
                //Asks for input information about Rentable
                System.out.print("Please input the title: ");
                title = scanner.next();
                System.out.print("Please input the condition: ");
                condition = scanner.next();
                System.out.print("Please input the genre: ");
                genre = scanner.next();
                
                //Creates Rentable and calls the method to add it to the db
                inventory.addRentable(new Rentable(100, title, condition, genre));
                break;
            case 3:
                //Asks for input information about Rentable
                System.out.print("Please input the title: ");
                title = scanner.next();
                System.out.print("Please input the isbn: ");
                isbn = scanner.next();
                System.out.print("Please input the condition: ");
                condition = scanner.next();
                System.out.print("Please input the genre: ");
                genre = scanner.next();
                
                //Creates Rentable and calls the method to add it to the db
                inventory.addRentable(new Rentable(100, title, isbn, condition, genre, "EBook"));
                break;
            default:
                System.out.print("Invalid rentable type. Rentable not added.");
                break;
        }
    }

    public static void searchRentableUI() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        RentableInventory inventory = new RentableInventory();
        
        System.out.println("Please select an attribute to search by:\n"
                + "1) SKU\n"
                + "2) Title\n"
                + "3) ISBN\n"
                + "4) Condition\n"
                + "5) Genre\n"
                + "6) Type\n"
                + "");
        int choice = scanner.nextInt();
        String type = "";
        if(choice == 1) {
            type = "sku";
        } else if(choice == 2) {
            type = "title";
        }else if(choice == 3) {
            type = "isbn";
        }else if(choice == 4) {
            type = "condition";
        }else if(choice ==5) {
            type = "genre";
        }else if(choice ==6) {
            type = "type";
        } else {
            System.out.println("Invalid input. Please try again.");
            searchRentableUI();
            return;
        }
        String parameter = "";
        System.out.print("Please input what you would like to search for. \nSearch: ");
        parameter = scanner.next();
        
        if(!type.equals("")) {
            System.out.println("Results:");
            inventory.searchRentables(type, parameter);
        }
    }
    
    public static void searchRentalUI() {
        /*Scanner scanner = new Scanner(System.in);
        RentalInventory inventory = new RentalInventory();
        
        System.out.println("Please select an attribute to search by:\n"
                + "1) SKU\n"
                + "2) User ID\n"
                + "3) Times Renewed");
        int choice = scanner.nextInt();
        String type = "";
        if(choice == 1) {
            type = "sku";
        } else if(choice == 2) {
            type = "title";
        }else if(choice == 3) {
            type = "isbn";
        } else {
            System.out.println("Invalid input. Please try again.");
            searchRentalUI();
            return;
        }
        String parameter = "";
        System.out.print("Please input what you would like to search for. \nSearch: ");
        parameter = scanner.next();
        
        if(!type.equals("")) {
            System.out.println("Results: ");
            inventory.searchRentals(type, parameter);
        }*/
        System.out.println("If you are seeing this message Search Rentals has not been implemented");
    }
    
    public static void viewRentableUI() throws SQLException
    {
        RentableInventory inventory = new RentableInventory();
        System.out.println("All rentables:");
        inventory.viewRentables();
    }
    
    public static void viewRentalUI(){
        System.out.println("All rentables:");
        RentalInventory inventory = new RentalInventory();
        inventory.viewRentals();
    }

    private static void rentalManagement() {
        Scanner scanner = new Scanner(System.in);
        int method = 0;
        
        //Print UI choices
        System.out.println("Please select an operation: \n"
                + "1) View all rentals\n"
                + "2) Search rentals\n"
                + "3) Exit Rental Management");
        method = scanner.nextInt();
        switch (method) {
            case 1:
                viewRentalUI();
                rentalManagement();
                break;
            case 2:
                searchRentalUI();
                rentalManagement();
                break;
            case 3: break;
        }
    }


    private static void reservationManagement() throws ClassNotFoundException, SQLException {
        new StaffRMI();
    }

    private static void accountManagement() throws SQLException, ClassNotFoundException {
        int staffAcctMngChoice = 0;
        System.out.println("Account Management Menu\n"
                + "1) View Account\n"
                + "2) Edit Account\n"
                + "3) Change Password\n"
                + "4) Issue an ID\n"
                + "5) Create a User Account \n"
                + "6) Edit a User Account\n"
                + "7) Remove an Account\n"
                + "8) Search for an Account\n"
                + "9) View All Accounts\n"
                + "10) Pay user Balance\n"
                + "**) Any other option will return you to the Main Menu\n");
        Scanner sc = new Scanner(System.in);
        staffAcctMngChoice = sc.nextInt();
            switch(staffAcctMngChoice)
            {
            case 1: viewAccount();
                    break;
            case 2: editMyAccount();
                    break;
            case 3: changePassword();
                    break;
            case 4: issueAnID();
                    break;
            case 5: createUserAccount();
                    break;
            case 6: editUserAccount();
                    break;
            case 7: removeByID();
                    break;
            case 8: searchForAnAccount();
                    break;
            case 9: viewAllAccounts();
                    break;
            case 10:payUserBalance();
                    break;
                    
            default://staff main menu
                    System.out.println("Exiting account management");
                    break;
            }
        }

    private static void payUserBalance() throws SQLException {
        Scanner sc = new Scanner(System.in);
        AccountCollection ac = new AccountCollection();
        System.out.println("Enter user ID");
        String userID = sc.next();
        User u = (User)ac.search(userID);
        if(u == null)
        {
            System.out.println("User not found");
            return;
        }
        double balance = u.getBalance();
        if(balance>0)
        {
            System.out.println("User balance = " + balance);
            System.out.print("Enter amount to pay: $");
            double toPay = sc.nextDouble();
            u.decreaseBalance(toPay);
            ac.updateBalance(u);
            System.out.println("User "+u.getId()+" has paid $"+toPay);
            System.out.println("Updated Balance for "+u.getId()+" = "+u.getBalance());
        }
        else
        {
            System.out.println("User does not have a balance");
        }
        
    }
        
}
    




