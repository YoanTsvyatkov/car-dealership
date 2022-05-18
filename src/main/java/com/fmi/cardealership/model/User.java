package com.fmi.cardealership.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (unique = true, nullable = false)
    private String username;

    @Column (nullable = false)
    private String password;

    @Column (unique = true, nullable = false)
    private String email;

    @Column (nullable = false)
    private LocalDate birthday;
    
    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private UserRole role;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setUsername(String username) {
        if (username != null) {
            this.username = username;
        }
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday != null) {
            this.birthday = birthday;
        }
    }

    public void setRole(UserRole role) {
        if (role != null) {
            this.role = role;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void update(User other) {
        if (other != null) {
            this.setName(other.getName());
            this.setUsername(other.getUsername());
            this.setPassword(other.getPassword());
            this.setEmail(other.getEmail());
            this.setBirthday(other.getBirthday());
            this.setRole(other.getRole());
            this.setPhoneNumber(other.getPhoneNumber());
        }
    }
}
