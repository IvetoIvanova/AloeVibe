package com.example.aloevibe.model.dto;

import com.example.aloevibe.validation.annotations.PasswordMatches;
import jakarta.validation.constraints.*;

import static com.example.aloevibe.util.Constants.PASSWORD_REGEX;

@PasswordMatches
public class UserRegistrationDTO {

    @NotNull(message = "Имейлът е задължителен.")
    @Email(message = "Моля, въведете валиден имейл адрес.")
    private String email;

    @NotBlank(message = "Името е задължително.")
    private String firstName;

    @NotBlank(message = "Фамилията е задължителна.")
    private String lastName;

    @Size(min = 10, max = 15)
    private String phone;

    @NotBlank(message = "Това поле е задължително.")
    @Size(min = 8, message = "Паролата трябва да е минимум 8 символа.")
    @Pattern(regexp = PASSWORD_REGEX, message = "")
    private String password;

    @NotBlank(message = "Това поле е задължително.")
    private String confirmPassword;

    public UserRegistrationDTO() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
