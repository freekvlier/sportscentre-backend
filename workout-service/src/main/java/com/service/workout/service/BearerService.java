package com.service.workout.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BearerService {

    public String getUserId(String bearer) {
        DecodedJWT decodedJWT = JWT.decode(bearer.substring(7));
        return decodedJWT.getClaim("oid").asString();
    }
}
