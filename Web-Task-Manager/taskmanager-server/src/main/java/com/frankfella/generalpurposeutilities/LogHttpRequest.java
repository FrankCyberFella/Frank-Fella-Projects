package com.frankfella.generalpurposeutilities;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHttpRequest {

    // Log request with timestamp and information from the Http Request received by the server
    public static void logHttpRequest(HttpServletRequest theRequest) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.println("-".repeat(150));
        System.out.printf("%s --> %4s %4s request for URL: %s%s\n"
                , timeNow
                , theRequest.getProtocol()
                , theRequest.getMethod()
                , theRequest.getRequestURI()
                , (theRequest.getQueryString() != null ? ("?" + theRequest.getQueryString()) : ""));
        // the line above will include the query string if there is one
    }

    // log a message passed in as a parameter
    public static void logMessage(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.printf("%s --> %s\n", timeNow, message);
    }

}