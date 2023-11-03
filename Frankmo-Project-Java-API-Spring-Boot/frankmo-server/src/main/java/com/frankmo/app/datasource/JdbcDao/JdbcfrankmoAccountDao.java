package com.frankmo.app.datasource.JdbcDao;


import com.frankmo.app.datasource.dao.frankmoAccountDao;
import com.frankmo.app.datasource.model.frankmoAccount;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcfrankmoAccountDao implements frankmoAccountDao {

    private JdbcTemplate thefrankmoDataBase;

    public JdbcfrankmoAccountDao(JdbcTemplate theInjectedJdbcTemplate) {
        this.thefrankmoDataBase = theInjectedJdbcTemplate;
    }

    @Override
    public List<frankmoAccount> getAllAccounts() {

        List<frankmoAccount> allAccounts = new ArrayList();

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
    public List<frankmoAccount> getAccountsForAUserId(int theUserId) {

        List<frankmoAccount> userAccounts = new ArrayList();

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
    public frankmoAccount getAccountForAccountId(Long theAccountId) {

        frankmoAccount theAccount = null;

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
    public frankmoAccount saveAccount(frankmoAccount frankmoAccount2Save) {

       frankmoAccount newAccount = new frankmoAccount(frankmoAccount2Save.getAccount_id()
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
    public frankmoAccount updateAccount(frankmoAccount frankmoAccount2Update) {

        String updateAccountSQL = "update account "
                                +" Set balance = ? "
                                +" where account_id = ?";


        thefrankmoDataBase.update(updateAccountSQL
                               ,frankmoAccount2Update.getBalance()
                               ,frankmoAccount2Update.getAccount_id());

        return getAccountForAccountId(frankmoAccount2Update.getAccount_id());
    }

    private frankmoAccount mapRowToAccount(SqlRowSet resultRow) {
        return new frankmoAccount(resultRow.getLong("account_id")
                               ,resultRow.getInt("user_id")
                               ,resultRow.getBigDecimal("balance"));
    }
}
