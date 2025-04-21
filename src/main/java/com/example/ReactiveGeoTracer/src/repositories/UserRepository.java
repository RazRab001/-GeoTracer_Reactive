package com.example.ReactiveGeoTracer.src.repositories;

import com.example.ReactiveGeoTracer.src.models.Role;
import com.example.ReactiveGeoTracer.src.models.User;
import lombok.NonNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {

    @Query("SELECT u FROM users u WHERE u.id = :id AND u.is_active = true")
    Mono<User> getUserByIdAndActiveIsTrue(@Param("id") UUID id);

    @Query("SELECT u FROM users u WHERE u.id = :id AND u.is_active = false")
    Mono<User> getUserByIdAndActiveIsFalse(@Param("id") UUID id);

    @Query("SELECT u FROM users u WHERE u.email = :email AND u.is_active = true")
    Mono<User> getUserByEmailAndActiveIsTrue(@Param("email") String email);

    @Query("UPDATE users SET email = :email WHERE id = :id")
    Mono<Void> updateUserEmail(@Param("id") UUID id, @Param("email") String newEmail);

    @Query("UPDATE users SET hashed_password = :password WHERE id = :id")
    void updateUserPassword(@Param("id") UUID id, @Param("password") String newPassword);

    @Query("UPDATE users SET role = :role WHERE id = :id")
    void updateUserRole(@Param("id") UUID id, @Param("role") Role role);

    @Query("UPDATE users SET is_active = CASE WHEN is_active = true THEN false ELSE true END WHERE id = :id")
    void changeUserActive(@Param("id") UUID id);
}
