package com.example.ReactiveGeoTracer.utils.dto.auth;

import com.example.ReactiveGeoTracer.src.models.Role;
import com.example.ReactiveGeoTracer.src.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
public class SignInDto {
    private String email;
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Role role;

    public User toUser(String hashedPassword) {
        User user = new User();
        user.setEmail(email);
        user.setHashedPassword(hashedPassword);
        if(role != null){
            user.setRole(role.toString());
        }
        return user;
    }
}
