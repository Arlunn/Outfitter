package com.example.outfitter;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Account {
    public String username;
    public String password;


    public Account(String u, String p) {
        username = u;
        password = p;
    }

    public void setUsername(String u) {
        username = u;
    }
    public void setPassword(String u) {
        password = u;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}

}
