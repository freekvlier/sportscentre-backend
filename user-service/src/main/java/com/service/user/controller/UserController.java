package com.service.user.controller;




import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.service.user.model.User;
import com.service.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @RequestMapping("/test")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "test";
    }

    @GetMapping("/login/check")
    public User isFirstLogin(@RequestHeader(name="Authorization") String bearer) throws UnsupportedEncodingException {
        return userService.login(bearer);
    }

    @DeleteMapping("/{userid}")
    public boolean deleteUser(@RequestParam String userid) {
        return userService.delete(userid);
    }
}
