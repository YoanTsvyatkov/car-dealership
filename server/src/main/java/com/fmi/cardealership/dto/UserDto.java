package com.fmi.cardealership.dto;

import com.fmi.cardealership.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;
    private UserRole role;
    private String phoneNumber;
}
