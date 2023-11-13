package com.frankfella.datasource.model.taskmanager;


import java.util.List;

public interface TaskDao {
    /**
     * Retrieve all tasks from data source
     *
     * @param  - none
     * @return - list of tasks
     */
    public List<Task> getAllTasks();

    /**
     * Retrieve a task from data source for given taskId
     *
     * @param taskId
     * @return a Task object
     */
    public Task getTaskById(Long taskId);

    /**
     * Update task in data sourceusing give Task object
     *
     * @param newTask
     * @return Task Object with primary key value filled in
     */
    public Task addATask(Task newTask);

    /**
     * Delete task from data source using given Task object
     *
     * @param updatedTask
     * @return theUpdateTask from the datasource
     */
    public Task updateATask(Task updatedTask);

    /**
     * Remove a given task from data source
     *
     * @param task2Delete
     */

    public void deleteATask(Long taskId);
}

