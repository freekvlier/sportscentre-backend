package com.service.user.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.service.user.dto.UserResponse;
import com.service.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
//@CrossOrigin(origins = "*")
//@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "usertest";
    }

    @GetMapping("/login")
    public UserResponse login(@RequestHeader(name="Authorization") String bearer) throws UnsupportedEncodingException {
        return userService.login(bearer);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAll(@RequestHeader(name="Authorization") String auth) throws UnsupportedEncodingException, IllegalAccessException {
        if(isAdmin(auth))
            return new ResponseEntity<>(userService.getAll(auth), HttpStatus.OK);
        else
            return new ResponseEntity<>("User doesn't have admin privileges", HttpStatus.FORBIDDEN);
    }

    @PostMapping(value = "/setname/{name}")
    public UserResponse setName(@RequestHeader(name="Authorization") String bearer, @PathVariable String name) throws IllegalAccessException {
        return userService.setName(bearer, name);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader(name="Authorization") String bearer, @PathVariable String userId) throws IllegalAccessException {
        if(isAdmin(bearer))
            return new ResponseEntity<>(userService.delete(userId), HttpStatus.OK);
        else
            return new ResponseEntity<>("User doesn't have admin privileges", HttpStatus.FORBIDDEN);
    }

    private boolean isAdmin(String bearer) throws IllegalAccessException {
        DecodedJWT decodedJWT = JWT.decode(bearer.substring(7));
        Claim roles = decodedJWT.getClaims().get("roles");

        if(roles.toString().contains("Admin"))
            return true;

        return false;
    }
}
