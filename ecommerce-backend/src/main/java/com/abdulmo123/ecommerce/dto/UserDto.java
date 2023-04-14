package com.abdulmo123.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

public class UserDto implements Serializable {

    @NotEmpty(message = "First name field cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name field cannot be empty")
    private String lastName;

    @NotEmpty(message = "Email field cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotEmpty(message = "Password field cannot be empty")
    private String password;

    @NotEmpty(message = "Phone number field cannot be empty")
    private String phoneNumber;

    @NotEmpty(message = "Address field cannot be empty")
    private String address;

    public UserDto () {
        super();
    }

    public UserDto(String firstName, String lastName, String email, String password, String phoneNumber, String address) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }
}

