package com.example.ReactiveGeoTracer.src.services;

import com.example.ReactiveGeoTracer.src.models.Role;
import com.example.ReactiveGeoTracer.src.models.User;
import com.example.ReactiveGeoTracer.src.repositories.UserRepository;
import com.example.ReactiveGeoTracer.src.services.interfaces.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> getUserById(UUID id) {
        return userRepository.getUserByIdAndActiveIsTrue(id);
    }

    @Override
    public Mono<User> getUnActiveUserById(UUID id) {
        return userRepository.getUserByIdAndActiveIsFalse(id);
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return userRepository.getUserByEmailAndActiveIsTrue(email);
    }

    @Override
    public Mono<User> updateUserPassword(UUID id, String newPassword) {
        return userRepository.getUserByIdAndActiveIsTrue(id)
                .flatMap(user -> {
                    user.setHashedPassword(newPassword);
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> updateUserEmail(UUID id, String newEmail) {
        return userRepository.getUserByIdAndActiveIsTrue(id)
                .flatMap(user -> {
                    user.setEmail(newEmail);
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> updateUserRole(UUID id, Role role) {
        return userRepository.getUserByIdAndActiveIsTrue(id)
                .flatMap(user -> {
                    user.setRole(role.toString());
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> changeUserActive(UUID id) {
        return userRepository.getUserByIdAndActiveIsTrue(id)
                .flatMap(user -> {
                    user.setActive(!user.isActive());
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<Void> deleteUser(UUID id) {
        return userRepository.deleteById(id);
    }
}
