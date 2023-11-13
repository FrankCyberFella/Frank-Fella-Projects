package com.frankfella.datasource.model.exception;

public class UpdateTaskException extends RuntimeException{
    public UpdateTaskException() { super();}

    public UpdateTaskException(String message) {
        super(message);
    }
}

