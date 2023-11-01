package com.frank.controller;

import com.frank.model.Project;
import com.frank.model.ProjectDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {
    ProjectDAO theProjects;

    ProjectController(ProjectDAO theProjectAccessMethods) {
        theProjects = theProjectAccessMethods;
    }

    /**
     * Get a list of all active projects. A project is active if we are past
     * its from_date but before its to_date. If its to_date is null, then
     * we consider it ongoing. If its from_date is null, then we consider
     * it not started yet.
     *
     * HTTP Request: GET
     * path: /project
     *
     * DAO method:  public List<Project> getAllActiveProjects();
     */
    @GetMapping
    @RequestMapping(path="/project")
    public List<Project> getAllActiveProjects(){
        return theProjects.getAllActiveProjects();
    }

    /**
     * Unassign the employee from a project.
     *
     * HTTP Request: DELETE
     * path: /project/{id}?employee=
     *
     * DAO Method: public void removeEmployeeFromProject(Long projectId, Long employeeId);
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    @RequestMapping(path="/project/{projectID}/employee/{employeeID")
    public void removeEmployeeFromProject(@PathVariable Long projectID
                                         ,@RequestParam Long employeeID) {
        theProjects.removeEmployeeFromProject(projectID, employeeID);
    }
    /**
     * Assign an employee to a project
     *
     *  HTTP Request: POST
     *  path: /project/{projectId}/employee/{employeeId}
     *  public void removeEmployeeFromProject(Long projectId, Long employeeId);
     *
     *
     * DAO method: public void addEmployeeToProject(Long projectId, Long employeeId);
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    @RequestMapping(path="/project/{projectId}/employee/{employeeId}")
    public void addEmployeeToProject(@PathVariable Long projectId
                                    ,@PathVariable Long employeeId){
       theProjects.addEmployeeToProject(projectId,employeeId);
    }



}
