package com.service.user.controller;

import com.service.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

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
    public void isFirstLogin(@RequestHeader (name="Authorization") String token) throws IllegalAccessException, ParseException, UnsupportedEncodingException {
//        Object user = SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
        userService.login(token);

    }
}
