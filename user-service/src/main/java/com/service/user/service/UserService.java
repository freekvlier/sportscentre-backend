package com.service.user.service;

import com.google.gson.Gson;
import com.service.user.dto.UserRequest;
import com.service.user.dto.UserResponse;
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

    public UserResponse login(String bearer) throws UnsupportedEncodingException {
        UserRequest user = new UserRequest(bearer);
        if(userRepository.findById(user.getId()).isEmpty()){
            userRepository.save(new User(user));
        }

        return mapToUserResponse(userRepository.findById(user.getId()).orElse(null));
    }

    public UserResponse findByUserid(String userId){
        return mapToUserResponse(userRepository.findById(userId).orElse(null));
    }


    public boolean delete(String userId) {
        if(userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            kafkaTemplate.send("user-deletion", userId); //Emit user deletion event
            return true;
        }
        return false;
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .build();
    }

    public UserResponse setName(String bearer, String name) {
        UserRequest user = new UserRequest(bearer);
        User newUser = userRepository.findById(user.getId()).orElse(null);
        newUser.setName(name);
        return mapToUserResponse(userRepository.save(newUser));
    }
}
