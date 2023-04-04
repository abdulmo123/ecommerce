package com.abdulmo123.ecommerce.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super();
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
