package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Service
@RequiredArgsConstructor
public class BackService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;


    public User authenticate(User user){
        User currUser = repository.findByUserName(user.getUserName());
        if(currUser == null ||!passwordEncoder.matches(user.getPassword(), currUser.getPassword())){
            return null;
        }
        else{
            return currUser;
        }
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }



    public void updateTasks(String name , ArrayList<TaskSender> tasks){
        Query query = new Query(Criteria.where("userName").is(name));
        Update update = new Update().set("userTasks" , tasks);
        mongoTemplate.updateFirst(query , update , User.class);
    }
}
