package com.example.firebasedemo;

import java.sql.Connection;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public class User {
    private String name;
    private List<String> connection;
//    private List<StickMessage> messages;
//    private Map<User, Map<Time,String>> messages;


    public User() {
    }

    public User(String name, List<String> connection) {
        this.name = name;
        this.connection = connection;
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
