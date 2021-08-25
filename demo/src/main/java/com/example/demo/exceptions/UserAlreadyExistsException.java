package com.example.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String name){
        super("A user with the given name "+name+" Already exists.");
    }
    
}
