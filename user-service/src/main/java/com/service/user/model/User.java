package com.service.user.model;

import com.bol.secure.Encrypted;
import com.service.user.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String id;
    @Encrypted
    public String email;
    public String name;

    public User(UserRequest userRequest) {
        this.id = userRequest.getId();
        this.email = userRequest.getEmail();
        this.name = userRequest.getName();
    }
}
