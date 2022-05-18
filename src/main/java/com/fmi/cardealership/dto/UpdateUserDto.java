package com.fmi.cardealership.dto;

import com.fmi.cardealership.model.UserRole;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String name;
    private String username;
    private String password;

    @Email(message = "Invalid email address!")
    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    private UserRole role;
    private String phoneNumber;
}
