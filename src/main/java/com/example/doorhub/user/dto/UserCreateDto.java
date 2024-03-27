package com.example.doorhub.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Pattern(regexp = "^998[0-9]{9}$")
    private String phoneNumber;
    @Email
    private String email;
}
