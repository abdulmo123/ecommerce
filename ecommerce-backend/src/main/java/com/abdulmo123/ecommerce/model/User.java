package com.abdulmo123.ecommerce.model;

import org.springframework.data.annotation.Transient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable=false)
    private Long id;

    @Email(message = "Invalid email")
    @NotEmpty(message = "Cannot be empty")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    @Transient
    private String password;

    @NotEmpty(message = "Please enter first name")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Please enter last name")
    @Column(name = "last_name")
    private String lastName;

    private String phoneNumber;
    private String address;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    public User () {}

    public User (String email, String password, String firstName, String lastName, String phoneNumber, String address, boolean enabled, String confirmationToken) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.enabled = enabled;
        this.confirmationToken = confirmationToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName;}

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getConfirmationToken() { return confirmationToken; }

    public void setConfirmationToken(String confirmationToken) { this.confirmationToken = confirmationToken; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", first name='" + firstName + '\'' +
                ", last name='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", enabled='" + enabled + '\'' +
                ", confirmation token='" + confirmationToken + '\'' +
                '}';
    }
}
