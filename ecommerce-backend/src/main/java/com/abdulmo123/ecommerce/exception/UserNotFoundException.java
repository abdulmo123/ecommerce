package com.abdulmo123.ecommerce.exception;

import com.abdulmo123.ecommerce.model.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
