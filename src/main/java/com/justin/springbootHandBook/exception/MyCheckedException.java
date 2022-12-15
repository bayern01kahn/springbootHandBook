package com.justin.springbootHandBook.exception;

public class MyCheckedException extends Exception {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyCheckedException(String message) {
        this.message = message;
    }
}
