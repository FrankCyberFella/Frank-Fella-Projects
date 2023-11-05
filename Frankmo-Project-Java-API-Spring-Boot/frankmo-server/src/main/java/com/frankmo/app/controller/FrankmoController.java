package com.frankmo.app.controller;

import com.frankmo.app.datasource.dao.FrankmoAccountDao;
import com.frankmo.app.datasource.dao.FrankmoTransferDao;
import com.frankmo.app.datasource.model.FrankmoAccount;
import com.frankmo.app.datasource.model.FrankmoTransfer;
import com.frankmo.app.usermanagement.model.User;
import com.frankmo.app.usermanagement.model.dao.UserDao;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.frankmo.generalpurposeutilities.LogHttpRequest.logHttpRequest;
import static com.frankmo.generalpurposeutilities.LogHttpRequest.logMessage;

@RestController
public class FrankmoController {

    private FrankmoAccountDao thefrankmoAccountDao;
    private UserDao            theUserDao;
    private FrankmoTransferDao thefrankmoTransferDao;

       public FrankmoController(FrankmoAccountDao theInjectedfrankmoAccountDao
                          , UserDao theInjectedUserDao
                          , FrankmoTransferDao theInjectedfrankmoTransferDao) {
        thefrankmoAccountDao  = theInjectedfrankmoAccountDao;
        theUserDao            = theInjectedUserDao;
        thefrankmoTransferDao = theInjectedfrankmoTransferDao;
    }

    /**
     * Handles an HTTP POST request for the path: /account
     *
     * Add a frankmoAccount object to  the datasource
     *
     * @param aNewAccount - must be present in the request body as a valid JSON object for a frankmoAccount object
     *                      Note: If an account is sent in the JSON object it will be ignored as the data source
     *                            manager will assign a unique account id when storing the frankmoObject
     *
     * @return the frankmoAccount object with the data source assigned accountId
     */
    @RequestMapping(path="/account", method= RequestMethod.POST)
    public FrankmoAccount addAnAccount(HttpServletRequest theHttpRequest, @RequestBody FrankmoAccount aNewAccount) {
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        return thefrankmoAccountDao.saveAccount(aNewAccount);
    }

    /**
     * Handle an HTTP GET request for the path: /account/{accountId}
     *
     * Return the account from the data source with the accountId provided
     *
     * Note: The accountId requested must be specified as a path variable in the request
     *
     * @param theAcctId - must be specified as a path variable
     *
     * @return the frankmoAccount object for the accountId specified or null
     */
    @RequestMapping(path="/account/{theAcctId}", method= RequestMethod.GET)
    public FrankmoAccount getAnAccount(HttpServletRequest theHttpRequest,
                                       @PathVariable Long theAcctId) {
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        return thefrankmoAccountDao.getAccountForAccountId(theAcctId);
    }

    /**
     * Handle an HTTP GET request for either the path: /account
     *                                             or: /account?id=userId
     *
     * if the /account path is used for the request, all frankmoAccounts in the datasource will be returned
     *
     * if the /account?id=usedId path is used in the request, all accounts for the specified userid will be returned
     *
     * @param theUserId - optional query parameter to request all accounts for a specific userid
     *
     * @return - a list containing all accounts indicated by the path or an empty list if no accounts found
     */
    @RequestMapping(path="/account", method= RequestMethod.GET)
    public List<FrankmoAccount> getAccountsForUser(HttpServletRequest theHttpRequest,
                                                   @RequestParam (name="userid", required=false, defaultValue="-1") int theUserId) {

        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        if (theUserId == -1) {
            return thefrankmoAccountDao.getAllAccounts();
        }
        return thefrankmoAccountDao.getAccountsForAUserId(theUserId);
    }
    /**
     * Handles an HTTP GET request for path /user
     *
     * @return - a list of all users in the data source
     */
    @RequestMapping(path="/user", method = RequestMethod.GET)
    public List<User> getUsers(HttpServletRequest theHttpRequest,
                               @RequestParam (name="userid", required=false, defaultValue="-1") int theUserId) {
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        List<User> theUsers = new ArrayList<>();
        if (theUserId == -1) {
            theUsers = theUserDao.findAll();
        }
        else {
            theUsers.add(theUserDao.findUserById(theUserId));
        }
        return theUsers;
    }
    /**
     * Handles an HTTP PUT request for the path: /account
     *
     * Add a frankmoAccount object to  the datasource
     *
     * @param theUpdatedAcct - must be present in the request body as a valid JSON object for a frankmoAccount object
     *
     *
     * @return the update frankmoAccount object from the datasource
     */
    @RequestMapping(path="/account", method= RequestMethod.PUT)
    public FrankmoAccount updateAnAccount(HttpServletRequest theHttpRequest,            // Get request as an HttpServletRequest object
                                          @RequestBody FrankmoAccount theUpdatedAcct) { // Get JSON from request body and create FrankmoAccount object
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        logMessage("updated acct: " + theUpdatedAcct);
        return thefrankmoAccountDao.updateAccount(theUpdatedAcct);
    }

    /**
     * Handles an HTTP POST request for the path: /transfer
     *
     * Add a frankmoTransfer object to  the datasource
     *
     * @param theTransfer - must be present in the request body as a valid JSON object for a frankmoTransfer object
     *
     *
     * @return the update frankmoTransfer object from the datasource
     */
    @RequestMapping(path="/transfer", method= RequestMethod.POST)
    public FrankmoTransfer addATransfer(HttpServletRequest theHttpRequest,           // Get request as an HttpServletRequest object
                                        @RequestBody FrankmoTransfer theTransfer) {  // Get JSON from request body and create FrankmoTransfer object
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        logMessage("transfer received: " + theTransfer);
        FrankmoTransfer newTransfer = thefrankmoTransferDao.saveTransfer(theTransfer);
        return newTransfer;
    }

    /**
     * Handles HTTP GET for path /transfer?id=userid
     *
     * Return all transfer for the userid given
     *
     * @param id - the userid whose transfers should be returned
     */
    @RequestMapping (path="/transfer", method=RequestMethod.GET)
    public List<FrankmoTransfer> getTransfersForUser(HttpServletRequest theHttpRequest,     // Get request as an HttpServletRequest object
                                                     @RequestParam int id) {                // Get the is queey parameter from the URL query string
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        List<FrankmoTransfer> allTransfersForUser = thefrankmoTransferDao.getTransfersForUser(id);
        return allTransfersForUser;
    }
}
