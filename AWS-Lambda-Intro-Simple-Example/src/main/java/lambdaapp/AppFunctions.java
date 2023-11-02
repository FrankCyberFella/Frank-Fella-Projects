package lambdaapp;

import model.RequestData;
import model.ResponseData;

public class AppFunctions {

    public ResponseData appMain(RequestData aRequest) {
        // Display message delimiting line
        System.out.println("-".repeat(100));

        // Display greetimh using data from the RequestData object passed into the method
        System.out.println("Hello there " + aRequest.getFirstName() + " " + aRequest.getLastName());
        System.out.println("Welcome to the BD Unit_3 Preparedness Task AWS Lambda App");
        System.out.println("I see were born on " + aRequest.getDob());

        // Display message delimiting line
        System.out.println("-".repeat(100));

        // Instantiate a ResponseData object to br returned from this method
        ResponseData theResponse = new ResponseData(aRequest.getFirstName() + " " + aRequest.getLastName()
                                                  , aRequest.getDob());

        // Return the ResponseData object
        return theResponse;
    }
}
