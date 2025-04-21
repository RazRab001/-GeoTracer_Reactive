package com.example.ReactiveGeoTracer.utils.dto.user;

import com.example.ReactiveGeoTracer.src.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserGetDto {
    private UUID id;
    private String email;
    private String hashedPassword;
    private String role;

    public UserGetDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.hashedPassword = user.getHashedPassword();
        this.role = user.getRole();
    }
}
