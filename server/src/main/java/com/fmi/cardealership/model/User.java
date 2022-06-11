package com.fmi.cardealership.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {
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
    private UserRole role = UserRole.USER;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    public void update(User other) {
        if (other != null) {
            this.setName(other.getName());
            this.setUsername(other.getUsername());
            this.setPassword(other.getPassword());
            this.setEmail(other.getEmail());
            this.setBirthday(other.getBirthday());
            this.setRole(other.getRole());
            this.setPhoneNumber(other.getPhoneNumber());
            this.setStatus(other.status);
        }
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", role.toString())));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return status == Status.ACTIVE ||
                status == Status.DEACTIVATED;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE;
    }
}
