package com.mycompany.myapp.exception;

public class CustomExceptions {

    //테스트
    public static class testException extends RuntimeException{
        public testException(String message){
            super(message);
        }
    }

    public static class RefreshTokenInvalidException extends  RuntimeException{
        public RefreshTokenInvalidException(String message){super(message);}
    }

    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(String message){super(message);}
    }

}
