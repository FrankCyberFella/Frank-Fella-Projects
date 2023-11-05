package com.frankmo.generalpurposeutilities;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHttpRequest {

private static long controllerStartTime;
private static long controllerEndTime;

// Log request with timestamp and information from the Http Request received by the server
public static void logHttpRequest(HttpServletRequest theRequest) {
        controllerStartTime = System.currentTimeMillis();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.println("-".repeat(100));
        System.out.printf("%s --> %4s %4s request for URL: %s%s\n",
        timeNow
        , theRequest.getProtocol()
        , theRequest.getMethod()
        , theRequest.getRequestURI()
        , (theRequest.getQueryString() != null ? ("?" + theRequest.getQueryString()) : ""));
        // the line above will include the query string if there is one
        }
public static void logEndOfProcessInformation() {
        controllerEndTime = System.currentTimeMillis();
        logMessage("Processing time for request: " + (controllerEndTime - controllerStartTime) + " milliseconds");
        }


    // log a message passed in as a parameter
public static void logMessage(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.printf("%s --> %s\n", timeNow, message);
    }

}
