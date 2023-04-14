package com.abdulmo123.ecommerce.exception;

public class OrderHistoryNotFoundException extends RuntimeException {

    public OrderHistoryNotFoundException () {
        super();
    }
    public OrderHistoryNotFoundException(String message) {
        super(message);
    }
}
