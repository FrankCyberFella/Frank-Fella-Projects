package com.frankmo.generalpurposeutilities;
/******************************************************************************************************
 * Class to be used to LogAPICalls with timestamp and user provided message
 *
 *          classname.method(parameters)
 * use:  LogAPIRequest.LogAPICall(message)
 *
 * The static method(s) in the class may be called from anywhere using the class name
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
