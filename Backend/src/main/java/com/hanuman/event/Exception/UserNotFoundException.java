package com.hanuman.event.Exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
