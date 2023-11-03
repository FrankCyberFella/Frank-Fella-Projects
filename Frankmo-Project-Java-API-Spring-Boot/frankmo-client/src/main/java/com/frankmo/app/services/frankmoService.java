package com.frankmo.app.services;

import com.frankmo.app.model.frankmoAccount;
import com.frankmo.app.model.frankmoTransfer;
import com.frankmo.app.security.model.AuthenticatedUser;
import com.frankmo.app.security.model.User;
import com.frankmo.app.services.interfaces.CurrencyTransferProcesses;
import com.frankmo.frankmo.exceptions.MultipleAccountsForUser;
import com.frankmo.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

public class frankmoService implements CurrencyTransferProcesses {

    private final ConsoleService userInterface = new ConsoleService();

    // Define base URL for server to be used in server API calls
    private final String API_BASE_URL = "http://localhost:8080";

    // Instantiate a RestTemplate object to be used in calls to the API server
    private RestTemplate theDataSourceApi = new RestTemplate();

    /**
     * Get all the accounts from the data source
     *
     * @return - a list containing all the accounts in the data source - null if no accounts
     */
    @Override
    public List<frankmoAccount> getAllAccounts() {
        // Call Server API with path to retrieve all accounts from the datasource as an array
        frankmoAccount allAccounts[] = theDataSourceApi.getForObject(API_BASE_URL + "/account", frankmoAccount[].class);

        // RestTemplate only returns an array, convert the array to a Java List object for return
        return Arrays.asList(allAccounts);
    }

    /**
     * * Get all the accounts for a specific user id from the data source
     *
     * @param userId - the user id whose accounts you want retrieved
     * @return - a list containing all the accounts in the data source for a specific userid - null if no accounts
     */
    @Override
    public List<frankmoAccount> getAllAccountsForAUser(int userId) {
        // Call Server API with path and query parameter to retrieve all accounts for a given userid from the datasource as an array
        frankmoAccount allAccounts4User[] = theDataSourceApi.getForObject(API_BASE_URL + "/account/?userid=" + userId, frankmoAccount[].class);

        // RestTemplate only returns an array, convert the array to a Java List object for return
        return Arrays.asList(allAccounts4User);
    }

    /**
     * Retrieve an account from the data source for a specific account_id
     *
     * @param accountId - the account id to be whose accounts should be retrieved
     * @return - the account for the id specified or null if no account
     */
    @Override
    public List<frankmoAccount> getAnAccount(Long accountId) {
        // Call Server API with path and path variable to retrieve a given accountId from the datasource
        frankmoAccount anAccount = theDataSourceApi.getForObject(API_BASE_URL + "/account/" + accountId, frankmoAccount.class);

        // RestTemplate only returns an array, convert the array to a Java List object for return
        return Arrays.asList(anAccount);
    }

    @Override
    public void viewCurrentBalance(AuthenticatedUser currentUser) {
        // TODONE #1 - Use Case #3 - Display balance of current user

        // Get all accounts for the currently logged-in user
        List<frankmoAccount> userAccounts = this.getAllAccountsForAUser(currentUser.getUser().getId());

        // Loop through the list of logged-in users and display their account information
        for (frankmoAccount anAccount : userAccounts) {
            userInterface.displayAnAccount(anAccount, currentUser);
        }
    }

    @Override
    public User[] getUserForAccount(frankmoAccount anAccount) {
        return theDataSourceApi.getForObject(API_BASE_URL + "/user?userid="+ anAccount.getUser_id(), User[].class);
    }

