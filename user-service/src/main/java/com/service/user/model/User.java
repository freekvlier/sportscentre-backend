package com.service.user.model;

import com.service.user.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public String id;
    public String email;
    public String name;

    public User(UserRequest userRequest) {
        this.id = userRequest.getId();
        this.email = userRequest.getEmail();
        this.name = userRequest.getName();
    }
}
