package com.frank.controller;
/******************************************************************************************************
 * Class to be used to LogAPICalls with timestamp and user provided message
 *
 * use:  LogAPICalls.LogAPICall(message)
 ****************************************************************************************************/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogAPIRequest {
    /* method is static so it can be called using the class name */

    public static void logAPICall(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);
        System.out.println(timeNow + "-" + message);
    }
}
