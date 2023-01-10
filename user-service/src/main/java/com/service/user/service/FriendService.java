package com.service.user.service;

import com.service.user.dto.FriendRequestResponse;
import com.service.user.dto.FriendResponse;
import com.service.user.model.Friend;
import com.service.user.model.FriendRequest;
import com.service.user.repository.IFriendRepository;
import com.service.user.repository.IFriendRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FriendService {

    private final UserService userService;
    private final IFriendRepository friendRepository;

    private final IFriendRequestRepository friendRequestRepository;

    public Boolean sentFriendRequest(String userId, String friendId) throws IllegalAccessException {
        List<FriendResponse> friends = getFriends(userId);
        List<FriendRequest> sendedFriendRequestList = friendRequestRepository.findAllByUserId(userId);
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByFriendId(userId);
        FriendRequest friendRequest = FriendRequest.builder()
                .userId(userId)
                .friendId(friendId)
                .build();


        // check if the friend request is send to the same user
        if (friendRequest.getFriendId().equals(friendRequest.getUserId())) {
            throw new IllegalAccessException("Sending a friend request to yourself is not allowed");
        }
        // check if the friend request is already sent to this person
        for (FriendRequest sendedFriendRequest : sendedFriendRequestList) {
            if (sendedFriendRequest.getFriendId().equals(friendRequest.getFriendId())) {
                throw new IllegalAccessException("Friend request is already sent to this person");
            }
        }
        // check if a friend request is already received from this person
        for (FriendRequest friend : friendRequests) {
            if (friend.getUserId().equals(friendRequest.getFriendId())) {
                throw new IllegalAccessException("Friend request is already received from this person");
            }
        }
        // check if user is sending a request to an existing friend
        for (FriendResponse friend : friends) {
            if (friend.getUserId().equals(friendRequest.getFriendId())) {
                throw new IllegalAccessException("Sending a friend request to existing friend is not allowed");
            }
        }
        friendRequestRepository.save(friendRequest);
        return true;
    }

    public List<FriendResponse> getFriends(String userId) throws IllegalAccessException {
        List<Friend> friends = friendRepository.findAllByUserIdOrFriendId(userId, userId);
        List<FriendResponse> friendsList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendResponse friendResponse;
            if (friend.getUserId().equals(userId)) {
                friendResponse = FriendResponse.builder()
                        .userId(friend.getFriendId())
                        .name(userService.findByUserid(friend.getFriendId()).getName())
                        .build();
            } else {
                friendResponse = FriendResponse.builder()
                        .userId(friend.getUserId())
                        .name(userService.findByUserid(friend.getUserId()).getName())
                        .build();
            }
            friendsList.add(friendResponse);
        }
        return friendsList;
    }

    private FriendRequestResponse mapToFriendRequestResponse(FriendRequest friendRequest) {
        return FriendRequestResponse.builder()
                .userId(friendRequest.getFriendId())
                .name(userService.findByUserid(friendRequest.getFriendId()).getName())
                .build();
    }

    private FriendResponse mapToFriendResponse(Friend friend) {
        return FriendResponse.builder()
                .userId(friend.getFriendId())
                .name(userService.findByUserid(friend.getFriendId()).getName())
                .build();
    }

    public List<FriendRequestResponse> getFriendRequests(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByFriendId(userId);
        List<FriendRequestResponse> friendRequestResponseList = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            friendRequestResponseList.add(mapToFriendRequestResponse(friendRequest));
        }
        return friendRequestResponseList;
    }

    public List<FriendRequestResponse> getSendedFriendRequests(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByUserId(userId);
        List<FriendRequestResponse> friendRequestResponseList = new ArrayList<>();
        for (FriendRequest friendRequest : friendRequests) {
            friendRequestResponseList.add(mapToFriendRequestResponse(friendRequest));
        }
        return friendRequestResponseList;
    }

    public FriendResponse acceptFriendRequest(String userId, String friendRequestId) throws IllegalAccessException {
        FriendRequest friendRequest = friendRequestRepository.findById(friendRequestId).orElse(null);

        if (friendRequest == null)
            throw new IllegalAccessException("Friend request not found");
        if(!userId.equals(friendRequest.getFriendId()))
            throw new IllegalAccessException("This is not your friend request");

        Friend friend = Friend.builder()
                .userId(friendRequest.getUserId())
                .friendId(friendRequest.getFriendId())
                .build();

        friendRequestRepository.deleteById(friendRequest.getId()); //Delete friend request
        return mapToFriendResponse(friendRepository.save(friend));
    }

    public void removeFriend(String userId, String friendId) {
        friendRepository.delete(friendRepository.findByUserIdAndFriendId(userId, friendId));
    }
}
