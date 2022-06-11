package com.fmi.cardealership.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CredentialsDto {
    @NotBlank(message = "The username is mandatory!")
    private String username;
    @NotBlank(message = "The password is mandatory!")
    private String password;
}