    @Override
    public boolean sendBucks(AuthenticatedUser currentUser) {
        boolean isSent = false;
        // TODONE #1 - Use Case #4 - Send bucks to another user
        // 1. Call service method to get list of all accounts in data source
        // 2. Remove account for current user from list
        //    a. Get all accounts for the current user
        //    b. If the current user has multiple accounts
        //       i. Throw an exception
        //    c. Remove current user account from list of all accounts
        // 3. Display list of users from list of all accounts
        // 4. Ask which one to send bucks to
        // 5. Ask how much money to send
        // 6. Call service method to do transfer

        // Call service method to get list of all accounts in datasource
        //     except the currentuser's accounts

        List<frankmoAccount> allButCurrentUserAccts = getAllButCurrUsersAccts(currentUser);

        if(allButCurrentUserAccts.size() < 1) {
            userInterface.printProcessCancelledMessage("No accounts available to be transferred to");
            isSent = false;
            return isSent;
        }
        // Display list of users from list of all accounts
        //      and ask which one to send bucks to
        frankmoAccount toUserAccount = userInterface.selectFromUserList(allButCurrentUserAccts);

        if(toUserAccount == null) {
            userInterface.printProcessCancelledMessage("Transfer cancelled per user request");
            isSent = false;
            return isSent;
        }

        Double amt2Send = userInterface.promptForDouble("How much money do you want to send? ");

        boolean isConfirmed = userInterface.promptForConfirmation("You want to send $" + amt2Send + " to user " + toUserAccount.getUser_id() + " is that correct?");

        if (!isConfirmed || amt2Send < 0.00) {
            userInterface.printProcessCancelledMessage("Transfer of $" + amt2Send + " to user " + toUserAccount.getUser_id());
            isConfirmed = false;
        }
        // Call service method to do transfer
       if(isConfirmed) {
           frankmoAccount currentUserAccount = getCurrentUserfrankmoAccount(currentUser);
           isSent = processTransfer(currentUserAccount, toUserAccount, amt2Send);
       }
        return isSent;
    }
@Override
    public void viewTransferHistory(AuthenticatedUser currentUser) {
        // TODONE #2 - Use Case #5 - View List of Transfers
        List<frankmoTransfer> userTransfers = getUserTransfersFromDataSource(currentUser);

        // Display list of transfers for user
        //      and ask if they want details for one
        frankmoTransfer selectedTransfer = userInterface.selectFromTransferList(userTransfers);

        // TODONE #3 - use Case #6 - Display details for a chosen transfer
        if (selectedTransfer != null) {
            userInterface.printTransferDetails(selectedTransfer);
        }
    }
@Override
    public void requestBucks(AuthenticatedUser currentUser) {
    userInterface.printFeatureNotAvailable(); // remove this when you implement this feature
        // TODO #4 (optional) Use Case #7 Request Frankmo Bucks from another user
        // 1. Display list of registered users - not including current user
        // 2. Ask which user from which request is to be made
        // 3. Ask for amount requested
        // 4. Store transfer request on data source


    }
@Override
    public void viewPendingRequests(AuthenticatedUser currentUser) {
        userInterface.printFeatureNotAvailable(); // remove this when you implement this feature
        // TODO #5 (optional) - Use Case #8 - View Pending Requests Requests


        // TODO #6 (optional) - Use Case #9 - Approve or Reject Pending Transfer Requests

    }

public boolean processTransfer(frankmoAccount fromAccount, frankmoAccount toAccount, Double amt2Send) {
        boolean isTransferred = false;

        // Determine if from account will be overdrawn
        boolean fromAcctOverdrawn = (fromAccount.getBalance().compareTo(new BigDecimal(amt2Send)) < 0);

        // if from account will be overdrawn, issue process cancellation message to that effect
        if (fromAcctOverdrawn) {
            userInterface.printProcessCancelledMessage("Insufficient funds in account for user " + fromAccount.getUser_id());
            isTransferred = false;
            return isTransferred;
        }

        // Update from account balance in the from account and update on data source
        fromAccount.setBalance(fromAccount.getBalance().subtract(new BigDecimal(amt2Send)));
        updateAcctOnDataSource(fromAccount);

        // Update from account balance in the to account and update on data source
        toAccount.setBalance(toAccount.getBalance().add(new BigDecimal(amt2Send)));
        updateAcctOnDataSource(toAccount);

        // Instantiate transfer object to send to data source server
        frankmoTransfer aTransfer = new frankmoTransfer();
        aTransfer.setFromfrankmoAccount(fromAccount);
        aTransfer.setTofrankmoAccount(toAccount);
        aTransfer.setAmount(new BigDecimal(amt2Send));
        aTransfer.setTransferType(frankmoTransfer.TRANSFER_TYPE.SEND);
        aTransfer.setTransferStatus(frankmoTransfer.TRANSFER_STATUS.APPROVED);

        frankmoTransfer newTransfer = addTransferToDataSource(aTransfer);

        String transferSuccessfulMsg = String.format("Transfer of $%.2f from user %s to user %s was successful\n"
                                                     ,aTransfer.getAmount()
                                                     ,aTransfer.getFromfrankmoAccount().getUser_id()
                                                     ,aTransfer.getTofrankmoAccount().getUser_id()
        );
        userInterface.printMessage(transferSuccessfulMsg);

        return isTransferred;
    }

private boolean updateAcctOnDataSource(frankmoAccount afrankmoAccount) {

        boolean isDataSourceUpdated = false;

        // Define HTTP headers and entity required for the update request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<frankmoAccount> entity = new HttpEntity<>(afrankmoAccount, headers);

        // Call the data source server to update from account on data source
        try {
            theDataSourceApi.put(API_BASE_URL + "/account", entity);
            isDataSourceUpdated = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log("Network error: " + e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log("Server error: " + e.getMessage());
        }
        if (!isDataSourceUpdated) {
            // issue process cancelled message
            userInterface.printProcessCancelledMessage("error updating account on data source server for user: " + afrankmoAccount.getUser_id() + " - see log file for details");
        }
        return isDataSourceUpdated;
    }

    private List<frankmoAccount> getAllButCurrUsersAccts(AuthenticatedUser currentUser){

        frankmoAccount  currentUserAccount= getCurrentUserfrankmoAccount(currentUser);

        // Call service method to get list of all accounts in datasource
        // Note: We need to create a new ArrayList from the List returned
        //       from the server, so we can change it (remove currentuser account).
        //       The List returned from the server is immutable (cannot be changed)
        List<frankmoAccount> allButCurrentUserAccounts = new ArrayList<>(getAllAccounts());

        // Remove current user account from list of all accounts

        // Remove current user account from list of all accounts
        allButCurrentUserAccounts.remove(currentUserAccount);

        return allButCurrentUserAccounts;
    }

    private frankmoAccount getCurrentUserfrankmoAccount(AuthenticatedUser currentUser) {
        //    Get all accounts for the current user
        List<frankmoAccount> currentUserAccounts = getAllAccountsForAUser(currentUser.getUser().getId());

        //    If the current user has multiple accounts... throw an exception
        if (currentUserAccounts.size() > 1) {
            throw new MultipleAccountsForUser("Multiple accounts found for user: " + currentUser.getUser().getId());
        }
        return currentUserAccounts.get(0);
    }

    private frankmoTransfer addTransferToDataSource(frankmoTransfer afrankmoTransfer) {

        boolean isTransferAdded = false;

        // Define HTTP headers and entity required for the update request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<frankmoTransfer> entity = new HttpEntity<>(afrankmoTransfer, headers);

        frankmoTransfer newTransfer = null;
        // Call the data source server to update from account on data source
        try {
             newTransfer = theDataSourceApi.postForObject(API_BASE_URL + "/transfer", entity, frankmoTransfer.class);
             isTransferAdded = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log("Network error: " + e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log("Server error: " + e.getMessage());
        }
        if (!isTransferAdded) {
            // issue process cancelled message
            String transferErrorMsg = String.format("Error recording transfer of $%.2f from user %s to user %s"
                                                    ,afrankmoTransfer.getAmount()
                                                    ,afrankmoTransfer.getFromfrankmoAccount().getUser_id()
                                                    ,afrankmoTransfer.getTofrankmoAccount().getUser_id()
                                                   );
            userInterface.printProcessCancelledMessage(transferErrorMsg + " - see log file for details");
        }
        return newTransfer;
    }


    private List<frankmoTransfer> getUserTransfersFromDataSource(AuthenticatedUser currentUser) {
        // Call Server API with path and query parameter to retrieve all accounts for a given userid from the datasource as an array
        frankmoTransfer allTransfers4User[] = theDataSourceApi.getForObject(API_BASE_URL + "/transfer/?id=" + currentUser.getUser().getId(), frankmoTransfer[].class);

        // RestTemplate only returns an array, convert the array to a Java List object for return
        return Arrays.asList(allTransfers4User);
    }
}


