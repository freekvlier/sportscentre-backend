package com.service.user.controller;


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
    public boolean deleteUser(@PathVariable String userId) {
        return userService.delete(userId);
    }
}
