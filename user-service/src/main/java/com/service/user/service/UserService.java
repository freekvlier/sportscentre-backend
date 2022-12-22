package com.service.user.service;

import com.google.gson.Gson;
import com.service.user.dto.UserRequest;
import com.service.user.model.User;
import com.service.user.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    final KafkaTemplate kafkaTemplate;

    private boolean isFirstLogin(){
        return true;
    }

    public User login(String bearer) throws UnsupportedEncodingException {
        UserRequest user = new UserRequest(bearer);
        if(userRepository.findById(user.getId()).isEmpty()){
            userRepository.save(new User(user));
        }
        return userRepository.findById(user.getId()).orElse(null);
    }

    public boolean delete(String userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            kafkaTemplate.send("user-deletion", userId); //Emit user deletion event
            return true;
        }
        return false;
    }
}
