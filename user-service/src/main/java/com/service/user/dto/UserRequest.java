package com.service.user.dto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.service.user.model.User;
import lombok.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    public String id;
    public String email;
    public String name;

    public UserRequest(String bearer) {
        DecodedJWT decodedJWT = JWT.decode(bearer.substring(7));
        this.id = decodedJWT.getClaim("oid").asString();
        this.email = decodedJWT.getClaim("preferred_username").asString();
        this.name = decodedJWT.getClaim("name").asString();
    }
}
