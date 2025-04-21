package com.example.ReactiveGeoTracer.src.controllers;

import com.example.ReactiveGeoTracer.components.HashComponent;
import com.example.ReactiveGeoTracer.src.models.User;
import com.example.ReactiveGeoTracer.src.services.interfaces.IUserService;
import com.example.ReactiveGeoTracer.utils.dto.auth.SignInDto;
import com.example.ReactiveGeoTracer.utils.dto.user.UserGetDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;
    private final HashComponent hashComponent;

    public AuthController(IUserService userService, HashComponent hashComponent) {
        this.userService = userService;
        this.hashComponent = hashComponent;
    }

    @PostMapping("/reg")
    public Mono<ResponseEntity<UserGetDto>> registration(@Valid @RequestBody SignInDto dto) {
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body(null));
        }

        String hashedPassword = hashComponent.hash(dto.getPassword());
        User user = dto.toUser(hashedPassword);

        return userService.createUser(user)
                .map(UserGetDto::new)
                .map(userGetDto -> ResponseEntity.status(HttpStatus.CREATED).body(userGetDto))
                .onErrorResume(e -> {
                    System.err.println("Error during registration: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
                });
    }
}
