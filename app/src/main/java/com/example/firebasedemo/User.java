package com.example.firebasedemo;

import java.sql.Connection;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private String name;
    private List<String> connection;
//    private List<StickMessage> messages;
//    private Map<User, Map<Time,String>> messages;


    public User(String name) {
        this.name = name;
        this.connection = new ArrayList<>();
        connection.add(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getConnection() {
        return connection;
    }

    public void setConnection(List<String> connection) {
        this.connection = connection;
    }
}
