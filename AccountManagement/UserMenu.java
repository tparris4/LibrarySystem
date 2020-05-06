package LibraryManagementSystem.AccountManagement;
import LibraryManagementSystem.RentableManagement.RentableInventory;
import LibraryManagementSystem.ReservationManagement.InputValidation;
import LibraryManagementSystem.ReservationManagement.UserRMI;

import java.util.Scanner;
import java.sql.*;
public class UserMenu {
    public static String userID ="";
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/db_library?useSSL=false";
    static final String MYSQL_USERNAME ="root";
    static final String MYSQL_PASSWORD ="g2t2";
    private static boolean isLoggedIn = false;


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        homeMenu(sc);
        
    }
    
    private static void homeMenu(Scanner sc) throws ClassNotFoundException, SQLException {
        String loginMenu = "Welcome to the Library Management System \n"
                + "Please choose an option: \n"
                + "1) Login\n"
                + "2) Create an Account\n"
                + "3) View All Rentables\n";
        
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
                    createUser();
                    isLoggedIn=(false);
                    break;
            case 3: System.out.println("Viewing all rentables");
                    RentableInventory inventory = new RentableInventory();
                    inventory.viewRentables();
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

    private static void createUser() throws SQLException, ClassNotFoundException {
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
         System.out.println("Enter ID (Must Start with U): ");
        Scanner sc = new Scanner(System.in);
        UserID = sc.nextLine();
        while(UserID.charAt(0)!='U')
        {
            System.out.println("Enter ID (Must Start with U): ");
            UserID = sc.nextLine();
        }
        String sqlID = "SELECT * FROM useraccount WHERE userid = '"+UserID+"'";
        rstSet = myState.executeQuery(sqlID); 
        while(rstSet.next()) 
        {
            System.out.println("UserID is already taken");
            System.out.println("Enter UserID: ");
            UserID = sc.nextLine();
            sqlID = "SELECT * FROM useraccount WHERE userid = '"+UserID+"'";
            rstSet = myState.executeQuery(sqlID); 
        }
        rstSet.close();
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
        
        System.out.println("Account Created: Returning to Login");
        ac.add(temp);
        
    }

    private static void login() throws SQLException, ClassNotFoundException {
        //database access check if username and password match
        //if so returnID save in userID, set login = true
        //else message user of error, loop back to login
        
             Connection myConn = DriverManager.getConnection(DATABASE_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
         ResultSet rs = null;
         PreparedStatement prepStatement = null;
         
         String userName = new String();
         String userPass = new String();
         isLoggedIn = false;
         
         while (!isLoggedIn)
            {
                //Prompt user for input
                System.out.println("Enter username: ");
                Scanner sc = new Scanner(System.in);
                userName = sc.nextLine();
                System.out.println("Enter Password: ");
                userPass = sc.nextLine();
                
                
                //Use for prepared statement
                String sql = "select userID from useraccount where username = ? and password = ?";
                
                //Fill in preparedstatement with value of username and user password
                prepStatement = myConn.prepareStatement(sql);
                prepStatement.setString(1, userName);
                prepStatement.setString(2, userPass);
                rs = prepStatement.executeQuery();
                
                //If RS has been intialized, then we are logged in
                if(rs.next())
                {
                        System.out.println("You are logged in!");
                        isLoggedIn = true;
                        userID = rs.getString("userID");
                        userMenu(isLoggedIn);
                        break;
                }
                //RS has no values in it so we didn't find a matching username/password
                else
                {
                       System.out.println("User Account not found");
                       break;
                }
            }
    }

    private static void userMenu(boolean b) throws ClassNotFoundException, SQLException {
        while(isLoggedIn)
        {
            System.out.println("Main Menu\n"
                    + "Please choose an option:\n"
                    + "1) Account Management\n"
                    + "2) Reservation Management\n"
                    + "3) Rentables Management\n"
                    + "4) Logout\n");
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
                case 4: logout();
                        break;
            }
            
        }
    }

    private static void logout() {
        userID="";
        isLoggedIn = false;
        System.out.println("You have successfully logged out!");        
    }

    private static void rentableManagement() throws SQLException
    {
        System.out.println("\nWould you like to search rentables?\n"
                + "1) Yes\n"
                + "2) No");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        if(choice == 1) {
            searchRentableUI();
        } else {
            return;
        }
        
    }
    
    public static void searchRentableUI() throws SQLException
    {
        Scanner scanner = new Scanner(System.in);
        RentableInventory inventory = new RentableInventory();
        
        System.out.println("\nPlease select an attribute to search by:\n"
                + "1) SKU\n"
                + "2) Title\n"
                + "3) ISBN\n"
                + "4) Condition\n"
                + "5) Genre\n"
                + "6) Type\n");
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

    private static void reservationManagement() throws ClassNotFoundException, SQLException {
        new UserRMI(userID);
        
    }

    private static void accountManagement() throws SQLException {
        String userAcctMngChoice = "0";
        while(!userAcctMngChoice.equals("4"))
        {
            System.out.println("Account Management Menu\n"
                    + "1) View Account\n"
                    + "2) Edit Account\n"
                    + "3) Change Password\n"
                    + "4) User Main Menu\n");
            Scanner sc = new Scanner(System.in);
            userAcctMngChoice = sc.next();
            userAcctMngChoice = InputValidation.validateSelection(userAcctMngChoice, 4);
            
            switch(userAcctMngChoice)
            {
            case "1":
                viewAccount();
                break;
            case "2":
                editAccount();
                break;
            case "3":
                changePassword();
                break;
            case "4"://staff main menu
                System.out.println("Exiting account management");
                break; //returns a '5' and ends the UserRMI loop, exiting to user main menu
            }
        }
    }

    private static void changePassword() throws SQLException {
        AccountCollection ac = new AccountCollection();
        Scanner sc = new Scanner(System.in);
        String newPassword;
        
        System.out.println("Enter New Password: ");
        newPassword = sc.next();
        Staff tempUser = ac.search(userID);
        
        if(ac.changePassword(newPassword, tempUser))
        {
            System.out.println("Your password has been changed");
        }
        
        else 
        {
            System.out.println("Your password has not been changed");
        }
        
    }

    private static void editAccount() {
        String UserID = new String(),
                 FirstName = new String(), 
                 LastName = new String(),
                 Email = new String(),
                 StreetNum = new String(), 
                 StreetName = new String(),
                City = new String(),
                State = new String(),
                Zip = new String();
         AccountCollection ac = new AccountCollection();
         
        System.out.println("Enter ID (Must Start with U): ");
        Scanner sc = new Scanner(System.in);
        UserID = sc.nextLine();
        while(!userID.equals(UserID))
        {
            System.out.println("Incorrect ID, Try Again: ");
            System.out.println(userID);
            UserID = sc.nextLine();
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
        
        User currentUser = (User) ac.search(userID);
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
        
        System.out.println("User has been updated");        
    }

    private static void viewAccount() {
        AccountCollection ac = new AccountCollection();
        System.out.println(ac.search(userID));
        
    }
}





