package com.frankmo.app.datasource.JdbcDao;


import com.frankmo.app.datasource.dao.FrankmoAccountDao;
import com.frankmo.app.datasource.model.FrankmoAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFrankmoAccountDao implements FrankmoAccountDao {

    private JdbcTemplate thefrankmoDataBase;

    public JdbcFrankmoAccountDao(JdbcTemplate theInjectedJdbcTemplate) {
        this.thefrankmoDataBase = theInjectedJdbcTemplate;
    }

    @Override
    public List<FrankmoAccount> getAllAccounts() {

        List<FrankmoAccount> allAccounts = new ArrayList();

        String getAccountsSQL = "select account_id"
                +"      ,user_id  "
                +"      ,balance  "
                +"from Account ";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getAccountsSQL);

        while(sqlResult.next()) {
            allAccounts.add(mapRowToAccount(sqlResult));
        }
        return allAccounts;
    }


    @Override
    public List<FrankmoAccount> getAccountsForAUserId(int theUserId) {

        List<FrankmoAccount> userAccounts = new ArrayList();

        String getAccountsSQL = "select account_id"
                               +"      ,user_id  "
                               +"      ,balance  "
                               +"from Account "
                               +"where user_id = ?";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getAccountsSQL, theUserId);

        while(sqlResult.next()) {
           userAccounts.add(mapRowToAccount(sqlResult));
        }

        return userAccounts;
    }

    @Override
    public FrankmoAccount getAccountForAccountId(Long theAccountId) {

        FrankmoAccount theAccount = null;

        String getAccountsSQL = "select account_id"
                               +"      ,user_id  "
                               +"      ,balance  "
                               +"from Account "
                               +"where account_id = ?";

        SqlRowSet sqlResult = thefrankmoDataBase.queryForRowSet(getAccountsSQL, theAccountId);

        while(sqlResult.next()) {
            theAccount = mapRowToAccount(sqlResult);
        }

        return theAccount;
    }


    @Override
    public FrankmoAccount saveAccount(FrankmoAccount frankmoAccount2Save) {

       FrankmoAccount newAccount = new FrankmoAccount(frankmoAccount2Save.getAccount_id()
                                                 ,frankmoAccount2Save.getUser_id()
                                                 ,frankmoAccount2Save.getBalance());
       // Database manager will assign new account_id which we are asking to be returned
       String addAccountSQL = "insert into account "
                                      +" (user_id, balance) "
                                      +" values(?, ?)"
                                      +" Returning account_id";

        Long newAccountId = thefrankmoDataBase.queryForObject(addAccountSQL
                                                              ,Long.class
                                                              ,newAccount.getUser_id()
                                                              ,newAccount.getBalance());


        newAccount.setAccount_id(newAccountId);

        return newAccount;
    }

    @Override
    public FrankmoAccount updateAccount(FrankmoAccount frankmoAccount2Update) {

        String updateAccountSQL = "update account "
                                +" Set balance = ? "
                                +" where account_id = ?";


        thefrankmoDataBase.update(updateAccountSQL
                               ,frankmoAccount2Update.getBalance()
                               ,frankmoAccount2Update.getAccount_id());

        return getAccountForAccountId(frankmoAccount2Update.getAccount_id());
    }

    private FrankmoAccount mapRowToAccount(SqlRowSet resultRow) {
        return new FrankmoAccount(resultRow.getLong("account_id")
                               ,resultRow.getInt("user_id")
                               ,resultRow.getBigDecimal("balance"));
    }
}
