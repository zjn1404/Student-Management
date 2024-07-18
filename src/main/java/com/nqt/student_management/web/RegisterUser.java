package com.nqt.student_management.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUser {

    @NotBlank(message = "Username can not be empty")
    @Size(min = 6, max = 15, message = "Username should be between 6-15 characters")
    private String username;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 8, message = "Password should be at minimum 8 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should contain at least 1 lowercase letter, 1 uppercase letter, 1 number and 1 special character")
    private String password;

    @NotBlank(message = "First name can not be empty")
    private String firstName;

    @NotBlank(message = "Name can not be empty")
    private String name;

    @NotBlank(message = "Email can not be empty")
    @Email(message = "Email is not valid")
    private String email;

    public RegisterUser() {}

    public RegisterUser(String username, String password, String firstName, String name, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.name = name;
        this.email = email;
    }

    public @NotBlank(message = "Username can not be empty") @Size(min = 6, max = 15, message = "Username should be between 6-15 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username can not be empty") @Size(min = 6, max = 15, message = "Username should be between 6-15 characters") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Password can not be empty") @Size(min = 8, message = "Password should be at minimum 8 characters") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should contain at least 1 lowercase letter, 1 uppercase letter, 1 number and 1 special character") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password can not be empty") @Size(min = 8, message = "Password should be at minimum 8 characters") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password should contain at least 1 lowercase letter, 1 uppercase letter, 1 number and 1 special character") String password) {
        this.password = password;
    }

    public @NotBlank(message = "First name can not be empty") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name can not be empty") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Name can not be empty") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name can not be empty") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email can not be empty") @Email(message = "Email is not valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email can not be empty") @Email(message = "Email is not valid") String email) {
        this.email = email;
    }
}
