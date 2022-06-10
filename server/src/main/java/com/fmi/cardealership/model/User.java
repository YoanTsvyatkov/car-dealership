package com.fmi.cardealership.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
