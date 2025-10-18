package com.hanuman.event.Exception;

public class QRCodeNotFoundException extends RuntimeException {
    
    public QRCodeNotFoundException(String msg){
        super(msg);
    }
}
