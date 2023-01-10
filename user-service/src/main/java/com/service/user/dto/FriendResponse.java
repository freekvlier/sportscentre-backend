package com.service.user.dto;

import com.service.user.model.Friend;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FriendResponse {
    private String userId;
    private String name;
}
