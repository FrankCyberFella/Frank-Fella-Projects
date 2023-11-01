package com.frank.controller;

import com.frank.model.Department;
import com.frank.model.DepartmentDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller to process API requests
 */
@RestController  // Tell the server there are methods ih here to handle URL paths it get
public class DepartmentController {

// Define a reference to the Department DAO so we can handle API calls to get Department data
DepartmentDAO theDeptData;

// Constructor for the controller class to receive DAO object(s) and assign to our reference(s)
// Spring will Dependency Inject the DAO object when it instantiates the controller
//        so we don't have to know or care about the concrete class is actually called
//                 (we are loosely coupled to the concrete class for the DAO)
    public DepartmentController(DepartmentDAO theDeptMethods) {
        theDeptData = theDeptMethods;
    }

// Write  controller to handle an API class to get all the departments
//
//  HTTP Request: GET
//  path:   /departments     - no path variable or query string variables needed
//
//  to get data use Department DAO method:
//
//                 public List<Department> getAllDepartments();

@RequestMapping(path="/department", method=RequestMethod.GET)
public List<Department> getTheDepartments() {
    LogAPIRequest.logAPICall("GET  - /department");    // log the request and path we received
    List<Department> theDepartments = new ArrayList();   // hold the result to be returned

    theDepartments = theDeptData.getAllDepartments();  // Call the DAO to get the data

    return theDepartments;
//  return theDeptData.getAllDepartments();  // Call the DAO to get the data and return it
    }

// Write a controller to add a new Department to the department resource
//
//    HTTP Request: POST
//    Path: /department
//
//  DAO method to use:
//
//           public Department createDepartment(Department newDepartment);
//
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(path="/department", method=RequestMethod.POST)
// @RequestBody - Get the data from the request body and store it in a Department object
public Department addNewDepartment(@RequestBody Department newDepartment) {
    LogAPIRequest.logAPICall("POST - /department");
    return   theDeptData.createDepartment(newDepartment);
    }

// Write a controller to update an existing Department to the department resource
//
//    HTTP Request: PUT
//    Path: /department
//
//  DAO method to use:
//
//           public void saveDepartment(Department updatedDepartment);
//
@ResponseStatus(HttpStatus.NO_CONTENT)
@RequestMapping(path="/department", method=RequestMethod.PUT)
// @RequestBody - Get the data from the request body and store it in a Department object
public void updateDepartment(@RequestBody Department newDepartment) {
    LogAPIRequest.logAPICall("PUT - /department");
    theDeptData.saveDepartment(newDepartment);
}

// Write a controller to get and return a specific Department
//
//    HTTP Request: GET
//    Path: /department/{id}
//
//  DAO method to use:
//
//            public Department getDepartmentById(Long id);
//
@RequestMapping(path="/department/{id}")  // GET is implied if no method= is coded
public Department GetDepartment(@PathVariable Long id) {
    LogAPIRequest.logAPICall("PUT - /department");
    return theDeptData.getDepartmentById(id);
}

// Write a controller to get all the departments whose name contains the search string.
//
//    HTTP Request: GET
//    Path: /department/search?name=search-string
//
//  DAO method to use:  public List<Department> searchDepartmentsByName(String nameSearch);
//
@RequestMapping(path="/department/namesearch", method=RequestMethod.GET)
        public List<Department> seaachForADept(@RequestParam(value="name") String searchName) {
        LogAPIRequest.logAPICall("GET   - /department/namesearch?name="+searchName);
        return(theDeptData.searchDepartmentsByName(searchName));
    }

}  // End of controller class


