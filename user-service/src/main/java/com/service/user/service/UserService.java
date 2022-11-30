package com.service.user.service;

import com.google.gson.Gson;
import com.service.user.dto.UserRequest;
import com.service.user.model.User;
import com.service.user.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    private UserRequest decodeJWT(String jwt) throws UnsupportedEncodingException {
        String[] pieces = jwt.substring(6).split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
        return new Gson().fromJson(jsonString, UserRequest.class);
    }

    private boolean isFirstLogin(){
        return true;
    }

    public User login(String token) throws UnsupportedEncodingException {
//        if(isFirstLogin()){
//            userRepository.save(user);
//        }
        UserRequest user = decodeJWT(token);
        return userRepository.findByOid(user.oid);
    }
}
