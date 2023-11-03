package com.frankmo.app.datasource.dao;

import com.frankmo.app.datasource.model.frankmoAccount;

import java.util.List;

public interface frankmoAccountDao {

    public List<frankmoAccount> getAllAccounts();

    public frankmoAccount getAccountForAccountId(Long theAccountId);

    public List<frankmoAccount> getAccountsForAUserId(int anUserId);

    public frankmoAccount saveAccount(frankmoAccount frankmoAccount2Save);

    public frankmoAccount updateAccount(frankmoAccount frankmoAccount2Update);


}
