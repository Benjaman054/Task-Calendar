package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User , String> {
    User findByUserName(String username);
}
