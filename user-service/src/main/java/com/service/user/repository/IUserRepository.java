package com.service.user.repository;

import com.service.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {
    public User findByOid(String oid);
}
