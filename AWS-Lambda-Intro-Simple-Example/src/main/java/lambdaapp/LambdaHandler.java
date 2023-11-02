package lambdaapp;

import model.RequestData;
import model.ResponseData;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaHandler implements RequestHandler<RequestData, ResponseData> {
    /*
    Method to handle AWS Lambda requests
     */
    @Override
    //     return-type  method-name(object-with-request-data, aws-lambda-context-object)
    public ResponseData handleRequest(RequestData theRequest, Context context) {
        // an aws-lambda-context-object supplies methods and properties that provide
        //    information about the invocation, function, and execution environment.

        /*
         Instantiate an object of the class containing the main application methods
         */
        AppFunctions theApp = new AppFunctions();

        /*
         For debugging purposes, display teh request received for processing
         */
        System.out.println("Request Received: " + theRequest);

        /*
        Instantiate a response object and set it to teh result of calling the appMain() method for the application
         */
        ResponseData theResponse = theApp.appMain(theRequest);

        /*
         For debugging purposes, display the response object recieved from the application
         */
        System.out.println("Response was: " + theResponse);

        /*
         Return the response object from the application
         */
        return theResponse;
    }
}
