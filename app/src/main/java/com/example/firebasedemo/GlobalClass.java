package com.example.firebasedemo;

import android.app.Application;

public class GlobalClass extends Application {
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
