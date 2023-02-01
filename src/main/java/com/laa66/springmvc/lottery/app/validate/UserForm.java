package com.laa66.springmvc.lottery.app.validate;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *
 *  UserValid is a draft class for validating user input
 *  submitted with /user/save endpoint
 *
 */

@ValidPassword(message = "Incorrect password")
public class UserForm {

    @NotBlank(message = "Field cannot be empty")
    @Email(message = "Wrong email address")
    private String email;

    @NotBlank(message = "Field cannot be empty")
    private String firstName;

    @NotBlank(message = "Field cannot be empty")
    private String lastName;

    @ValidDate(message = "You must be over 18 years old")
    private String birthDate;

    @NotBlank(message = "Field cannot be empty")
    private String username;

    @NotBlank(message = "Field cannot be empty")
    private String password;

    @NotBlank(message = "Field cannot be empty")
    private String confirmPassword;

    public UserForm() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserValid{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
