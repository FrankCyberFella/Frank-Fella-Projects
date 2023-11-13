package com.frankfella.datasource.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason="Error Deleting Task from data source - task was not deleted")
public class DeleteTaskException extends RuntimeException {

    public DeleteTaskException() { super();}

    public DeleteTaskException(String message) {
        super(message);
    }
}
