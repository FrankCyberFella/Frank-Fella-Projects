package com.frankmo.frankmo.exceptions;

public class MultipleAccountsForUser extends RuntimeException {

    public MultipleAccountsForUser() {super(); }

    public MultipleAccountsForUser(String errorMessage) {
        super(errorMessage);
    }
}
