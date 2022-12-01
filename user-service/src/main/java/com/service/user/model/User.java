package com.service.user.model;

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
    public String oid;
    public String name;

    public User(UserRequest userRequest) {
        this.oid = userRequest.getOid();
        this.name = userRequest.getName();
    }
}
