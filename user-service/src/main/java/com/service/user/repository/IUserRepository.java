package com.service.user.repository;

import com.service.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {

}
