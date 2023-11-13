package com.frankfella.datasource.model.taskmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

public class Task {

    private long      taskId;
    @NotNull (message="Due Date cannot be empty or blank")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("duedate")
    private LocalDate dueDate;
    @NotNull
    @NotBlank (message="description cannot be empty or blank")
    private String    description;
    private boolean   complete;

    public Task() {}

    public Task(long taskId, LocalDate dueDate, String description, boolean complete) {
        this.taskId      = taskId;
        this.dueDate     = dueDate;
        this.description = description;
        this.complete    = complete;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId && complete == task.complete && Objects.equals(dueDate, task.dueDate) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, dueDate, description, complete);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId="         + taskId +
                ", dueDate="      + dueDate +
                ", description='" + description + '\'' +
                ", complete="    + complete +
                '}';
    }
}

