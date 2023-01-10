package com.service.user.repository;

import com.service.user.model.FriendRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IFriendRequestRepository extends MongoRepository<FriendRequest, String> {
    List<FriendRequest> findAllByFriendId(String userId);

    List<FriendRequest> findAllByUserId(String userId);
}
