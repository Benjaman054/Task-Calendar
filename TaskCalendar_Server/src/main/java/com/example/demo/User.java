package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Data
public class User {
    @Id
    private String id;
    private String userName;
    private String password;
    @JsonProperty("userTasks")
    private  ArrayList<TaskSender> userTasks = new ArrayList<>();


    public User() {
    }

    public User(String name , String pass){
        this.userName = name;
        this.password = pass;
    }




}
