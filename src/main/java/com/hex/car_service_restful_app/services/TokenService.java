package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public String createNewToken(User user) {
        return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    }
}
