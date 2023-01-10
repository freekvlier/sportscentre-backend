package com.service.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendRequestResponse {
    private String userId;
    private String name;
}
