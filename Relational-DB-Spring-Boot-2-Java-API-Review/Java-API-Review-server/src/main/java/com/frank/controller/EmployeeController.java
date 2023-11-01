package com.frank.controller;

import com.frank.model.Employee;
import com.frank.model.EmployeeDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    EmployeeDAO theEmployeeData;

    public EmployeeController(EmployeeDAO theEmployeeMethods) {
        theEmployeeData = theEmployeeMethods;
    }

    /**
     * Find all employees whose names match the search strings. Names should
     * contain both first and last name searches. If a search string is blank,
     * ignore it. Be sure to use LIKE or ILIKE for proper search matching!
     *
     *  HTTP Request: GET
     *  path: /employee?firstname=""&lastname-""
     *
     *  public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch);
    */
    @RequestMapping(path="/employee", method= RequestMethod.GET)
    public List<Employee> getSearchedEmployess(@RequestParam(defaultValue = "", required=false) String firstname,
                                               @RequestParam(defaultValue = "", required=false) String lastname) {
        LogAPIRequest.logAPICall("GET  - /employee?firstname="+firstname+"&lastname="+lastname);
        return theEmployeeData.searchEmployeesByName(firstname, lastname);
    }

    /**
     * Get all the employees that are in the department with the {@code id}.
     *
     *  HTTP Request: GET
     *  path: /employee/department/{deptId}
     *
     * DAO method: public List<Employee> getEmployeesByDepartmentId(long id);
     */
    @GetMapping  // Alternative to method=RequestMethod.GET in @RequestMapping
    @RequestMapping(path="/employee/department/{deptId}")
    public List<Employee> getEmployeesForDept(@PathVariable Long deptId){
        LogAPIRequest.logAPICall("GET  - /employee/department/"+deptId);
        return theEmployeeData.getEmployeesByDepartmentId(deptId);
    }

    /**
     * Get all of the employees that are on the project with the given {@code id}.
     *
     * HTTP Request: GET
     * Path: /employees/project/{projectId}
     *
     * DAO method: public List<Employee> getEmployeesByProjectId(Long projectId);
     */
    @GetMapping
    @RequestMapping(path="/employee/project/{projectId}")
    public List<Employee> findEmployeesOnProject(@PathVariable Long projectId) {
        LogAPIRequest.logAPICall("GET  - /employee/project/"+projectId);
        return theEmployeeData.getEmployeesByProjectId(projectId);
    }

    /**
     * Get all of the employees that aren't assigned to a project.
     *
     * HTTP Request: GET
     * Path: /employees/project/none
     *
     * DAO method: public List<Employee> getEmployeesWithoutProjects();
     */
    @GetMapping
    @RequestMapping(path="/employee/project/none")
    public List<Employee> findEmployeesWithoutProjects() {
        LogAPIRequest.logAPICall("GET  - /employee/project/none");
        return theEmployeeData.getEmployeesWithoutProjects();
    }

    /**
     * Change the given employee to the new department.
     *
     * HTTP Request: PUT
     * Path: /employee/{employeeId}/?newdept=
     *
     * DAO method: public void changeEmployeeDepartment(Long employeeId, Long departmentId);
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path="/employee/{employeeId}", method=RequestMethod.PUT)
    public void changeEmployeesdDept(@RequestParam(value="newdept") Long newDept
                                    ,@PathVariable Long employeeId) {
        LogAPIRequest.logAPICall("GET  - /employee/"+employeeId+"?newdept="+newDept);
        theEmployeeData.changeEmployeeDepartment(employeeId, newDept);
    }

}
