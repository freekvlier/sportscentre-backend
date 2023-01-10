package com.service.user.repository;

import com.service.user.dto.FriendResponse;
import com.service.user.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IFriendRepository extends MongoRepository<Friend, String> {
    List<Friend> findAllByUserIdOrFriendId(String id, String id2);
    Friend findByUserIdAndFriendId(String userId, String friendid);
}
