package com.example.ReactiveGeoTracer.components;

import de.mkammerer.argon2.Argon2;
import org.springframework.stereotype.Component;

@Component
public class HashComponent {
    private final Argon2 argon2;

    public HashComponent(Argon2 argon2) {
        this.argon2 = argon2;
    }

    public String hash(String input) {
        return argon2.hash(2, 65536, 1, input.toCharArray());
    }
}
