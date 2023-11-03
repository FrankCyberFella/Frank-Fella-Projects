package com.frankmo.app.services;

import com.frankmo.app.services.frankmoService;
import com.frankmo.app.model.frankmoAccount;
import com.frankmo.app.model.frankmoTransfer;
import com.frankmo.app.security.model.AuthenticatedUser;
import com.frankmo.app.security.model.User;
import com.frankmo.app.security.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private static final Scanner scanner = new Scanner(System.in);

    private static final frankmoService thefrankmoService = new frankmoService();

    public  static enum MAIN_MENU_CHOICES {Exit
                                          ,View_Your_Current_Balance
                                          ,Send_TE_Bucks
                                          ,View_Your_Past_Transfers
                                          ,View_Your_Pending_Requests
                                          ,Request_TE_Bucks
                                         }
    /**
     * Have user select a userid from a given list of userids
     *
     * @param accountList - List of accounts whose users are to be displayed
     * @return - User account selected
     */
    public frankmoAccount selectFromUserList(List<frankmoAccount> accountList) {
        frankmoAccount selectedUserAccount = null;

        System.out.println("\nSelect user id to receive transfer: ");
        for(int i = 0; i < accountList.size(); i++) {
            User[] usersForAccount =thefrankmoService.getUserForAccount(accountList.get(i));
            System.out.printf("%d - user id: %d (%s)\n", i+1
                                                       , accountList.get(i).getUser_id()
                                                       , usersForAccount[0].getUsername());
        }
        System.out.println("0 - Exit");
        boolean validChoice = false;
        while(!validChoice) {
            Integer userChoice = promptForInt("\nChoice? ");
            if(userChoice == 0) {  // if user chose exit option
                break;             // exit loop
            }
            if (userChoice > 0 && userChoice <= accountList.size()) {
               validChoice = true;
               selectedUserAccount = accountList.get(userChoice-1);
            }
            else {
                printMenuSelectionError();
            }
        }
        return selectedUserAccount;
    }

    /**
     * Display list of transfers for user
     *      and ask if they want details on one
     *
     * @param userTransfers - List of transfers to be displayed
     * @return - Transfer selected for details or null if none selected
     */
    public frankmoTransfer selectFromTransferList(List<frankmoTransfer> userTransfers) {
        frankmoTransfer selectedTransfer = null;

        System.out.println("\nSelect item number for details on transfer (or 0 to exit): \n");
        System.out.println(" Transfer");
        System.out.println("   Id    \tFrom\t To\t\t Amount");
        System.out.println(" --------\t-------\t------\t-----------");
        for(int i = 0; i < userTransfers.size(); i++) {
            User[] toUser   = thefrankmoService.getUserForAccount(userTransfers.get(i).getTofrankmoAccount());
            User[] fromUser = thefrankmoService.getUserForAccount(userTransfers.get(i).getFromfrankmoAccount());
            System.out.printf("%d. %d \t%s\t%s\t$%-10.2f\n",i+1
                                                ,userTransfers.get(i).getTransferId()
                                                ,fromUser[0].getUsername()
                                                ,toUser[0].getUsername()
                                                ,userTransfers.get(i).getAmount() );
        }
        System.out.println("\n0 to Exit");
        boolean validChoice = false;
        while(!validChoice) {
            Integer userChoice = promptForInt("\nChoice? ");
            if(userChoice == 0) {  // if user chose exit option
                break;             // exit loop
            }
            if (userChoice > 0 && userChoice <= userTransfers.size()) {
                validChoice = true;
                selectedTransfer = userTransfers.get(userChoice-1);
            }
            else {
                printMenuSelectionError();
            }
        }
        return  selectedTransfer;
    }

    public void printTransferDetails(frankmoTransfer aTransfer) {
        System.out.printf("-".repeat(50) + "\nTransfers details\n"+"-".repeat(50)+"\n");

        User[] toUser   = thefrankmoService.getUserForAccount(aTransfer.getTofrankmoAccount());
        User[] fromUser = thefrankmoService.getUserForAccount(aTransfer.getFromfrankmoAccount());

        System.out.println("\t    Id:\t" + aTransfer.getTransferId());
        System.out.printf("\t  From:\t%s (%d)\n", fromUser[0].getUsername(), aTransfer.getFromfrankmoAccount().getUser_id());
        System.out.printf("\t    To:\t%s (%d)\n", toUser[0].getUsername(), aTransfer.getTofrankmoAccount().getUser_id());
        System.out.println("\t  Type:\t" + aTransfer.getTransferType());
        System.out.println("\tStatus:\t" + aTransfer.getTransferStatus());
        System.out.println("\tAmount:\t" + aTransfer.getAmount());
        System.out.println("-".repeat(50) + "\n");

    }

    /**
     * Display account information for a given account and current user
     *
     * @param anAccount - The Account whose information should be displayed
     * @param theUser   - currently logged-in user
     */
    public void displayAnAccount(frankmoAccount anAccount, AuthenticatedUser theUser) {
        System.out.printf("-".repeat(50) + "\nAccount information for %s (User Id: %d)"
                         , theUser.getUser().getUsername()
                         , theUser.getUser().getId());
        System.out.printf("\n\tAccount #: %-10d", anAccount.getAccount_id());
        System.out.printf("\n\t  Balance: %-12.2f", anAccount.getBalance().doubleValue());
        System.out.printf("\n" +"-".repeat(50) + "\n");
    }
    /**
     * Issue custom prompt for confirmation
     */
    public boolean promptForConfirmation(String promptMessage) {
        boolean isConfirmed = false;
        System.out.println(promptMessage);
        String response = promptForString("Please respond with 'Y' for yes and 'N' for no (default='N')\n");
        if(response.toUpperCase().startsWith("Y")) {
            isConfirmed = true;
        }
        return isConfirmed;
    }
    /**
     * Issue custom prompt for a user for a login/register menu choice
     *
     * @param prompt - The prompt to be displayed to the user
     * @return
     */
    public int promptForLoginMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }
    /**
     * Issue custom prompt for a user for a main processing menu choice
     *
     * @param prompt - The prompt to be displayed to the user
     * @return - the MAIN_MENU_CHOICES option chosen by the user
     */
    public MAIN_MENU_CHOICES promptForMainMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        if(menuSelection < 0 || menuSelection >= MAIN_MENU_CHOICES.values().length) {
            printMenuSelectionError();
            pause();
            return null;
        }
        // return numeric choice convert to enum equivalent
        return MAIN_MENU_CHOICES.values()[menuSelection];
    }
    /**
     * Display a given message
     */
    public void printMessage(String aMessage) {
        System.out.println(aMessage);
    }
    /**
     * Display summary of transfers for a user
     */
    public void printTransfersSummary(List<frankmoTransfer> userTransfers) {
        for(frankmoTransfer aTransfer : userTransfers) {

        }
    }
    /**
     * Display custom operation cancelled message
     */
    public void printProcessCancelledMessage(String cancellationMessage) {
        System.out.println("!!! Process Cancelled !!!");
        System.out.println(cancellationMessage);
        System.out.println("!!! Process Cancelled !!!\n");
    }
    /**
     * Display application greeting message
     */
    public void printGreeting() {
        System.out.println("-".repeat(50));
        System.out.println("Welcome to frankmo!");
        System.out.println("-".repeat(50));
    }
    /**
     * Display login/register menu options
     */
    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }
    /**
     * Display welcome message when user logs in
     *
     * @param currentUser - currently logged in Authenticated user
     */
    public void printLoginWelcome(AuthenticatedUser currentUser) {
        System.out.println("-".repeat(50));
        System.out.println("Hello, "
                          + currentUser.getUser().getUsername()
                          + " (User id: " + currentUser.getUser().getId() + ")");
        System.out.println("\nWhat would you like to do today?\n");
    }

    /**
     * Display goodbye message for non-logged in user
     */
    public void printExitLoginMenu() {
        printAuRevoirMessage(null);
    }
    /**
     * Display main processing menu choices
     */
    public void printMainMenu() {
          MAIN_MENU_CHOICES menuChoices[] = MAIN_MENU_CHOICES.values();

          for(int i=1; i < menuChoices.length; i++){
              String aChoice = menuChoices[i].toString();
              aChoice = aChoice.replace("_"," ");
              System.out.println(menuChoices[i].ordinal() + " - " + aChoice);
          }
          System.out.println(menuChoices[0].ordinal() + " - " + menuChoices[0].toString());
    }
    /**
     * Prompt for login credentials
     *
     * @return - Credentials entered by the user
     */
    public UserCredentials promptForCredentials() {
        String username = promptForString("Username (lower case only): ");
        String password = promptForString("Password (lower case only): ");
        return new UserCredentials(username, password);
    }
    /**
     * Pause user interaction until enter is pressed
     */
    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    /**
     * Display general error message when details are in log file
     */
    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    /**
     * Display error message when invalid choice is made from a menu
     */
    public void printMenuSelectionError() {
        System.out.println("!!!! Invalid Selection !!!");
    }
    /**
     * Display application exit message
     *
     * @param currentUser - Currently logged-in user; null if no user logged in
     */
    public void printAuRevoirMessage(String currentUser) {
        System.out.printf("\n%50s\nThanks for using frankmo%s\nHope to see you again!\n%50s"
                          , "-".repeat(50)
                          , currentUser != null ? ", " + currentUser : ""
                           , "-".repeat(50));
    }
    /**
     * Print feature not implemented yet message
     */
    public void printFeatureNotAvailable() {
        System.out.println("!".repeat(5) + " Feature unavailable " + "!".repeat(5) + "\n");
    }

    /********************************************************************************************************
     * Prompt user for various data types with validation for correct data
     ********************************************************************************************************/
    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }
    public double promptForDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
