package com.example.ReactiveGeoTracer.src.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private UUID id;

    @Column("email")
    private String email;

    @Column("hashed_password")
    private String hashedPassword;

    @Column("is_active")
    private boolean isActive = false;

    @Column("role")
    private String role = Role.USER.toString();
}