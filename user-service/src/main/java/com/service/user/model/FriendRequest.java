package com.service.user.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Document("friendrequests")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type= "uuid-char")
    private String id;
    private String userId;
    private String friendId;
}
