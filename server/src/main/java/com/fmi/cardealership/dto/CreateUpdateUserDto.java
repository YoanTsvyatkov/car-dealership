package com.fmi.cardealership.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmi.cardealership.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateUserDto {
    @NotBlank(message = "The name is mandatory!")
    private String name;

    @NotBlank(message = "The username is mandatory!")
    private String username;

    @NotBlank(message = "The password is mandatory!")
    private String password;

    @NotBlank(message = "The email address is mandatory!")
    @Email(message = "Invalid email address!")
    private String email;

    @NotNull(message = "The birth date is mandatory!")
    @Past(message = "The date should be in the past!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "The phone number is mandatory!")
    private String phoneNumber;
}
