package com.frankmo.app.model;

import java.math.BigDecimal;
import java.util.Objects;

public class frankmoAccount {

    private Long          account_id;
    private int           user_id;
    private BigDecimal    balance;

    public frankmoAccount() {
    }

    public frankmoAccount(Long account_id, int user_id, BigDecimal balance) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account_id="   + account_id +
                ", user_id="    + user_id +
                ", balance="    + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof frankmoAccount)) return false;
        frankmoAccount that = (frankmoAccount) o;
        return getAccount_id().equals(that.getAccount_id()) && getUser_id() == getUser_id();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccount_id(), getUser_id());
    }

}
