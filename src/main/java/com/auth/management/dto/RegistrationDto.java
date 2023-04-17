package com.auth.management.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
    private Long id;

    @NotEmpty(message = "First Name should not be empty or null")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty or null")
    private String lastName;

    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message="Phone number should not be empty")
    private String phoneNumber;
}