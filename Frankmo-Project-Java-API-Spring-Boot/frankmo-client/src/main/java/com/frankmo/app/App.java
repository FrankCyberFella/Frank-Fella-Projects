package com.frankmo.app;

import com.frankmo.app.security.model.AuthenticatedUser;
import com.frankmo.app.security.model.UserCredentials;
import com.frankmo.app.services.AuthenticationService;
import com.frankmo.app.services.ConsoleService;
import com.frankmo.app.services.frankmoService;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService userInterface = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final frankmoService frankmoServices = new frankmoService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();      // Define the application object
        app.run();                // Tell the application object to start running
    }

    /**
     * Application management method - controll all aspects of the running application
     */
    private void run() {
        userInterface.printGreeting();   // Greet the user
        loginMenu();                     // Present opportunity to register and login
        if (currentUser != null) {       // If they have logged in...
            mainMenu();                  // Display main processing menu/loop
            exitApp();                   // When done with main processing - perform exit processing
        }
        return;                          // and return to caller (unnecessary, but included for clarity)
    }

    /**
     * Present user with opportunity to register and login
     */
    private void loginMenu() {

        boolean isLoggedIn       = false;  // Loop control variable - assume user is not logged in
        boolean exitMenuSelected = false;  // loop control variable - assume exit option has not been chosen

        while (!exitMenuSelected && !isLoggedIn) { // Loop while exit option not chosen and user is not logged
            userInterface.printLoginMenu();
            int menuSelection = userInterface.promptForLoginMenuSelection("Please choose an option: ");
            switch (menuSelection) {
                case 0: {                               // If exit option chosen...
                    exitMenuSelected = true;            //    indicate to end login loop
                    userInterface.printExitLoginMenu(); //    display exit message
                    break;                              //    and leave switch statement
                }
                case 1: {                              // if register option chosen...
                    handleRegister();                  //    perform registration process
                    break;                             //    and leave switch statement
                }
                case 2: {                              // if login option chosen...
                    isLoggedIn = handleLogin();        //    perform login process and set loop control variable
                    break;                             //    and leave switch statement
                }
                default: {                             // if an option is chosen we didn't expect...
                   userInterface.printMenuSelectionError(); // display error message
                    userInterface.pause();                  // wait for user to press enter
                    break;                                  // and leve switch statement
                }
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = userInterface.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            userInterface.printErrorMessage();
        }
    }

    private boolean handleLogin() {
        boolean isLoginSuccessful = false;
        UserCredentials credentials = userInterface.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser != null) {
            isLoginSuccessful = true;
            userInterface.printLoginWelcome(currentUser);
        }
        else {
            isLoginSuccessful = false;
            userInterface.printErrorMessage();
        }
        return isLoginSuccessful;

    }

    private void exitApp() {
        userInterface.printAuRevoirMessage(currentUser.getUser().getUsername());
    }

    private void mainMenu() {
        boolean leaveApp = false;
        while (!leaveApp) {
            userInterface.printMainMenu();
            ConsoleService.MAIN_MENU_CHOICES menuSelection = userInterface.promptForMainMenuSelection("Please choose an option: ");
            if(menuSelection == null) {  // if no valid option selected...
                continue;                //    loop again
            }
            switch(menuSelection) {
                case Exit: {               // If exit option chosen...
                    leaveApp = true;       //    set loop control variable to exit loop
                    break;                 //    and exit switch statement
                }
                case View_Your_Current_Balance: {             // If option to see current balance is chosen...
                frankmoServices.viewCurrentBalance(currentUser);//    call service to perform the process
                break;                                        //    and exit switch statement
                }
                case View_Your_Past_Transfers: {                    // If option to see transfer history is chosen...
                    frankmoServices.viewTransferHistory(currentUser); //    call service to perform the process
                    break;                                          //    and exit switch statement
                }
                case View_Your_Pending_Requests: {                  // If option to see pending transfer requests is chosen...
                    frankmoServices.viewPendingRequests(currentUser); //    call service to perform the process
                    break;                                          //    and exit switch statement
                }
                case Send_TE_Bucks: {                              // If option to see transfer money is chosen...
                    frankmoServices.sendBucks(currentUser);          //    call service to perform the process
                    break;                                         //    and exit switch statement
                }
                case Request_TE_Bucks:  {                          // If option to see request money is chosen...
                    frankmoServices.requestBucks(currentUser);       //    call service to perform the process
                    break;                                         //    and exit switch statement
                }
                default:                                           // If option we didn't expect is chosen...
                  userInterface.printMenuSelectionError();         //    issue error message
                  userInterface.pause();                           //    wait for user interaction to proceed
                  break;                                           //    and exit switch statement
                }
        }
        return;  // not necessary, but coded for clarity
        }
	}

