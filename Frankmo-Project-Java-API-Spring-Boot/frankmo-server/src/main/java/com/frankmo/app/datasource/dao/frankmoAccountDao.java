package com.frankmo.app.datasource.dao;

import com.frankmo.app.datasource.model.FrankmoAccount;

import java.util.List;

public interface FrankmoAccountDao {

    public List<FrankmoAccount> getAllAccounts();

    public FrankmoAccount getAccountForAccountId(Long theAccountId);

    public List<FrankmoAccount> getAccountsForAUserId(int anUserId);

    public FrankmoAccount saveAccount(FrankmoAccount frankmoAccount2Save);

    public FrankmoAccount updateAccount(FrankmoAccount frankmoAccount2Update);


}
