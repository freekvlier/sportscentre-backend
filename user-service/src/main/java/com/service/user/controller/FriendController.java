package com.service.user.controller;

import com.service.user.dto.FriendRequestResponse;
import com.service.user.dto.FriendResponse;
import com.service.user.dto.UserRequest;
import com.service.user.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("friends")
public class FriendController {
    private final FriendService friendService;

    @PostMapping(value = "/add/{friendId}")
    public ResponseEntity<?> sentFriendRequest(@RequestHeader(name="Authorization") String bearer, @PathVariable String friendId) {
        UserRequest user = new UserRequest(bearer);
        try {
            Boolean friendRequest = friendService.sentFriendRequest(user.getId(), friendId);
            return new ResponseEntity<>(friendRequest, HttpStatus.OK);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/requests")
    public List<FriendRequestResponse> getFriendRequests(@RequestHeader(name="Authorization") String bearer) {
        UserRequest user = new UserRequest(bearer);
        return friendService.getFriendRequests(user.getId());
    }

    @GetMapping(value = "/sendedrequests")
    public List<FriendRequestResponse> getSendedFriendRequests(@RequestHeader(name="Authorization") String bearer) {
        UserRequest user = new UserRequest(bearer);
        return friendService.getSendedFriendRequests(user.getId());
    }

    @PostMapping(value = "/requests/accept/{friendRequestId}")
    public FriendResponse acceptFriendRequest(@RequestHeader(name="Authorization") String bearer, @PathVariable String friendRequestId) throws IllegalAccessException {
        UserRequest user = new UserRequest(bearer);
        return friendService.acceptFriendRequest(user.getId(), friendRequestId);
    }

    @GetMapping
    public List<FriendResponse> getFriends(@RequestHeader(name="Authorization") String bearer) throws IllegalAccessException {
        UserRequest user = new UserRequest(bearer);
        return friendService.getFriends(user.getId());
    }

    @DeleteMapping(value = "/remove/{friendid}")
    public void removeFriend(@RequestHeader(name="Authorization") String bearer, @PathVariable String friendid) {
        UserRequest user = new UserRequest(bearer);
        friendService.removeFriend(user.getId(), friendid);
    }
}
