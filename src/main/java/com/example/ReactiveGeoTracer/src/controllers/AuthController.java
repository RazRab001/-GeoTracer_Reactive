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
        long startTime = System.currentTimeMillis();
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return Mono.just(ResponseEntity.badRequest().body(null));
        }
        System.out.println("Step 1 (User ID check): " + (System.currentTimeMillis() - startTime) + " ms");

        String hashedPassword = hashComponent.hash(dto.getPassword());
        System.out.println("Step 2 (Password hashing): " + (System.currentTimeMillis() - startTime) + " ms");

        User user = dto.toUser(hashedPassword);
        System.out.println("Step 3 (Convert from dto): " + (System.currentTimeMillis() - startTime) + " ms");

        return userService.createUser(user)
                .doOnNext(savedUser -> {
                    long dbSaveTime = System.currentTimeMillis();
                    System.out.println("Step 4 (Database save): " + (dbSaveTime - startTime) + " ms");
                })
                .map(UserGetDto::new)
                .doOnNext(userGetDto -> {
                    long mappingTime = System.currentTimeMillis();
                    System.out.println("Step 5 (Mapping to UserGetDto): " + (mappingTime - startTime) + " ms");
                })
                .map(userGetDto -> ResponseEntity.status(HttpStatus.CREATED).body(userGetDto))
                .onErrorResume(e -> {
                    System.err.println("Error during registration: " + e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
                });
    }
}
