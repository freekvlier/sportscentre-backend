package com.service.user.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.service.user.dto.UserResponse;
import com.service.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@AllArgsConstructor
//@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "usertest";
    }

    @GetMapping("/login")
    public UserResponse isFirstLogin(@RequestHeader(name="Authorization") String bearer) throws UnsupportedEncodingException {
        return userService.login(bearer);
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUser(@RequestHeader(name="Authorization") String bearer, @PathVariable String userId) throws IllegalAccessException {
        if(isAdmin(bearer))
            return userService.delete(userId);
        else
            return false;
    }

    private boolean isAdmin(String bearer) throws IllegalAccessException {
        DecodedJWT decodedJWT = JWT.decode(bearer.substring(7));
        String roles = decodedJWT.getClaim("roles").asString();

        if(roles.contains("Admin"))
            return true;

        return false;
    }
}
