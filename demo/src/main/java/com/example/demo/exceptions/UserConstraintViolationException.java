package com.example.demo.exceptions;

public class UserConstraintViolationException extends RuntimeException{
    public UserConstraintViolationException(){
        super("Please provide user name.");
    }
    
}
