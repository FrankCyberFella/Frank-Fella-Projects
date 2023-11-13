package com.frankfella.controller;

import com.frankfella.datasource.model.taskmanager.Task;
import com.frankfella.datasource.model.taskmanager.TaskDao;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.frankfella.generalpurposeutilities.LogHttpRequest.logHttpRequest;
import static com.frankfella.generalpurposeutilities.LogHttpRequest.logMessage;

@RestController
@CrossOrigin
public class TaskManagerAPIController {

    TaskDao theTasksDatasource;

    public TaskManagerAPIController(TaskDao theInjectedTasksDataSource) {
        theTasksDatasource = theInjectedTasksDataSource;
    }

    @RequestMapping(path="/tasks", method=RequestMethod.GET)
    public ResponseEntity<List<Task>> returnAllTasks(HttpServletRequest theRequest) {
        logHttpRequest(theRequest);
        return new ResponseEntity(theTasksDatasource.getAllTasks(), HttpStatus.OK);
    }

    @RequestMapping(path="/tasks/{taskId}", method=RequestMethod.GET)
    public Task returnATaskFromId(HttpServletRequest theRequest
                                 ,@PathVariable Long taskId ) {
        logHttpRequest(theRequest);
        return theTasksDatasource.getTaskById(taskId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/tasks", method=RequestMethod.POST)
    public Task addANewTask(HttpServletRequest theRequest
                          , @Valid @RequestBody Task newTask ) {
        logHttpRequest(theRequest);
        logMessage("Payload: " + newTask);
        return theTasksDatasource.addATask(newTask);
    }


    @RequestMapping(path="/tasks", method=RequestMethod.PUT)
    public ResponseEntity<Task> updateATask(HttpServletRequest theRequest
            , @Valid @RequestBody Task updatedTask ) {
        logHttpRequest(theRequest);
        logMessage("Payload: " + updatedTask);
        return new ResponseEntity(theTasksDatasource.updateATask(updatedTask), HttpStatus.OK);
    }

    @RequestMapping(path="/tasks/{taskId}", method=RequestMethod.DELETE)
    public ResponseEntity deleteATask(HttpServletRequest theRequest
                          , @PathVariable Long taskId) {
        logHttpRequest(theRequest);
        try {
            theTasksDatasource.deleteATask(taskId);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch(Exception exceptionObject) {
            logMessage(exceptionObject.getClass() + " thrown"
                     + " with message: " + exceptionObject.getLocalizedMessage());
            return  new ResponseEntity("Error deleting task from data source", HttpStatus.CONFLICT);
        }
    }


}
