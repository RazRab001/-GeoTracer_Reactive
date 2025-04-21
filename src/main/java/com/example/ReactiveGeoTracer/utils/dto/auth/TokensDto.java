package com.example.ReactiveGeoTracer.utils.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokensDto {
    private String accessToken;
    private String refreshToken;
}
