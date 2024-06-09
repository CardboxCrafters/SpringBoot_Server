package com.mycompany.myapp.exception;

public class CustomExceptions {

    public static class Exception extends RuntimeException{
        public Exception(String message){
            super(message);
        }
    }
}
