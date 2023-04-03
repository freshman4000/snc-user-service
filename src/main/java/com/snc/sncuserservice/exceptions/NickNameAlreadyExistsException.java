package com.snc.sncuserservice.exceptions;

public class NickNameAlreadyExistsException extends RuntimeException{
    public NickNameAlreadyExistsException() {
        super("NICKNAME_ALREADY_EXISTS");
    }
}
