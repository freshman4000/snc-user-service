package com.snc.sncuserservice.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }
}
