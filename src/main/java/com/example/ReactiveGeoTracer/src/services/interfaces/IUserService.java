package com.example.ReactiveGeoTracer.src.services.interfaces;

import com.example.ReactiveGeoTracer.src.models.Role;
import com.example.ReactiveGeoTracer.src.models.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IUserService {
    Mono<User> createUser(User user);
    Mono<User> getUserById(UUID id);
    Mono<User> getUnActiveUserById(UUID id);
    Mono<User> getUserByEmail(String email);
    Mono<User> updateUserPassword(UUID id, String newPassword);
    Mono<User> updateUserEmail(UUID id, String newEmail);
    Mono<User> updateUserRole(UUID id, Role role);
    Mono<User> changeUserActive(UUID id);
    Mono<Void> deleteUser(UUID id);
}
