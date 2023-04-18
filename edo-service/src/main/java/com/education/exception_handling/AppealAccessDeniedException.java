package com.education.exception_handling;

public class AppealAccessDeniedException extends RuntimeException{

    public AppealAccessDeniedException(String message) {
        super(message);
    }
}