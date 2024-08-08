package com.ideas2it.exception;

public class EmployeeException extends Exception{
    public EmployeeException(String message, Throwable cause){
        super(message, cause);
    }

    public EmployeeException(String message) {
        super(message);
    }
}
